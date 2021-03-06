package controllers.finances;

import constants.Keys;
import constants.UrlLinks;
import controllers.Page;
import entity.task.Unit;
import subscribe.Subscribe;
import utils.db.dao.finance.transactions.TransactionDAO;
import utils.db.dao.finance.transactions.TransactionDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

/**
 * Created by DELL on 06.07.2020.
 */
@WebServlet(UrlLinks.FINANCES)
public class FinancePage extends Page {
    private static final String _TITLE = "title.finances";
    private static final String _CONTENT = "/pages/finances/financesPage.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(TRANSACTION_EDIT, UrlLinks.TRANSACTION_EDIT);
        req.setAttribute(FAST_TRANSACTION_EDIT, UrlLinks.FAST_TRANSACTION_EDIT);
        req.setAttribute(ACCOUNT_EDIT, UrlLinks.ACCOUNT_EDIT);
        req.setAttribute(ACCOUNT_EXTRACT, UrlLinks.ACCOUNT_EXTRACT);
        req.setAttribute(BUY_LIST_EDIT, UrlLinks.BUY_LIST_EDIT);
        req.setAttribute(TRANSACTION_SUBSCRIBE, Subscribe.transactions);
        req.setAttribute(TRANSACTION_LIMIT, TransactionDAO.LIMIT);
        req.setAttribute(ACCOUNT_SUBSCRIBE, Subscribe.accounts);
        req.setAttribute(Keys.CATEGORIES, UrlLinks.CATEGORY_PAGE);
        req.setAttribute(BUY_LIST_SUBSCRIBE, Subscribe.buy);
        req.setAttribute(REMOVE_BUY_LIST, UrlLinks.BUY_LIST_REMOVE);
        req.setAttribute(Keys.REPORT_OF_MONTH, UrlLinks.REPORT_OF_MONTH);
        req.setAttribute(UNITS, Unit.values());
        show(req, resp);
    }
}
