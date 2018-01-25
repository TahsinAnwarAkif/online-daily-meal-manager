package net.therap.OnlineDailyMealManager.exceptions;

import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.ITEM_NOT_AVAILABLE_OR_ALREADY_EXISTS_IN_MEAL_ERROR;

/**
 * @author anwar
 * @since 1/30/18
 */
public class ItemMealInvalidException extends Exception {

    public ItemMealInvalidException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return ITEM_NOT_AVAILABLE_OR_ALREADY_EXISTS_IN_MEAL_ERROR.getMessage();
    }
}
