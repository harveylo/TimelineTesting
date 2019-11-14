package com.ceej.controller;

import com.ceej.model.Article;

import java.time.*;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseUtility {

    // connection
    private final static String db_url = "jdbc:mysql://localhost:3306/timeline?useSSL=false&serverTimezone=UTC" +
            "&useUnicode=true&amp&characterEncoding=UTF-8";
    final static String res_url = "localhost:8080/timeline/res/"; //img url
    private final static String user = "temp"; //user name
    private final static String pwd = "123456"; //user pwd

    protected Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(db_url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static void closeConnection(Connection con, PreparedStatement presto, ResultSet rs) {
        try {
            if (con != null) con.close();
            if (rs != null) rs.close();
            if (presto != null) presto.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // users
    public boolean isUserExisted(String userID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        String sql = "select userID from user where userID=?";
        try {
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            rs = pstm.executeQuery();

            if (rs != null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
        return false;
    }

    public boolean addUser(String userID, String nickname, String pwd) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        boolean flg = isUserExisted(userID);
        if (flg == true) return false;

        try {
            String sql = "insert into user(userID,nickname,password) values (?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            pstm.setString(2, nickname);
            pstm.setString(3, pwd);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
        return true;
    }

    public boolean isPwdCorrect(String userID, String pwd) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        try {
            String sql = "select password,nickname from user where userID=?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            rs = pstm.executeQuery();
            while (rs != null && rs.next()) {
                if (pwd.equals(rs.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
        return false;
    }

    public String getNickname(String userID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        try {
            String sql = "select nickname from user where userID=?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            rs = pstm.executeQuery();
            while (rs != null && rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
        return null;
    }


    // articles
    public boolean isArticleExisted(String articleID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        String sql = "select articleID from article where articleID=?";
        boolean flg = false;
        try {
            pstm = con.prepareStatement(sql);
            pstm.setString(1, articleID);
            rs = pstm.executeQuery();

            if (rs == null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
        return false;
    }

    public boolean addArticle(String userID, String content, String imageURL) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        boolean existed = isUserExisted(userID);
        if (!existed) return false;
        try {
            String sql = "insert into article(userID,content,imageURL) values (?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            pstm.setString(2, content);
            pstm.setString(3, imageURL);
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
    }

    public ArrayList<Article> getCurrentArticles(int front, int num) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            String sql = "select * from article where articleID > ? order by articleID DESC";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, front);
            rs = pstm.executeQuery();
            int count = 0;
            while (rs != null && rs.next() && (num-- != 0)) {
                Article article = new Article();
                count++;
                article.setArticleID(rs.getString("articleID"));
                article.setUserID(rs.getString("userID"));
                article.setContent(rs.getString("content"));
                article.setImageURL(rs.getString("imageURL"));
                article.setNickname(getNickname(rs.getString("userID")));
                article.setTimeStamp(rs.getTimestamp("timeStamp").toString());
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
    }

    public ArrayList<Article> getPreviousArticles(int tail, int num) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            String sql = "select * from article where articleID < ? order by articleID DESC";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, tail);
            rs = pstm.executeQuery();
            int count = 0;
            while (rs.next() && (num-- != 0)) {
                Article article = new Article();
                count++;
                article.setArticleID(rs.getString("articleID"));
                article.setUserID(rs.getString("userID"));
                article.setContent(rs.getString("content"));
                article.setImageURL(rs.getString("imageURL"));
                article.setNickname(getNickname(rs.getString("userID")));
                article.setTimeStamp(rs.getTimestamp("timeStamp").toString());
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
    }

    public boolean deleteArticle(String articleID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        boolean existed = isArticleExisted(articleID);
        if (!existed) return false;
        try {
            String sql = "delete from article where articleID = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, articleID);
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
    }

    public String getImageUrlOfArticle(String articleID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        boolean existed = isArticleExisted(articleID);
        if (!existed) return null;

        try {
            String sql = "select * from article where articleID = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, articleID);
            rs = pstm.executeQuery();
            if (rs != null) {
                return rs.getString("imageURL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtility.closeConnection(con, pstm, rs);
        }
        return null;
    }
}
