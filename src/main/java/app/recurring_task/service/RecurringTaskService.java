package app.recurring_task.service;

import app.recurring_task.model.RecurringTask;
import app.recurring_task.repository.RecurringTaskRepository;
import app.task.model.Task;
import app.user.model.User;
import app.web.dto.TaskRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecurringTaskService {
    private final RecurringTaskRepository recurringTaskRepository;

    public RecurringTaskService(RecurringTaskRepository recurringTaskRepository) {
        this.recurringTaskRepository = recurringTaskRepository;
    }

    public void save(User user, TaskRequest task, Task savedTask) {
        RecurringTask recurringTask = RecurringTask.builder()
                .task(savedTask)
                .type(task.getRecurringTaskType())
                .startDate(task.getStartDate().atStartOfDay())
                .endDate(task.getEndOccurrence().atTime(23, 59, 59))
                .build();
        recurringTaskRepository.save(recurringTask);
    }

    public void update(TaskRequest task, Task existingTask) {
        RecurringTask recurringTask = existingTask.getRecurringTask();
        if(recurringTask==null){
            save(existingTask.getUser(),task,existingTask);
            return;
        }
        recurringTask.setType(task.getRecurringTaskType());
        recurringTask.setEndDate(task.getEndOccurrence().atStartOfDay());
        recurringTaskRepository.save(recurringTask);
    }


}
