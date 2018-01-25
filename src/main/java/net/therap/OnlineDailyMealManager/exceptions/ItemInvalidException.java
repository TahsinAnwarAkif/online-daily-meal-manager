package net.therap.OnlineDailyMealManager.exceptions;

import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.ITEM_NOT_AVAILABLE_OR_ALREADY_EXISTS;

/**
 * @author anwar
 * @since 1/17/18
 */
public class ItemInvalidException extends Exception {

    public ItemInvalidException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return ITEM_NOT_AVAILABLE_OR_ALREADY_EXISTS.getMessage();
    }
}
