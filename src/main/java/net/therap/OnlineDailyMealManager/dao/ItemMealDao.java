package net.therap.OnlineDailyMealManager.dao;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.model.ItemMeal;
import net.therap.OnlineDailyMealManager.model.Meal;

import java.util.List;

import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.DELETE_OPERATION;
import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.SAVE_OR_UPDATE_OPERATION;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.OK;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.ALL_ITEM_MEAL;

/**
 * @author anwar
 * @since 1/30/18
 */
public class ItemMealDao {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();

    public List<ItemMeal> viewItemMealsByDayOrMealOrBoth(String day, Meal meal) {
        if (ALL_ITEM_MEAL.getValue().equals(meal.getName())) {
            return (List<ItemMeal>) (List<?>) commonUtilsForDao.findAllByColumnNames(new ItemMeal(),
                    new Pair("day", day));
        } else {
            return (List<ItemMeal>) (List<?>) commonUtilsForDao.findAllByColumnNames(new ItemMeal(),
                    new Pair<String, Object>("day", day),
                    new Pair<String, Object>("meal", (Meal) commonUtilsForDao.findOneByColumnNames(meal, new Pair<String, Object>("name", meal.getName()))));
        }
    }

    public ItemMeal findOneItemMeal(int id) {
        return (ItemMeal) commonUtilsForDao.findOneByColumnNames(new ItemMeal(), new Pair<String, Object>("id", id));
    }

    public String addItemMeal(ItemMeal... itemMeals) {
        commonUtilsForDao.saveOrUpdateOrDelete(SAVE_OR_UPDATE_OPERATION, itemMeals);
        return OK.getMessage();
    }

    public String deleteFromItemMeal(ItemMeal... itemMeals) {
        commonUtilsForDao.saveOrUpdateOrDelete(DELETE_OPERATION, itemMeals);
        return OK.getMessage();
    }
}
