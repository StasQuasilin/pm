package utils.db.dao.finance.transactions;

import entity.finance.transactions.Transaction;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionPoint;
import entity.user.User;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.hibernate.DateContainers.LE;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class TransactionDAOImpl implements TransactionDAO {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final Updater updater = new Updater();
    private final HashMap<String, Object> args = new HashMap<>();

    @Override
    public List<Transaction> getUserTransactions(User user) {
        args.put(OWNER, user);
        args.put(DATE, new LE(Date.valueOf(LocalDate.now().plusMonths(1))));
        return hibernator.query(Transaction.class, args, LIMIT);
    }

    @Override
    public Transaction getTransaction(Object id) {
        return hibernator.get(Transaction.class, ID, id);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        hibernator.save(transaction);
        updater.update(Subscribe.transactions, transaction, transaction.getOwner());

    }

    @Override
    public List<TransactionDetail> getDetails(Object transactionId) {
        return hibernator.query(TransactionDetail.class, TRANSACTION, transactionId);
    }

    @Override
    public void saveDetail(TransactionDetail detail) {
        hibernator.save(detail.getHeader());
        hibernator.save(detail);
    }

    @Override
    public void removeTransactions(User user) {
        for (Transaction transaction : hibernator.query(Transaction.class, "category/owner", user)){
            hibernator.remove(TransactionPoint.class, "transaction", transaction.getId());
            hibernator.remove(transaction);
        }
    }

}
