package services;



import DAO.Db;
import com.common.Attribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.common.Attribute.*;

/**
 * Created by orogozina on 12/10/14.
 */
public class CommentCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String themeName = request.getParameter(Attribute.THEME_NAME).toString();
        String authorName = request.getParameter(Attribute.LOGINNAME).toString();
        String commentText = request.getParameter(Attribute.COMMENTTEXT).toString();
        Comment comment = new Comment(authorName, commentText);
        Db.addNewCommentToDb(themeName, comment);
        request.setAttribute(Attribute.COMMENTLIST, Db.getCommentList(themeName));
        request.setAttribute(Attribute.THEME_NAME,themeName);
        request.setAttribute(Attribute.NOTHEME, false);
        request.getRequestDispatcher("/forum.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
