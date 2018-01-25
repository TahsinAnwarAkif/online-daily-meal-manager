package net.therap.OnlineDailyMealManager.controller;

import net.therap.OnlineDailyMealManager.dao.ItemDao;
import net.therap.OnlineDailyMealManager.dao.ItemMealDao;
import net.therap.OnlineDailyMealManager.dao.MealDao;
import net.therap.OnlineDailyMealManager.model.Item;
import net.therap.OnlineDailyMealManager.model.ItemMeal;
import net.therap.OnlineDailyMealManager.model.Meal;
import net.therap.OnlineDailyMealManager.utils.Utils;
import net.therap.OnlineDailyMealManager.validators.ItemMealValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static net.therap.OnlineDailyMealManager.utils.Constants.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.METHODS.valueOf;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.ALERT_IF_SUCCESS;
import static net.therap.OnlineDailyMealManager.utils.Mappings.CREATE_IN_ITEM_MEAL_VIEW_URL;
import static net.therap.OnlineDailyMealManager.utils.Mappings.SHOW_IN_ITEM_MEAL_VIEW_URL;

/**
 * @author anwar
 * @since 1/30/18
 */
public class ItemMealController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ItemDao itemDao;
    private MealDao mealDao;
    private ItemMealDao itemMealDao;
    private Utils utils;
    private ItemMealValidator itemMealValidator;

    public void init() throws ServletException {
        super.init();
        this.itemDao = new ItemDao();
        this.mealDao = new MealDao();
        this.itemMealDao = new ItemMealDao();
        this.utils = new Utils();
        this.itemMealValidator = new ItemMealValidator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("METHOD");
        METHODS method = methodName != null
                ? valueOf(methodName)
                : null;
        if (method == null) {
            this.doGet(request, response);
            return;
        }
        switch (method) {
            case POST: {
                String[] itemNamesPickedForAddition = request.getParameterValues("itemsToBeAdded");
                if (itemNamesPickedForAddition != null) {
                    ItemMeal[] toBeAdded = new ItemMeal[itemNamesPickedForAddition.length];
                    for (int idx = 0; idx < itemNamesPickedForAddition.length; idx++) {
                        toBeAdded[idx] = new ItemMeal(request.getParameter("day"),
                                new Item(itemNamesPickedForAddition[idx]),
                                new Meal(request.getParameter("mealName")));
                    }
                    String isAddingValidMessage = itemMealValidator.isAddingValid(toBeAdded);
                    if (isAddingValidMessage.equals(OK.getMessage())) {
                        itemMealDao.addItemMeal(toBeAdded);
                        request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                                .replace("{name}", String.join(",", itemNamesPickedForAddition))
                                .replace("{operation}", "added"));
                    }
                    this.forwardReqAndResBasedOnResult(request, response, isAddingValidMessage, CREATE_IN_ITEM_MEAL_VIEW_URL);
                } else {
                    request.setAttribute("error", NULL_CREATION_ERROR.getMessage());
                    forwardReqAndResBasedOnResult(request, response, NULL_CREATION_ERROR.getMessage(), SHOW_IN_ITEM_MEAL_VIEW_URL);
                }
                break;
            }
            case DELETE: {
                this.doDelete(request, response);
                break;
            }
            default: {
                break;
            }
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] idsPickedForDeletion = request.getParameterValues("toDeletes");
        if (idsPickedForDeletion != null) {
            String[] itemNamesToBeDeleted = new String[idsPickedForDeletion.length];
            ItemMeal[] toBeDeleted = new ItemMeal[idsPickedForDeletion.length];
            for (int idx = 0; idx < idsPickedForDeletion.length; idx++) {
                toBeDeleted[idx] = itemMealDao.findOneItemMeal(Integer.parseInt(idsPickedForDeletion[idx]));
                itemNamesToBeDeleted[idx] = toBeDeleted[idx].getItem().getName();
            }
            String isDeletingValidMessage = itemMealValidator.isDeletingValid(toBeDeleted);
            if (isDeletingValidMessage.equals(OK.getMessage())) {
                itemMealDao.deleteFromItemMeal(toBeDeleted);
                request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                        .replace("{name}", String.join(",", itemNamesToBeDeleted))
                        .replace("{operation}", "deleted"));
            }
            forwardReqAndResBasedOnResult(request, response, isDeletingValidMessage, SHOW_IN_ITEM_MEAL_VIEW_URL);
        } else {
            request.setAttribute("error", NULL_DELETION_ERROR.getMessage());
            forwardReqAndResBasedOnResult(request, response, NULL_DELETION_ERROR.getMessage(), SHOW_IN_ITEM_MEAL_VIEW_URL);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("METHOD");
        String inputtedDay = request.getParameter("day");
        Meal inputtedMeal = new Meal(request.getParameter("mealName"));
        initRequestAttributesAndViewItemMeals(request, inputtedDay, inputtedMeal);
        METHODS method = methodName != null
                ? METHODS.valueOf(methodName)
                : null;
        utils.forwardReqAndResBasedOnInput(request, response, method,
                SHOW_IN_ITEM_MEAL_VIEW_URL, CREATE_IN_ITEM_MEAL_VIEW_URL, null);
    }

    private void forwardReqAndResBasedOnResult(HttpServletRequest request, HttpServletResponse response,
                                               String isActionPerformedResult, String goToIfErrorsExist) throws ServletException, IOException {
        OPERATION_RESULTS result = getEnumByMessage(isActionPerformedResult);
        initRequestAttributesAndViewItemMeals(request, request.getParameter("day"),
                new Meal(request.getParameter("mealName")));
        switch (result) {
            case OK: {
                request.getRequestDispatcher(SHOW_IN_ITEM_MEAL_VIEW_URL).forward(request, response);
                break;
            }
            case NULL_DELETION_ERROR: {
                request.getRequestDispatcher(SHOW_IN_ITEM_MEAL_VIEW_URL).forward(request, response);
                break;
            }
            case NULL_CREATION_ERROR: {
                request.getRequestDispatcher(CREATE_IN_ITEM_MEAL_VIEW_URL).forward(request, response);
                break;
            }
            default: {
                request.setAttribute("alertNotificationForSuccess", null);
                request.setAttribute("error", isActionPerformedResult);
                request.getRequestDispatcher(goToIfErrorsExist).forward(request, response);
                break;
            }
        }
    }

    private void initRequestAttributesAndViewItemMeals(HttpServletRequest request, String selectedDay, Meal selectedMeal) {
        request.setAttribute("allDays", DAYS.getAllDays());
        request.setAttribute("allMeals", mealDao.viewAllMeals());
        request.setAttribute("allItems", itemDao.viewAllItems());
        if (selectedDay != null) {
            request.setAttribute("selectedDay", selectedDay);
            request.setAttribute("selectedMealName", selectedMeal.getName());
            String isInputDayAndMealValidMessage = itemMealValidator.isInputDayAndMealValid(selectedDay, selectedMeal);
            if (isInputDayAndMealValidMessage.equals(OK.getMessage())) {
                List<ItemMeal> itemMeals = itemMealDao.viewItemMealsByDayOrMealOrBoth(selectedDay, selectedMeal);
                request.setAttribute("allItemMeals", itemMeals);
            } else {
                request.setAttribute("error", isInputDayAndMealValidMessage);
            }
        }
    }
}
