package controller;

import model.Article;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// 仅仅定义与数据库功能的接口
// 不进行与数据库的对接
// 为了实现Timeline特性以及测试，接口已经足够了

public class DataBaseUtility {
    public final static String db_url = ""; //db url
    public final static String res_url= ""; //img url
    public final static String user   = ""; //user name
    public final static String pwd    = ""; //user pwd

    public Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(db_url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeDBResource(Connection con,PreparedStatement presto,ResultSet rs) {
        if(rs==null||con==null||presto==null) return;
        try {
            rs.close();
            presto.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserExisted(String userID) {
        return true;
    }

    public boolean addUser(String userID,String nickname,String pwd) {
        return true;
    }

    public boolean isPwdCorrect(String userID,String pwd) {
        return true;
    }

    public String getNickname(String userID) {
        return "";
    }

    public boolean isArticleExisted(String articleID){
        return true;
    }

    public boolean addArticle(String userID,String content,String imageURL) {
        return true;
    }

    public ArrayList<Article> getCurrentArticles(int front, int num){
        ArrayList<Article> articles=new ArrayList<Article>();
        return articles;
    }

    public ArrayList<Article> getPreviousArticles(int tail,int num){
        ArrayList<Article> articles=new ArrayList<Article>();
        return articles;
    }

    public boolean deleteArticle(String articleID){
        return true;
    }

    public String getImageUrlOfArticle(String articleID) {
        return "";
    }
}
