package net.therap.OnlineDailyMealManager.exceptions;

import static net.therap.OnlineDailyMealManager.utils.Constants.*;


/**
 * @author anwar
 * @since 1/27/18
 */
public class InvalidDayException extends Exception {

    public InvalidDayException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return OPERATION_RESULTS.INVALID_DAY_ERROR.getMessage();
    }
}
