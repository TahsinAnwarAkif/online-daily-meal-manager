package net.therap.OnlineDailyMealManager.dao;

import javafx.util.Pair;
import net.therap.OnlineDailyMealManager.model.Meal;

import java.util.List;

import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.DELETE_OPERATION;
import static net.therap.OnlineDailyMealManager.utils.Constants.DB_OPERATIONS.SAVE_OR_UPDATE_OPERATION;

/**
 * @author anwar
 * @since 1/15/18
 */
public class MealDao {

    private CommonUtilsForDao commonUtilsForDao = new CommonUtilsForDao();

    public List<Meal> viewAllMeals() {
        return (List<Meal>) (List<?>) commonUtilsForDao.findAllByColumnNames(new Meal());
    }

    public Meal findOneMeal(int id) {
        return (Meal) commonUtilsForDao.findOneByColumnNames(new Meal(), new Pair<String, Object>("id", id));
    }

    public Meal addMeal(Meal meal) {
        commonUtilsForDao.saveOrUpdateOrDelete(SAVE_OR_UPDATE_OPERATION, meal);
        return meal;
    }

    public Meal updateMeal(Meal oldMeal, Meal updatedMeal) {
        updatedMeal.setId(oldMeal.getId());
        commonUtilsForDao.saveOrUpdateOrDelete(SAVE_OR_UPDATE_OPERATION, updatedMeal);
        return updatedMeal;
    }

    public void deleteMeal(Meal meal) {
        commonUtilsForDao.saveOrUpdateOrDelete(DELETE_OPERATION, meal);
    }
}
