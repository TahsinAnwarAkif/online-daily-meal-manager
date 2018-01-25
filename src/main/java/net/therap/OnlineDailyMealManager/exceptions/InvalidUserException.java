package net.therap.OnlineDailyMealManager.exceptions;

import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.USER_NOT_AVAILABLE_ERROR;

/**
 * @author anwar
 * @since 1/31/18
 */
public class InvalidUserException extends Exception {

    public InvalidUserException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return USER_NOT_AVAILABLE_ERROR.getMessage();
    }
}
