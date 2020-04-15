package utils;

import api.socket.UpdateUtil;
import entity.budget.Budget;
import entity.budget.BudgetPoint;
import entity.budget.PointScale;
import entity.transactions.Transaction;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
public class BudgetCalculator {

    dbDAO dao = dbDAOService.getDao();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public void calculate(Budget budget, Transaction transaction) {
        Date date = transaction.getDate();
        BudgetPoint budgetPoint = dao.getBudgetPoint(budget, date, PointScale.day);
        if (budgetPoint == null){
            budgetPoint = new BudgetPoint();
            budgetPoint.setDate(date);
            budgetPoint.setBudget(budget);
            budgetPoint.setScale(PointScale.day);
        }
        float amount = 0;
        for (Transaction t : dao.getTransactionsByBudget(budget, date)){
            amount += t.getTotalSum();
        }
        budgetPoint.setQuantity(amount);
        dao.save(budgetPoint);

        calculatePointByPoint(date, budget, PointScale.week);
        calculateBudget(budget);
    }

    public void calculateBudget(Budget budget) {
        float amount = 0;
        for (BudgetPoint point : dao.getBudgetPoints(null, null, budget,PointScale.year)){
            amount += point.getQuantity();
        }
        if (budget.getBudgetSum() != amount) {
            budget.setBudgetSum(amount);
            dao.save(budget);
            try {
                updateUtil.onSave(budget);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void calculatePointByPoint(Date date, Budget budget, PointScale scale) {
        LocalDate localDate = date.toLocalDate();
        LocalDate beginDate = getBeginDate(localDate, scale);
        LocalDate endDate = getEndDate(localDate, scale);

        BudgetPoint budgetPoint = dao.getBudgetPoint(budget, Date.valueOf(beginDate), scale);
        if (budgetPoint == null){
            budgetPoint = new BudgetPoint();
            budgetPoint.setDate(Date.valueOf(beginDate));
            budgetPoint.setBudget(budget);
            budgetPoint.setScale(scale);
        }
        float amount = 0;
        for (BudgetPoint point : dao.getBudgetPoints(Date.valueOf(beginDate), Date.valueOf(endDate), budget, prevScale(scale))){
            amount += point.getQuantity();
        }
        budgetPoint.setQuantity(amount);
        dao.save(budgetPoint);
        PointScale next = nextScale(scale);
        if (next != scale){
            calculatePointByPoint(date, budget, next);
        }
    }

    public static synchronized LocalDate getBeginDate(LocalDate date, PointScale scale){
        int minus;
        if (scale == PointScale.week) {
            minus = Math.min(toBegin(date, scale), toBegin(date, PointScale.month));
        } else {
            minus = toBegin(date, scale);
        }

        return date.minusDays(minus);
    }

    public static synchronized LocalDate getEndDate(LocalDate date, PointScale scale){
        int plus;
        if (scale == PointScale.week) {
            plus = Math.min(toEnd(date, scale), toEnd(date, PointScale.month));
        } else {
            plus = toEnd(date, scale);
        }
        return date.plusDays(plus);
    }

    public static int toBegin(LocalDate date, PointScale scale){
        switch (scale){
            case week:
                return date.getDayOfWeek().getValue() - 1;
            case month:
                return date.getDayOfMonth() - 1;
            case year:
                return date.getDayOfYear() - 1;
            default:
                return 0;
        }
    }

    public static int toEnd(LocalDate date, PointScale scale){
        switch (scale){
            case week:
                return 7 - date.getDayOfWeek().getValue();
            case month:
                return date.lengthOfMonth() - date.getDayOfMonth();
            case year:
                return date.lengthOfYear() - date.getDayOfYear();
            default:
                return 0;
        }
    }

    public PointScale prevScale(PointScale scale){
        switch (scale){
            case year:
                return PointScale.month;
            case month:
                return PointScale.week;
            case week:
                return PointScale.day;
            default:
                return scale;
        }
    }

    public static PointScale nextScale(PointScale scale){
        switch (scale){
            case day:
                return PointScale.week;
            case week:
                return PointScale.month;
            case month:
                return PointScale.year;
            default:
                return scale;
        }
    }
}
