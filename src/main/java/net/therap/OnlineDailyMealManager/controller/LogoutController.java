package net.therap.OnlineDailyMealManager.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static net.therap.OnlineDailyMealManager.utils.Mappings.LOGIN_CONTROLLER_URL;

/**
 * @author anwar
 * @since 1/28/18
 */
public class LogoutController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + LOGIN_CONTROLLER_URL);
    }
}
