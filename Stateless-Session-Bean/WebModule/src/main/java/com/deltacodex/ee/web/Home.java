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
    @EJB(lookup = "java:global/EJBModule/UserDetailsBean")
    private UserDetails userDetails;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//           InitialContext ctx = new InitialContext();
//            UserDetails details = (UserDetails) ctx.lookup("UserInfo"); //--> Change Name of Lookup
//            UserDetails details = (UserDetails) ctx.lookup("com.deltacodex.ee.ejb.remote.UserDetails");
//            response.getWriter().println(details.getName());
           response.getWriter().print(userDetails.getName());  //--> With @EJB private UserDetails

//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }  //--> Before Put @EJB private UserDetails
    }
}
