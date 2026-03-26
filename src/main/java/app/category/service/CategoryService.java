package app.category.service;

import app.category.model.Category;
import app.category.repository.CategoryRepository;
import app.recurring_task.model.RecurringTask;
import app.recurring_task.model.RecurringTaskType;
import app.recurring_task.repository.RecurringTaskRepository;
import app.task.model.Task;
import app.task.service.TaskService;
import app.user.model.User;
import app.exception.*;
import app.user.service.UserService;
import app.web.dto.CategoryCombinedWithTask;
import app.web.dto.CategoryRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final RecurringTaskRepository recurringTaskRepository;

    public CategoryService(CategoryRepository categoryRepository, UserService userService, @Lazy TaskService taskService, RecurringTaskRepository recurringTaskRepository) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.taskService = taskService;
        this.recurringTaskRepository = recurringTaskRepository;
    }

    public List<CategoryCombinedWithTask> makeCombinedObject(List<Category> categories, LocalDate selectedDate) {
        List<CategoryCombinedWithTask> categoryCombinedWithTasks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (Category category : categories) {
            for (Task task : category.getTasks()) {
                if (selectedDate.equals(task.getDueDate().toLocalDate())) {
                    CategoryCombinedWithTask c = new CategoryCombinedWithTask();
                    c.setColor(category.getColor());
                    c.setPriority(task.getPriority());
                    c.setTitleTask(task.getTitle());
                    c.setDate(task.getDueDate().toLocalTime().format(formatter));
                    categoryCombinedWithTasks.add(c);
                }
            }
        }
        
        addRecurringTasksForDate(categories, selectedDate, categoryCombinedWithTasks, formatter);
        
        return categoryCombinedWithTasks;
    }
    
    private void addRecurringTasksForDate(List<Category> categories, LocalDate selectedDate, List<CategoryCombinedWithTask> categoryCombinedWithTasks, DateTimeFormatter formatter) {
        if (categories.isEmpty()) {
            return;
        }
        
        List<RecurringTask> recurringTasks = recurringTaskRepository.findByTaskUser(categories.get(0).getUser());
        
        for (RecurringTask rt : recurringTasks) {
            LocalDate taskStartDate = rt.getStartDate().toLocalDate();
            LocalDate taskEndDate = rt.getEndDate().toLocalDate();
            
            if (selectedDate.isBefore(taskStartDate) || selectedDate.isAfter(taskEndDate)) {
                continue;
            }
            
            if (!isOccurrenceDate(taskStartDate, selectedDate, rt.getType())) {
                continue;
            }
            
            Task recurringTask = rt.getTask();
            Category taskCategory = recurringTask.getCategory();
            
            CategoryCombinedWithTask c = new CategoryCombinedWithTask();
            c.setColor(taskCategory.getColor());
            c.setPriority(recurringTask.getPriority());
            c.setTitleTask(recurringTask.getTitle());
            c.setDate(recurringTask.getDueDate().toLocalTime().format(formatter));
            categoryCombinedWithTasks.add(c);
        }
    }
    
    private boolean isOccurrenceDate(LocalDate startDate, LocalDate selectedDate, RecurringTaskType type) {
        switch (type) {
            case DAILY:
                return !selectedDate.isBefore(startDate);
            case WEEKLY:
                return selectedDate.equals(startDate) || !selectedDate.isBefore(startDate) && selectedDate.getDayOfWeek() == startDate.getDayOfWeek();
            case MONTHLY:
                return selectedDate.equals(startDate) || !selectedDate.isBefore(startDate) && selectedDate.getDayOfMonth() == startDate.getDayOfMonth();
            case YEARLY:
                return selectedDate.equals(startDate) || !selectedDate.isBefore(startDate) && selectedDate.getDayOfYear() == startDate.getDayOfYear();
            default:
                return false;
        }
    }

    public void createCategory(User user, CategoryRequest categoryRequest) {
        if (categoryRequest.getCategoryName() == null || categoryRequest.getCategoryName().isEmpty()
                || categoryRequest.getCategoryName().length() < 4 || categoryRequest.getCategoryName().length() > 50) {
            throw new CategoryInvalidLengthException("The category size must be between 4 and 50 characters");
        }
        if (categoryRequest.getCategoryColor() == null || categoryRequest.getCategoryColor().isEmpty()
                || categoryRequest.getCategoryColor().length() < 4 || categoryRequest.getCategoryColor().length() > 50) {
            throw new InvalidColorException("The color is invalid");
        }

        Category category = Category.builder()
                .name(categoryRequest.getCategoryName())
                .color(categoryRequest.getCategoryColor())
                .user(user)
                .isDeleted(false)
                .build();
        Category savedCategory = categoryRepository.save(category);
        if (user.getCategories() == null) {
            user.setCategories(new ArrayList<>());
        }
        user.getCategories().add(savedCategory);
        userService.save(user);
    }


    public Category getById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void save(Category categoryById) {
        categoryRepository.save(categoryById);
    }

    public Map<String, Integer> getCountOfCategoriesAndTheirTasks(User user) {
        Map<String, Integer> countOfCategoriesAndTheirTasks = new HashMap<>();
        for (Category category : user.getCategories()) {
            countOfCategoriesAndTheirTasks.put(category.getName(), category.getTasks().size());
        }

        return countOfCategoriesAndTheirTasks;
    }

    public List<String> getCategoryNames(User user) {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : user.getCategories()) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }

    public void deleteCategoryAndAllTasks(User user, UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        if (category.getTasks() != null) {
//            for (Task task : category.getTasks()) {
//                category.getTasks().remove(task);
//            }
            for (int i = category.getTasks().size() - 1; i >= 0; i--) {
                Task task = category.getTasks().get(i);
                category.getTasks().remove(task);
            }
        }
        userService.deleteCategory(user, category);
        categoryRepository.delete(category);
    }
}
