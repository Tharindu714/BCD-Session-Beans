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

@WebServlet("/home")
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDetails details;
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

