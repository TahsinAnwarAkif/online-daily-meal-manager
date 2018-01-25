package net.therap.OnlineDailyMealManager.controller;

import net.therap.OnlineDailyMealManager.dao.MealDao;
import net.therap.OnlineDailyMealManager.model.Meal;
import net.therap.OnlineDailyMealManager.utils.Utils;
import net.therap.OnlineDailyMealManager.validators.MealValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.therap.OnlineDailyMealManager.utils.Constants.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.OK;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.getEnumByMessage;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.ALERT_IF_SUCCESS;
import static net.therap.OnlineDailyMealManager.utils.Mappings.*;

/**
 * @author anwar
 * @since 1/26/18
 */
public class MealController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MealDao mealDao;
    private Utils utils;
    private MealValidator mealValidator;

    public void init() throws ServletException {
        super.init();
        this.mealDao = new MealDao();
        this.utils = new Utils();
        this.mealValidator = new MealValidator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String methodName = request.getParameter("METHOD");
        METHODS method = methodName != null
                ? METHODS.valueOf(methodName)
                : null;
        if (method == null) {
            this.doGet(request, response);
            return;
        }
        switch (method) {
            case POST: {
                String isAddingValidMessage = mealValidator.isAddingValid(new Meal(request.getParameter("name")));
                if (isAddingValidMessage.equals(OK.getMessage())) {
                    mealDao.addMeal(new Meal(request.getParameter("name")));
                    request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                            .replace("{name}", request.getParameter("name"))
                            .replace("{operation}", "created"));
                }
                forwardReqAndResBasedOnResult(request, response, isAddingValidMessage, CREATE_MEAL_VIEW_URL);
                break;
            }
            case PUT: {
                request.setAttribute("selectedMeal", request.getParameter("id") != null
                        ? mealDao.findOneMeal(Integer.parseInt(request.getParameter("id")))
                        : "");
                this.doPut(request, response);
                break;
            }
            case DELETE: {
                request.setAttribute("selectedMeal", request.getParameter("id") != null
                        ? mealDao.findOneMeal(Integer.parseInt(request.getParameter("id")))
                        : "");
                this.doDelete(request, response);
                break;
            }
            default: {
                break;
            }
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String isUpdatingValidMessage = mealValidator.isUpdatingValid((Meal) request.getAttribute("selectedMeal"),
                new Meal(request.getParameter("newName")));
        if (isUpdatingValidMessage.equals(OK.getMessage())) {
            mealDao.updateMeal((Meal) request.getAttribute("selectedMeal"),
                    new Meal(request.getParameter("newName")));
            request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                    .replace("{name}", request.getParameter("oldName"))
                    .replace("{operation}", "updated"));
        }
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("name", request.getParameter("oldName"));
        forwardReqAndResBasedOnResult(request, response, isUpdatingValidMessage, UPDATE_MEAL_VIEW_URL);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isDeletingValidMessage = mealValidator.isDeletingItemInvalid((Meal) request.getAttribute("selectedMeal"));
        if (isDeletingValidMessage.equals(OK.getMessage())) {
            mealDao.deleteMeal((Meal) request.getAttribute("selectedMeal"));
            request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                    .replace("{name}", request.getParameter("name"))
                    .replace("{operation}", "deleted"));
        }
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("name", request.getParameter("oldName"));
        forwardReqAndResBasedOnResult(request, response, isDeletingValidMessage, SHOW_MEALS_VIEW_URL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("METHOD");
        request.setAttribute("allMeals", mealDao.viewAllMeals());
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("name", request.getParameter("name"));
        METHODS method = methodName != null
                ? METHODS.valueOf(methodName)
                : null;
        utils.forwardReqAndResBasedOnInput(request, response, method,
                SHOW_MEALS_VIEW_URL, CREATE_MEAL_VIEW_URL, UPDATE_MEAL_VIEW_URL);
    }

    private void forwardReqAndResBasedOnResult(HttpServletRequest request, HttpServletResponse response,
                                               String isActionPerformedResult, String goToIfErrorsExist) throws ServletException, IOException {
        OPERATION_RESULTS result = getEnumByMessage(isActionPerformedResult);
        switch (result) {
            case OK: {
                request.setAttribute("allMeals", mealDao.viewAllMeals());
                request.getRequestDispatcher(SHOW_MEALS_VIEW_URL).forward(request, response);
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
}
