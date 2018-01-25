package net.therap.OnlineDailyMealManager.exceptions;

import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.MEAL_NOT_AVAILABLE_OR_ALREADY_EXISTS;

/**
 * @author anwar
 * @since 1/29/18
 */
public class MealInvalidException extends Exception {

    public MealInvalidException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return MEAL_NOT_AVAILABLE_OR_ALREADY_EXISTS.getMessage();
    }
}
