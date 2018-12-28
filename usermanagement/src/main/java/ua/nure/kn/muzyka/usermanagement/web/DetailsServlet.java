package ua.nure.kn.muzyka.usermanagement.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailsServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        if (req.getParameter("backButton") != null) {
            req.getSession(true).removeAttribute("user");
            req.getRequestDispatcher("/browse").forward(req, resp);
        } else {
            req.getRequestDispatcher("/details.jsp").forward(req, resp);
        }
    }
}