package app.recurring_task.repository;

import app.recurring_task.model.RecurringTask;
import app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, UUID> {
    List<RecurringTask> findByTaskUser(User user);
}
