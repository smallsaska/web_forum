package services;

/**
 * Created by orogozina on 12/8/14.
 */
public class Comment {
    String userLoginName;
    String commentText;

    public Comment(String userLoginName, String commentText) {
        this.userLoginName = userLoginName;
        this.commentText =commentText;
    }
    public  String getText (){
        return this.commentText;
    }

    public String getUserLoginName(){
        return this.userLoginName;
    }
}
