package services;

import DAO.Db;
import com.common.Attribute;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Created by orogozina on 11/24/14.
 */
public class ThemeFindServlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String theme = request.getParameter(Attribute.THEME_NAME);
        request.setAttribute(Attribute.THEME_NAME, theme);
        request.setAttribute(Attribute.NOTHEME, false);
        request.setAttribute(Attribute.COMMENTLIST, Db.getCommentList(theme));
        request.getRequestDispatcher("/forum.jsp").forward(request, response);
    }
}
