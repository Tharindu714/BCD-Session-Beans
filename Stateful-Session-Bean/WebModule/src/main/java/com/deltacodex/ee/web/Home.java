package com.deltacodex.ee.web;

import com.deltacodex.ee.ejb.remote.UserDetails;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import java.io.IOException;

/**
 * <h3>Like this object creation is not processing simultaneously</h3>
 * <p>
 * public class Home extends HttpServlet {<br>
 *
 * EJB private UserDetails userDetails;<br>
 * Override <br>
 * protected void doGet(HttpServletRequest request, HttpServletResponse response) <br>
 * throws ServletException, IOException { <br>
 * response.getWriter().print(userDetails.getName()); <br>
 * } } <br>
 * <p>
 */

@WebServlet("/home")
public class Home extends HttpServlet {
    /**
     * <h3>We Need to use this below system to create object simultaneously</h3>
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDetails details = null;
        if (request.getSession().getAttribute("user_bean") == null) {
            try {
                InitialContext ctx = new InitialContext();
                details = (UserDetails) ctx.lookup("com.deltacodex.ee.ejb.remote.UserDetails");
                request.getSession().setAttribute("user_bean", details);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            details = (UserDetails) request.getSession().getAttribute("user_bean");
        }
        response.getWriter().println(details.getName());
        details.remove();
        request.getSession().removeAttribute("user_bean");
    }
}

