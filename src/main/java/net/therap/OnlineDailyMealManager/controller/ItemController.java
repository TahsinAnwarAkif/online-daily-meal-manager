package net.therap.OnlineDailyMealManager.controller;

import net.therap.OnlineDailyMealManager.dao.ItemDao;
import net.therap.OnlineDailyMealManager.model.Item;
import net.therap.OnlineDailyMealManager.utils.Utils;
import net.therap.OnlineDailyMealManager.validators.ItemValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.therap.OnlineDailyMealManager.utils.Constants.METHODS;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.OK;
import static net.therap.OnlineDailyMealManager.utils.Constants.UTILITY_CHECKS.ALERT_IF_SUCCESS;
import static net.therap.OnlineDailyMealManager.utils.Mappings.*;

/**
 * @author anwar
 * @since 1/26/18
 */
public class ItemController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ItemDao itemDao;
    private Utils utils;
    private ItemValidator itemValidator;

    public void init() throws ServletException {
        super.init();
        this.itemDao = new ItemDao();
        this.utils = new Utils();
        this.itemValidator = new ItemValidator();
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
                String isAddingValidMessage = itemValidator.isAddingValid(new Item(request.getParameter("name")));
                if (isAddingValidMessage.equals(OK.getMessage())) {
                    itemDao.addAnItem(new Item(request.getParameter("name")));
                    request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                            .replace("{name}", request.getParameter("name"))
                            .replace("{operation}", "created"));
                }
                this.forwardReqAndResBasedOnResult(request, response, isAddingValidMessage, CREATE_ITEM_VIEW_URL);
                break;
            }
            case PUT: {
                request.setAttribute("selectedItem", request.getParameter("id") != null
                        ? itemDao.findOneItem(Integer.parseInt(request.getParameter("id")))
                        : "");
                this.doPut(request, response);
                break;
            }
            case DELETE: {
                request.setAttribute("selectedItem", request.getParameter("id") != null
                        ? itemDao.findOneItem(Integer.parseInt(request.getParameter("id")))
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
        String isUpdatingValidMessage = itemValidator.isUpdatingValid((Item) request.getAttribute("selectedItem"),
                new Item(request.getParameter("newName")));
        if (isUpdatingValidMessage.equals(OK.getMessage())) {
            itemDao.updateAnItem((Item) request.getAttribute("selectedItem"),
                    new Item(request.getParameter("newName")));
            request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                    .replace("{name}", request.getParameter("oldName"))
                    .replace("{operation}", "updated"));
        }
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("name", request.getParameter("oldName"));
        forwardReqAndResBasedOnResult(request, response, isUpdatingValidMessage, UPDATE_ITEM_VIEW_URL);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isDeletingValidMessage = itemValidator.isDeletingItemInvalid((Item) request.getAttribute("selectedItem"));
        if (isDeletingValidMessage.equals(OK.getMessage())) {
            itemDao.deleteAnItem((Item) request.getAttribute("selectedItem"));
            request.setAttribute("alertNotificationForSuccess", ALERT_IF_SUCCESS.getValue()
                    .replace("{name}", request.getParameter("name"))
                    .replace("{operation}", "deleted"));
        }
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("name", request.getParameter("name"));
        this.forwardReqAndResBasedOnResult(request, response, isDeletingValidMessage, ITEMS_VIEW_URL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("METHOD");
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("name", request.getParameter("name"));
        request.setAttribute("allItems", itemDao.viewAllItems());
        METHODS method = methodName != null
                ? METHODS.valueOf(methodName)
                : null;
        utils.forwardReqAndResBasedOnInput(request, response, method,
                ITEMS_VIEW_URL, CREATE_ITEM_VIEW_URL, UPDATE_ITEM_VIEW_URL);
    }

    private void forwardReqAndResBasedOnResult(HttpServletRequest request, HttpServletResponse response,
                                               String isActionPerformedResult, String goToIfErrorsExist) throws ServletException, IOException {
        OPERATION_RESULTS result = OPERATION_RESULTS.getEnumByMessage(isActionPerformedResult);
        switch (result) {
            case OK: {
                request.setAttribute("allItems", itemDao.viewAllItems());
                request.getRequestDispatcher(ITEMS_VIEW_URL).forward(request, response);
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
