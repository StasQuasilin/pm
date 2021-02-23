package utils.db.dao.tree;

import entity.finance.category.Category;
import entity.task.Task;
import entity.task.TaskDependency;
import entity.task.TaskStatus;
import entity.task.TimeLog;
import entity.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskDAO {
    List<Task> getTasksByParent(Category parent);
    List<Task> getTaskByOwner(User owner);
    Task getTask(Object id);
    void saveTask(Task task);
    List<Task> getTaskByStatus(User user, TaskStatus active);
    Task getTaskByCategory(Category category);

    TimeLog getTimeLogByUser(User user);

    TimeLog getTimeLog(Object o);

    void saveTimeLog(TimeLog timeLog);

    void removeTasks(User user);

    List<TaskDependency> getDependency(Task task);
    List<TaskDependency> getPrincipal(Task task);
}
