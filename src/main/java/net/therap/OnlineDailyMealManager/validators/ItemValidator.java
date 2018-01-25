package net.therap.OnlineDailyMealManager.validators;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.dao.CommonUtilsForDao;
import net.therap.OnlineDailyMealManager.exceptions.BlankInputException;
import net.therap.OnlineDailyMealManager.exceptions.ItemInvalidException;
import net.therap.OnlineDailyMealManager.model.Item;

import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS;
import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS.CHECK_IF_ALREADY_EXISTS;
import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS.CHECK_IF_NOT_AVAILABLE;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.BLANK_INPUT;

/**
 * @author anwar
 * @since 2/4/18
 */
public class ItemValidator {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();

    public String isAddingValid(Item item) {
        try {
            isItemAvailable(item, CHECK_IF_ALREADY_EXISTS);
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (ItemInvalidException e) {
            return ITEM_ALREADY_EXISTS_ERROR.getMessage();
        }
        return OK.getMessage();
    }

    public String isUpdatingValid(Item oldItem, Item updatedItem) {
        try {
            isItemAvailable(oldItem, CHECK_IF_NOT_AVAILABLE);
            isItemAvailable(updatedItem, CHECK_IF_ALREADY_EXISTS);
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (ItemInvalidException e) {
            return getEnumByMessage(e.getMessage()).getMessage();
        }
        return OK.getMessage();
    }

    public String isDeletingItemInvalid(Item item) {
        try {
            isItemAvailable(item, CHECK_IF_NOT_AVAILABLE);
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (ItemInvalidException e) {
            return getEnumByMessage(e.getMessage()).getMessage();
        }
        return OK.getMessage();
    }

    public Item isItemAvailable(Item item, CHECKS check) throws ItemInvalidException, BlankInputException {
        Item fetchedItem = fetchItem(item);
        if (BLANK_INPUT.getValue().equals(item.getName())) {
            throw new BlankInputException(BLANK_INPUT_ERROR.getMessage());
        }
        switch (check) {
            case CHECK_IF_ALREADY_EXISTS: {
                if (fetchedItem != null) {
                    throw new ItemInvalidException(ITEM_ALREADY_EXISTS_ERROR.getMessage());
                }
                break;
            }
            case CHECK_IF_NOT_AVAILABLE: {
                if (fetchedItem == null) {
                    throw new ItemInvalidException(ITEM_NOT_AVAILABLE_ERROR.getMessage());
                }
                break;
            }
            default: {
                break;
            }
        }
        return fetchedItem;
    }

    private Item fetchItem(Item item) {
        return (Item) commonUtilsForDao.findOneByColumnNames(item,
                new Pair<String, Object>("name", item.getName()));
    }
}
