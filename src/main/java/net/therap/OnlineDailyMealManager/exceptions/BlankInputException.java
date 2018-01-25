package net.therap.OnlineDailyMealManager.exceptions;

import static net.therap.OnlineDailyMealManager.utils.Constants.*;

/**
 * @author anwar
 * @since 1/27/18
 */
public class BlankInputException extends Exception {

    public BlankInputException(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return OPERATION_RESULTS.BLANK_INPUT_ERROR.getMessage();
    }
}
