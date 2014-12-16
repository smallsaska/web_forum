package DAO;

import com.common.Attribute;
import com.dbFields.Comments;
import com.dbFields.Theme;
import com.dbFields.Users;
import org.apache.commons.codec.digest.DigestUtils;
import services.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by orogozina on 11/11/14.
 */
public class Db {
    public static String isThemeNameUnique = null;
    static String URL = "jdbc:mysql://localhost/servlet_test?user=root&password=root";

    private static void dbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean AddUserToDB(String loginName, String loginPassword, String firstName, String lastName) {
        dbConnection();
        String sqlQuery;
        String salt = "";
        String md5pqssword = "";
        ResultSet rs = null;
        Integer lastUserIdInTable;
        boolean isUniqueUser = true;
        try (Connection conn = DriverManager.getConnection(URL)) {
            sqlQuery = "Select " + Users.LOGIN_NAME_FIELD + " from " + Users.TABLE_NAME + " where" + Users.LOGIN_NAME_FIELD + " = \"" + loginName + "\"";
            if (rs.next()){
                isUniqueUser = false;
            }else {
                sqlQuery = "Select " + Users.ID_FIELD + " from " + Users.TABLE_NAME + " order by" + Users.ID_FIELD + " desc limit 1;";
                Statement stmt = conn.createStatement();
                rs = stmt.executeQuery(sqlQuery);
                rs.next();
                lastUserIdInTable = rs.getInt(Users.ID_FIELD);
                salt = loginName + Integer.toString(lastUserIdInTable + 1);
                rs.close();
                md5pqssword = DigestUtils.md5Hex(loginPassword + salt);
                stmt.close();
                PreparedStatement stmt1;
                sqlQuery = "INSERT INTO servlet_test." + Users.TABLE_NAME + "(" +
                        Users.LOGIN_NAME_FIELD + ", " +
                        Users.PASSWORD_FIELD + ", " +
                        Users.FIRST_NAME_FIELD + ", " +
                        Users.LAST_NAME_FIELD +
                        " ) VALUES (? , ?, ?, ?, ? ) ";
                stmt1 = conn.prepareStatement(sqlQuery);
                stmt1.setInt(1, lastUserIdInTable + 1);
                stmt1.setString(2, loginName);
                stmt1.setString(3, md5pqssword);
                stmt1.setString(4, firstName);
                stmt1.setString(5, lastName);
                stmt1.executeUpdate();
                stmt1.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUniqueUser;
    }

    public static boolean checkNamePassword(String loginName, String loginPassword) {
        String sqlQuery;
        boolean result = false;
        dbConnection();
        String salt = "";
        String newMd5Password = "";
        String md5Password = "";
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(URL)) {
            sqlQuery = "SELECT  * from " + Users.TABLE_NAME + " where " + Users.LOGIN_NAME_FIELD + " = \"" + loginName + "\"";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rs.next();
            salt = loginName + Integer.toString(rs.getInt(Users.ID_FIELD));
            md5Password = rs.getString(Users.PASSWORD_FIELD);
            newMd5Password = DigestUtils.md5Hex(loginPassword + salt);
            rs.close();
            stmt.close();
            if (md5Password.equals(newMd5Password)) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    //return two strings: author first+last names and theme
    public static String[] createNewTheme(String theme, String loginName) {
        String sqlQuery;
        String[] authorTheme = new String[2];
        Integer userId;
        Integer themeCount;
        dbConnection();
        authorTheme[0] = theme;
        ResultSet rs = null;
        try (Connection conn = DriverManager.getConnection(URL)) {
            sqlQuery = "SELECT  id from " + Theme.TABLE_NAME + " where " + Theme.TITLE_FIELD + "=" + "\"" + theme + "\"";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            if (rs.first()){
                isThemeNameUnique = "There is such theme, enter another title ";
            };
            sqlQuery = "SELECT  id, first_name, last_name from " + Users.TABLE_NAME + " where " + Users.LOGIN_NAME_FIELD + "=" + "\"" + loginName + "\"";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rs.next();
            authorTheme[1] = rs.getString(Users.FIRST_NAME_FIELD) + " " +rs.getString(Users.LAST_NAME_FIELD);
            userId = rs.getInt(Users.ID_FIELD);
            sqlQuery = "Select id from " + Theme.TABLE_NAME + " theme order by " + Theme.ID_FIELD + " desc limit 1";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) {
                themeCount = rs.getInt(Theme.ID_FIELD);
            } else {
                themeCount=0;
            }
            stmt.close();
            PreparedStatement stmt1;
            if (isThemeNameUnique==null || themeCount==0) {
                sqlQuery = "INSERT INTO " + Theme.TABLE_NAME + " (" +
                        Theme.ID_FIELD + "," +
                        Theme.AUTHOR_ID_FIELD + "," +
                        Theme.TITLE_FIELD + "," +
                        Theme.DESCRIPTION_FIELD + ") VALUES (?, ?, ?, ?) ";
                stmt1 = conn.prepareStatement(sqlQuery);
                stmt1.setInt(1, themeCount + 1);
                stmt1.setInt(2, userId);
                stmt1.setString(3, theme);
                stmt1.setString(4, "null");
                stmt1.executeUpdate();
                stmt1.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return authorTheme;
    }

    // returns int = themeId
    public static int getTheThemeId(String theme) {
        String sqlQuery;
        dbConnection();
        ResultSet rs = null;
        Integer theme_id = 0;
        Integer result = -1;
        try (Connection conn = DriverManager.getConnection(URL)) {
            sqlQuery = "SELECT " +Theme.ID_FIELD+  " from " + Theme.TABLE_NAME + " where title =  " + "\"" + theme + "\"";
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) {
                theme_id = rs.getInt(Theme.ID_FIELD);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return theme_id;
    }

    public static void addNewCommentToDb(String themeName, Comment comment) {
        Integer commentsNumber;
        String sqlQuery;
        Integer userId;
        Integer themeId;
        Integer themeCount;
        dbConnection();
        ResultSet rs = null;
        String loginName = comment.getUserLoginName();
        Statement stmt;
        try (Connection conn = DriverManager.getConnection(URL)) {
            themeId = getTheThemeId(themeName);
            sqlQuery = "SELECT " + Users.ID_FIELD + " from " + Users.TABLE_NAME + " where " + Users.LOGIN_NAME_FIELD + " = " + "\"" + comment.getUserLoginName() + "\"";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            rs.next();
            userId = rs.getInt(Users.ID_FIELD);
            sqlQuery = "SELECT "+ Comments.ID_FIELD +" from " + Comments.TABLE_NAME + " order by " +Comments.ID_FIELD +" desc limit 1";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) {
                commentsNumber = rs.getInt(Comments.ID_FIELD);
            } else {
                commentsNumber = 0;
            }
            stmt.close();
            PreparedStatement stmt1;
            sqlQuery = "INSERT INTO " + Comments.TABLE_NAME + " ( " +
                    Comments.ID_FIELD + ", " +
                    Comments.THEME_ID_FIELD + ", " +
                    Comments.AUTHOR_ID_FIELD + ", " +
                    Comments.COMMENT_TEXT_FIELD + " ) VALUES ( ?, ?, ?, ? ) ";
            stmt1 = conn.prepareStatement(sqlQuery);
            stmt1.setInt(1, commentsNumber + 1);
            stmt1.setInt(2, themeId);
            stmt1.setInt(3, userId);
            stmt1.setString(4, comment.getText());
            stmt1.executeUpdate();
            stmt1.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static List<Comment> getCommentList(String themeName) {
        Integer commentsNumber;
        String sqlQuery;
        String sqlQuery1;
        String sqlQuery2;
        Statement stmt;
        Statement stmt1;
        Statement stmt2;
        ResultSet rs = null;
        ResultSet rs1;
        ResultSet rs2;
        List<Comment>  commentsList = new ArrayList<Comment>();
        Integer userId;
        Integer themeId;
        Integer themeCount;
        Comment comment;
        String authorName;
        String commentText;
        dbConnection();

        try (Connection conn = DriverManager.getConnection(URL)) {
            themeId = getTheThemeId(themeName);
            sqlQuery = "SELECT " + Comments.AUTHOR_ID_FIELD + ", " + Comments.COMMENT_TEXT_FIELD + " from " + Comments.TABLE_NAME + " where " +Comments.THEME_ID_FIELD + " = " + themeId;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            while (rs.next()){
                userId = rs.getInt(Comments.AUTHOR_ID_FIELD);
                commentText = rs.getString(Comments.COMMENT_TEXT_FIELD);
                sqlQuery1 = "SELECT " + Users.FIRST_NAME_FIELD + ", " + Users.LAST_NAME_FIELD +  " from " + Users.TABLE_NAME + " where " + Users.ID_FIELD + " = " + userId;
                stmt1 = conn.createStatement();
                rs1 = stmt1.executeQuery(sqlQuery1);
                rs1.next();
                authorName = rs1.getString(Users.FIRST_NAME_FIELD) + " " + rs1.getString(Users.LAST_NAME_FIELD);
                comment = new Comment(authorName, commentText);
                commentsList.add(comment);
            };
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        if (commentsList.size()==0){
            comment = new Comment("No comments", "be the first");
            commentsList.add(comment);
        };
        return commentsList;
    }
}
