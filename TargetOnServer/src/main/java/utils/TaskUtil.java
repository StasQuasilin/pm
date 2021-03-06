package utils;

import entity.finance.category.Header;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.task.*;
import entity.user.User;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;

public class TaskUtil {

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final Updater updater = new Updater();

    public void checkPossibility(Task task){
//        final List<Task> tasksByParent = taskDAO.getTasksByParent(task.getHeader());

//        TaskStatus status = tasksByParent.size() == 0 ? TaskStatus.active : task.isDoneIfChildren() ? TaskStatus.done : TaskStatus.active;
//
//        for (Task t : tasksByParent) {
//            if (t.getStatus() != TaskStatus.done) {
//                status = TaskStatus.impossible;
//                break;
//            }
//        }
//        task.setStatus(status);
    }

    public void calculateSpendTime(Header header) {

        long time = 0;
        for (TimeLog log : taskDAO.getTimeLogList(header)){
            time += log.getLength();
            System.out.println("\t+ " + log.getLength());
        }
        for (TaskStatistic statistic : taskDAO.getChildrenStatistic(header)){
            time += statistic.getSpendTime();
            System.out.println("\t+" + statistic.getHeader().getTitle() + ":" + statistic.getSpendTime());
        }
        final TaskStatistic statistic = taskDAO.getStatisticOrCreate(header);
        statistic.setSpendTime(time);
        taskDAO.saveStatistic(statistic);

        final Header parent = header.getParent();
        if (parent != null){
            calculateSpendTime(parent);
        }
        updateStatistic(header);

    }

    public void updateStatistic(Header header) {
        TaskStatistic statistic = taskDAO.getStatisticOrCreate(header);
        statistic.cleanChildren();

        for (Task task :  taskDAO.getTasksByParent(header)){
            final TaskStatistic childStatistic = taskDAO.getStatistic(task.getHeader().getId());
            if (childStatistic != null){
                statistic.add(childStatistic);
            }
            final TaskStatus status = task.getStatus();
            if (status == TaskStatus.active){
                statistic.plusActiveChild();
            } else if(status == TaskStatus.progressing){
                statistic.plusProgressingChild();
            } else if (status == TaskStatus.done){
                statistic.plusDoneChildren();
            } else {
                statistic.plusOtherChild();
            }
        }

        if (statistic.any()){
            taskDAO.saveStatistic(statistic);
            final Goal goal = goalDAO.getGoalByHeader(header.getId());
            if(goal != null){
                goal.setStatistic(statistic);
                updater.update(Subscribe.goal, goal, goal.getOwner());
                for (GoalMember member : goalDAO.getGoalMembers(goal)){
                    updater.update(Subscribe.goal, goal, member.getMember());
                }
            }
        }else if(statistic.getId() > 0){
            taskDAO.removeStatistic(statistic);
        }

        final Header parent = header.getParent();
        if(parent != null){
            updateStatistic(parent);
        }
    }

    public void addTaskDoer(Task task, User user) {
        TaskDoer pair = taskDAO.getTaskDoerPair(task, user);
        if(pair == null){
            pair = new TaskDoer();
            pair.setTask(task);
            pair.setDoer(user);
            taskDAO.saveTaskDoer(pair);
        }
    }


}
