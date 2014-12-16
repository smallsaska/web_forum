package services;

import DAO.Db;
import com.common.Attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orogozina on 11/10/14.
 */
public class RegServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String loginNameStr = request.getParameter("loginName_r");
        String passwordStr = request.getParameter("password_r");
        String firstName = request.getParameter("firstName_r");
        String lastName = request.getParameter("lastName_r");

        if (Db.AddUserToDB(loginNameStr, passwordStr, firstName, lastName)){
            request.setAttribute(Attribute.IFUSERNAMEISUNIQUE, "This login name is already in use");
        };
        String loginName = (String) request.getSession().getAttribute(Attribute.LOGINNAME);
        Comment comment = new Comment(loginName, "No theme  - no comments");
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);
        request.setAttribute(Attribute.COMMENTLIST, comments);
        request.setAttribute(Attribute.THEME_NAME, "No Theme");
        request.setAttribute(Attribute.NOTHEME, true);
        request.getRequestDispatcher("/forum.jsp").forward(request, response);

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
