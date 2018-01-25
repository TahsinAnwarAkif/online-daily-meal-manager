package net.therap.OnlineDailyMealManager.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static net.therap.OnlineDailyMealManager.utils.Mappings.LOGIN_VIEW_URL;
import static net.therap.OnlineDailyMealManager.utils.Mappings.MENU_VIEW_URL;

/**
 * @author anwar
 * @since 1/23/18
 */
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(LOGIN_VIEW_URL).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }
        synchronized (session) {
            session.setAttribute("userName", request.getParameter("userName"));
            session.setAttribute("userPassword", request.getParameter("userPassword"));
            session.setMaxInactiveInterval(30*60);
        }
        request.getRequestDispatcher(MENU_VIEW_URL).forward(request, response);
    }
}