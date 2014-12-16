/*
package services;



import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

*/
/**
 * Created by orogozina on 12/8/14.
 *//*

public class Parser {
    private static String filePath = "/Users/Orogozina/IdeaProjects/web_forum/src/main/resources/";

    private static String filePathName(Integer themeId) {

        return filePath + themeId.toString() + ".csv";
    }

    public static ArrayList<Comment> getComments(Integer themeId) throws IOException {

        ArrayList<Comment> commentList = new ArrayList<Comment>();
        List<String[]> subRecordLine;
        CSVReader csvReader = null;
        if (theme[0] == -1) {
            commentList.add(new Comment("", "There in no such theme"));
        } else {
            if (theme[1] == -1) {
                commentList.add(new Comment("", "There is no comments for this theme"));
            } else {
                try {
                    csvReader = new CSVReader(new FileReader(filePathName(themeId)));
                    subRecordLine = new ArrayList<String[]>();
                    subRecordLine = csvReader.readAll();
                    for (int i = 0; i < subRecordLine.size(); i++) {
                        commentList.add(new Comment(subRecordLine.get(i)[0], subRecordLine.get(i)[1]));
                    }
                }
                catch (IOException e){
                    System.out.println("No file"+filePath);
                }
            }
        }
        return  commentList;

    }

    public  static void addCommentToCSV(Integer themeId, Comment comment) throws IOException {
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        commentList = getComments(themeId);
        String file = filePathName(themeId);
        String[] comments = new String[1];

        comments[0] = comment.getUserLoginName() + "," + comment.getText();
        FileWriter fileWriter;
             fileWriter= new FileWriter(file,true);
        while ((line = fileWriter.a .readLine()) != null) {
            System.out.println(line);
            result.append(line).append("\n");
        }
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(comments);
    }

}public  static void addFileForNewTheme(Integer themeId) throws IOException {
    String file = filePathName(themeId);
    FileWriter fileWriter;
    fileWriter= new FileWriter(file);
    CSVWriter csvWriter = new CSVWriter(fileWriter);

}*/
