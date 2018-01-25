package net.therap.OnlineDailyMealManager.dao;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.model.Item;

import java.util.List;

import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.DELETE_OPERATION;
import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.SAVE_OR_UPDATE_OPERATION;

/**
 * @author anwar
 * @since 1/15/18
 */
public class ItemDao {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();

    public List<Item> viewAllItems() {
        return (List<Item>) (List<?>) commonUtilsForDao.findAllByColumnNames(new Item());
    }

    public Item findOneItem(int id) {
        return (Item) commonUtilsForDao.findOneByColumnNames(new Item(), new Pair<String, Object>("id", id));
    }

    public Item addAnItem(Item item) {
        commonUtilsForDao.saveOrUpdateOrDelete(SAVE_OR_UPDATE_OPERATION, item);
        return item;
    }

    public Item updateAnItem(Item oldItem, Item updatedItem) {
        updatedItem.setId(oldItem.getId());
        commonUtilsForDao.saveOrUpdateOrDelete(SAVE_OR_UPDATE_OPERATION, updatedItem);
        return updatedItem;
    }

    public void deleteAnItem(Item item) {
        commonUtilsForDao.saveOrUpdateOrDelete(DELETE_OPERATION, item);
    }
}
