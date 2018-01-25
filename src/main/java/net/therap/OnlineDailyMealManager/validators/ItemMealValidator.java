package net.therap.OnlineDailyMealManager.validators;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.dao.CommonUtilsForDao;
import net.therap.OnlineDailyMealManager.exceptions.*;
import net.therap.OnlineDailyMealManager.model.Item;
import net.therap.OnlineDailyMealManager.model.ItemMeal;
import net.therap.OnlineDailyMealManager.model.Meal;
import net.therap.OnlineDailyMealManager.utils.Constants.CHECKS;

import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS.CHECK_IF_ALREADY_EXISTS;
import static net.therap.OnlineDailyMealManager.utils.Constants.CHECKS.CHECK_IF_NOT_AVAILABLE;
import static net.therap.OnlineDailyMealManager.utils.Constants.DAYS;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.ALL_ITEM_MEAL;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.BLANK_INPUT;

/**
 * @author anwar
 * @since 2/4/18
 */
public class ItemMealValidator {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();
    private ItemValidator itemValidator = new ItemValidator();
    private MealValidator mealValidator = new MealValidator();

    public String isInputDayAndMealValid(String day, Meal meal) {
        try {
            isDayValid(day);
            if (!ALL_ITEM_MEAL.getValue().equals(meal.getName())) {
                mealValidator.isMealAvailable(meal, CHECK_IF_NOT_AVAILABLE);
            }
        } catch (BlankInputException e) {
            return BLANK_INPUT_ERROR.getMessage();
        } catch (InvalidDayException e) {
            return INVALID_DAY_ERROR.getMessage();
        } catch (MealInvalidException e) {
            return MEAL_NOT_AVAILABLE_ERROR.getMessage();
        }
        return OK.getMessage();
    }

    public String isAddingValid(ItemMeal... itemMeals) {
        for (int idx = 0; idx < itemMeals.length; idx++) {
            Item fetchedItem;
            Meal fetchedMeal;
            try {
                fetchedItem = itemValidator.isItemAvailable(itemMeals[idx].getItem(), CHECKS.CHECK_IF_NOT_AVAILABLE);
                fetchedMeal = mealValidator.isMealAvailable(itemMeals[idx].getMeal(), CHECKS.CHECK_IF_NOT_AVAILABLE);
                isItemAvailableInMeal(itemMeals[idx], CHECK_IF_ALREADY_EXISTS);
            } catch (BlankInputException e) {
                return BLANK_INPUT_ERROR.getMessage();
            } catch (InvalidDayException e) {
                return INVALID_DAY_ERROR.getMessage();
            } catch (ItemInvalidException e) {
                return ITEM_NOT_AVAILABLE_ERROR.getMessage();
            } catch (MealInvalidException e) {
                return MEAL_NOT_AVAILABLE_ERROR.getMessage();
            } catch (ItemMealInvalidException e) {
                return ITEM_MEAL_ALREADY_EXISTS_ERROR.getMessage();
            }
            itemMeals[idx].setItem(fetchedItem);
            itemMeals[idx].setMeal(fetchedMeal);
        }
        return OK.getMessage();
    }

    public String isDeletingValid(ItemMeal... itemMeals) {
        ItemMeal[] fetchedItemMeals = new ItemMeal[itemMeals.length];
        for (int idx = 0; idx < itemMeals.length; idx++) {
            try {
                itemValidator.isItemAvailable(itemMeals[idx].getItem(), CHECK_IF_NOT_AVAILABLE);
                mealValidator.isMealAvailable(itemMeals[idx].getMeal(), CHECK_IF_NOT_AVAILABLE);
                fetchedItemMeals[idx] = isItemAvailableInMeal(itemMeals[idx], CHECK_IF_NOT_AVAILABLE);
            } catch (BlankInputException e) {
                return BLANK_INPUT_ERROR.getMessage();
            } catch (InvalidDayException e) {
                return INVALID_DAY_ERROR.getMessage();
            } catch (ItemInvalidException e) {
                return ITEM_NOT_AVAILABLE_ERROR.getMessage();
            } catch (MealInvalidException e) {
                return MEAL_NOT_AVAILABLE_ERROR.getMessage();
            } catch (ItemMealInvalidException e) {
                return ITEM_MEAL_NOT_AVAILABLE_ERROR.getMessage();
            }
        }
        return OK.getMessage();
    }

    private ItemMeal isItemAvailableInMeal(ItemMeal itemMeal, CHECKS check) throws ItemMealInvalidException, BlankInputException, InvalidDayException {
        ItemMeal fetchedItemMeal = fetchItemMeal(itemMeal);
        if (BLANK_INPUT.getValue().equals(itemMeal.getItem().getName()) || BLANK_INPUT.getValue().equals(itemMeal.getDay())) {
            throw new BlankInputException(BLANK_INPUT_ERROR.getMessage());
        }
        this.isDayValid(itemMeal.getDay());
        switch (check) {
            case CHECK_IF_ALREADY_EXISTS: {
                if (fetchedItemMeal != null) {
                    throw new ItemMealInvalidException(ITEM_MEAL_ALREADY_EXISTS_ERROR.getMessage());
                }
                break;
            }
            case CHECK_IF_NOT_AVAILABLE: {
                if (fetchedItemMeal == null) {
                    throw new ItemMealInvalidException(ITEM_MEAL_NOT_AVAILABLE_ERROR.getMessage());
                }
                break;
            }
            default: {
                break;
            }
        }
        return fetchedItemMeal;
    }

    private void isDayValid(String input) throws InvalidDayException, BlankInputException {
        if (BLANK_INPUT.getValue().equals(input)) {
            throw new BlankInputException(BLANK_INPUT_ERROR.getMessage());
        }
        if (!DAYS.contains(input)) {
            throw new InvalidDayException(INVALID_DAY_ERROR.getMessage());
        }
    }

    private ItemMeal fetchItemMeal(ItemMeal itemMeal) {
        Item foundItem = (Item) commonUtilsForDao.findOneByColumnNames(itemMeal.getItem(),
                new Pair<String, Object>("name", itemMeal.getItem().getName()));
        Meal foundMeal = (Meal) commonUtilsForDao.findOneByColumnNames(itemMeal.getMeal(),
                new Pair<String, Object>("name", itemMeal.getMeal().getName()));
        ItemMeal foundItemMeal = (ItemMeal) commonUtilsForDao.findOneByColumnNames(itemMeal,
                new Pair<String, Object>("item", foundItem),
                new Pair<String, Object>("meal", foundMeal),
                new Pair<String, Object>("day", itemMeal.getDay()));
        return foundItemMeal;
    }
}
