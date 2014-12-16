package services;

import DAO.Db;
import com.common.Attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by orogozina on 11/10/14.
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String loginNameStr = request.getParameter("loginName_l");
        String passwordStr = request.getParameter("password_l");
        Comment comment = new Comment(loginNameStr, "No theme  - no comments");
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(0,comment);
        request.setAttribute(Attribute.COMMENTLIST,comments);
        request.setAttribute(Attribute.THEME_NAME, "No theme");
        request.setAttribute(Attribute.NOTHEME, true);
        if (Db.checkNamePassword(loginNameStr, passwordStr)) {
            request.getSession().setAttribute("loginName", loginNameStr);
            request.getRequestDispatcher("/forum.jsp").forward(request, response);
        } else {
            request.setAttribute(Attribute.ERRORREASON, "Incorrect login or passsword");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


    }

}
