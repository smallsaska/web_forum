package services;

import DAO.Db;
import com.common.Attribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orogozina on 12/5/14.
 */
public class ThemeCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String theme = request.getParameter(Attribute.THEME_NAME);
        String loginName = (String) request.getSession().getAttribute(Attribute.LOGINNAME);
        String[] authorTheme = Db.createNewTheme(theme, loginName);
        Comment comment = new Comment(authorTheme[1], "Theme " + authorTheme[0] + " has no comments yet");
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);
        request.setAttribute(Attribute.THEME_NAME, authorTheme[0]);
        request.setAttribute(Attribute.NOTHEME, false);
        request.setAttribute(Attribute.COMMENTLIST,comments);
        request.setAttribute(Attribute.IFTHEMENAMEISUNIQUE, Db.isThemeNameUnique);
        request.getRequestDispatcher("/forum.jsp").forward(request, response);


    }
}
