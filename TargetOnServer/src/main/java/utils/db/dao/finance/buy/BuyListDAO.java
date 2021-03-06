package utils.db.dao.finance.buy;

import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
import entity.user.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BuyListDAO {
    List<BuyList> getUserList(User user);
    BuyList getList(Object id);
    void saveList(BuyList list);

    BuyListItem getItemByHeader(Header header);

    void removeItems(Collection<BuyListItem> items);
    List<BuyList> findList(String key, User user);
    void removeItem(BuyListItem item);
    void removeList(BuyList list);

    BuyList getBaseList(Header header);

    void saveItems(Set<BuyListItem> itemSet);

    void saveItem(BuyListItem item);

    BuyListItem getItem(Object id);
}
