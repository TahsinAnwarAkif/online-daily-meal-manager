package net.therap.OnlineDailyMealManager.filters;

import net.therap.OnlineDailyMealManager.dao.UserDao;
import net.therap.OnlineDailyMealManager.model.User;
import net.therap.OnlineDailyMealManager.validators.UserValidator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static net.therap.OnlineDailyMealManager.utils.Constants.BUTTONS.*;
import static net.therap.OnlineDailyMealManager.utils.Constants.OPERATION_RESULTS.OK;
import static net.therap.OnlineDailyMealManager.utils.Mappings.LOGIN_CONTROLLER_URL;

/**
 * @author anwar
 * @since 1/25/18
 */
public class AuthFilter implements Filter {

    private FilterConfig filterConfig;
    private UserDao userDao;
    private UserValidator userValidator;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        this.userDao = new UserDao();
        this.userValidator = new UserValidator();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String inputtedName = (String) session.getAttribute("userName");
        String inputtedPassword = (String) session.getAttribute("userPassword");
        String isUserValid = userValidator.isUserValid(new User(inputtedName, inputtedPassword), session);
        User foundUser = null;
        if (OK.getMessage().equals(isUserValid)) {
            foundUser = userDao.findOneByName(inputtedName);
        }
        if (foundUser != null) {
            synchronized (session) {
                session.setAttribute("userRole", foundUser.getRole().getName());
                this.prepareUserMenuBasedOnRole(session, foundUser);
            }
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + LOGIN_CONTROLLER_URL);
        }
    }

    private void prepareUserMenuBasedOnRole(HttpSession session, User user) {
        session.setAttribute("showItems", SHOW_ITEMS_BUTTON.getName());
        session.setAttribute("showMeals", SHOW_A_DAYS_MEAL_BUTTON.getName());
        session.setAttribute("showItemMeals", SHOW_ITEM_MEAL_BUTTON.getName());
        if (user.getRole().getName().equals("admin")) {
            session.setAttribute("create", CREATE_BUTTON.getName());
            session.setAttribute("update", UPDATE_BUTTON.getName());
            session.setAttribute("delete", DELETE_BUTTON.getName());
        }
    }

    @Override
    public void destroy() {

    }
}
