package utils;

import api.socket.UpdateUtil;
import entity.project.Project;
import entity.task.*;
import entity.transactions.Transaction;
import entity.transactions.TransactionCategory;
import entity.transactions.TransactionDetail;
import entity.user.User;
import org.hibernate.type.TimeType;
import services.hibernate.HibernateSessionFactory;
import services.hibernate.Hibernator;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TaskUtil {

    static dbDAO dao = dbDAOService.getDao();
    static UpdateUtil updateUtil = new UpdateUtil();

    public static void checkParenthood(Task parent, User user) {
        List<Task> tasks = dao.getTaskByUserAndParent(user, parent);
        HashMap<TaskStatus, Integer> counts = new HashMap<>();

        TaskStatistic statistic = dao.getTaskStatistic(parent);
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setTask(parent);
        }

        statistic.setActiveChildren(0);
        statistic.setProgressingChildren(0);
        statistic.setDoneChildren(0);

        for (Task task : tasks){
            TaskStatus status = task.getStatus();
            if (!counts.containsKey(status)){
                counts.put(status, 0);
            }
            counts.put(status, counts.get(status) + 1);

            statistic.setActiveChildren(statistic.getActiveChildren() + task.getChildrenCount(TaskStatus.active));
            statistic.setProgressingChildren(statistic.getProgressingChildren() + task.getChildrenCount(TaskStatus.progressing));
            statistic.setDoneChildren(statistic.getDoneChildren() + task.getChildrenCount(TaskStatus.done));
        }

        if (counts.containsKey(TaskStatus.active))
        statistic.setActiveChildren(statistic.getActiveChildren() + counts.get(TaskStatus.active));
        if (counts.containsKey(TaskStatus.progressing))
        statistic.setProgressingChildren(statistic.getProgressingChildren() + counts.get(TaskStatus.progressing));
        if (counts.containsKey(TaskStatus.done))
        statistic.setDoneChildren(statistic.getDoneChildren() + counts.get(TaskStatus.done));
        dao.save(statistic);

        int size = tasks.size();
//        if (parent.getChildren() != size){
//            parent.setChildren(size);
//            if (size > 0){
//                parent.setStatus(TaskStatus.folder);
//            } else {
//                parent.setStatus(TaskStatus.active);
//            }
//            dao.save(parent);
//        }
        if (!parent.isTop()){
            System.out.println("\t...Have parent");
//            checkParenthood(parent.getParent(), user);
        }
    }

    public static void main(String[] args) {

        Hibernator hibernator = Hibernator.getInstance();
        dbDAO dao = dbDAOService.getDao();

        for (Project project : hibernator.query(Project.class, null)){
            System.out.println(project.getTitle());
            for (Task task : dao.getTaskByUserAndParent(project.getOwner(), project.getTask().getCategory())){
                System.out.print("\t - " + task.getParent().getName() + "-");
                System.out.println(task.getTitle());
            }
        }
//        for (Task task : hibernator.query(Task.class, null)){
//            if (task.getCategory() == null) {
//                TransactionCategory category = new TransactionCategory();
//                category.setName(task.getTitle());
//                category.setOwner(task.getOwner());
//                hibernator.save(category);
//                task.setCategory(category);
//                hibernator.save(task);
//            }
//            Task parent = task.getParent();
//            if (parent != null){
//                TransactionCategory category = task.getCategory();
//                category.setParent(parent.getCategory());
//                hibernator.save(category);
//            }
//        }
        HibernateSessionFactory.shutdown();
    }

    public static void checkSpellTime(Task task) {
        List<TimeLog> logs = dao.getTimeLogs(task);
        int spend = 0;
        for (TimeLog log : logs){
            spend += log.getSpend();
        }
        TaskStatistic statistic = task.getStatistic();
        if (statistic == null){
            statistic = new TaskStatistic();
            statistic.setTask(task);
        }
        statistic.setSpendTime(spend);
        dao.save(statistic);
    }
}
