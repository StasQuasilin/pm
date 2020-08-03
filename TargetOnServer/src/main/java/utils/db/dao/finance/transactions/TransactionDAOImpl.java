package utils.db.dao.finance.transactions;

import entity.finance.category.Category;
import entity.finance.transactions.Transaction;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.Hibernator;
import utils.finances.TransactionUtil;

import java.util.List;

import static constants.Keys.*;

public class TransactionDAOImpl implements TransactionDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();


    @Override
    public List<Transaction> getUserTransactions(User user) {
        return hibernator.query(Transaction.class, TASK_OWNER, user);
    }

    @Override
    public Transaction getTransaction(Object id) {
        return hibernator.get(Transaction.class, ID, id);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        hibernator.save(transaction.getCategory());
        hibernator.save(transaction);
        updater.update(Subscribe.transactions, transaction, transaction.getOwner());

    }

    @Override
    public List<Transaction> getTransactionsByCategory(Category category, int limit) {
        return hibernator.query(Transaction.class, CATEGORY, category);
    }
}