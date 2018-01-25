package net.therap.OnlineDailyMealManager.validators;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.dao.CommonUtilsForDao;
import net.therap.OnlineDailyMealManager.exceptions.BlankInputException;
import net.therap.OnlineDailyMealManager.exceptions.MealInvalidException;
import net.therap.OnlineDailyMealManager.model.Meal;

import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS;
import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS.CHECK_IF_ALREADY_EXISTS;
import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS.CHECK_IF_NOT_AVAILABLE;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.BLANK_INPUT;

/**
 * @author anwar
 * @since 1/29/18
 */
public class MealValidator {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();

    public String isAddingValid(Meal meal) {
        try {
            isMealAvailable(meal, CHECK_IF_ALREADY_EXISTS);
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (MealInvalidException e) {
            return MEAL_ALREADY_EXISTS_ERROR.getMessage();
        }
        return OK.getMessage();
    }

    public String isUpdatingValid(Meal oldMeal, Meal updatedMeal) {
        try {
            isMealAvailable(oldMeal, CHECK_IF_NOT_AVAILABLE);
            isMealAvailable(updatedMeal, CHECK_IF_ALREADY_EXISTS);
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (MealInvalidException e) {
            return getEnumByMessage(e.getMessage()).getMessage();
        }
        return OK.getMessage();
    }

    public String isDeletingItemInvalid(Meal meal) {
        try {
            isMealAvailable(meal, CHECK_IF_NOT_AVAILABLE);
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (MealInvalidException e) {
            return getEnumByMessage(e.getMessage()).getMessage();
        }
        return OK.getMessage();
    }

    public Meal isMealAvailable(Meal meal, CHECKS check) throws MealInvalidException, BlankInputException {
        Meal fetchedMeal = fetchMeal(meal);
        if (BLANK_INPUT.getValue().equals(meal.getName())) {
            throw new BlankInputException(BLANK_INPUT_ERROR.getMessage());
        }
        switch (check) {
            case CHECK_IF_ALREADY_EXISTS: {
                if (fetchedMeal != null) {
                    throw new MealInvalidException(MEAL_ALREADY_EXISTS_ERROR.getMessage());
                }
                break;
            }
            case CHECK_IF_NOT_AVAILABLE: {
                if (fetchedMeal == null) {
                    throw new MealInvalidException(MEAL_NOT_AVAILABLE_ERROR.getMessage());
                }
                break;
            }
            default: {
                break;
            }
        }
        return fetchedMeal;
    }

    private Meal fetchMeal(Meal meal) {
        return (Meal) commonUtilsForDao.findOneByColumnNames(meal,
                new Pair<String, Object>("name", meal.getName()));
    }
}
