package entity.budget;

import constants.Keys;
import entity.user.User;
import entity.user.UserAccess;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@Entity
@Table(name = Keys.BUDGETS)
public class Budget implements Keys {

    private int id;
    private String title;
    private BudgetSize budgetSize;
    private BudgetType budgetType;
    private float budgetSum;
    private String currency;
    private User owner;
    private Timestamp create;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = TITLE)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = SIZE)
    public BudgetSize getBudgetSize() {
        return budgetSize;
    }
    public void setBudgetSize(BudgetSize budgetSize) {
        this.budgetSize = budgetSize;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public BudgetType getBudgetType() {
        return budgetType;
    }
    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }

    @Basic
    @Column(name = SUM)
    public float getBudgetSum() {
        return budgetSum;
    }
    public void setBudgetSum(float budgetSum) {
        this.budgetSum = budgetSum;
    }

    @Basic
    @Column(name = CURRENCY)
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @OneToOne
    @JoinColumn(name = OWNER)
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = CREATED)
    public Timestamp getCreate() {
        return create;
    }

    public void setCreate(Timestamp create) {
        this.create = create;
    }
}
