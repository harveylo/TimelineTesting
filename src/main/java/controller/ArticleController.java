package controller;

import model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleController {

    public static void main(String[] args) {
        ArticleController a = new ArticleController();
        List<Article> as = a.retrieveNewestArticles(1,10);
        System.out.println(1);
    }

    DataBaseUtility dbutil;

    public ArticleController() {
        dbutil = new DataBaseUtility();
    }

    public ArticleController( DataBaseUtility dbutil) {
        this.dbutil = dbutil;
    }

    public boolean publishAnArticle(String userID, String content, String imgUrl){
        Article article = new Article();
        article.setUserID(userID);
        article.setContent(content);
        article.setImageURL(imgUrl);
        if(!dbutil.isUserExisted(userID)){
            return false;
        }
        if(!dbutil.addArticle(article.getUserID(), article.getContent(), article.getImageURL())){
            return false;
        }
        return true;
    }

    public boolean deleteAnArticle(String articleID){
        Article article = new Article();
        article.setArticleID(articleID);
        if(!dbutil.isArticleExisted(article.getArticleID())){
            return false;
        }
        return dbutil.deleteArticle(article.getArticleID());
    }

    public ArrayList<Article> retrieveNewestArticles(int front_article_id, int max_num){
        ArrayList<Article> articles=dbutil.getCurrentArticles(front_article_id,max_num);
        if(articles == null)
            articles = new ArrayList<Article>();
        return articles;
    }

    public ArrayList<Article> retrievePreviousArticles(int tail_article_id, int max_num){
        ArrayList<Article> articles=dbutil.getPreviousArticles(tail_article_id,max_num);
        if(articles == null)
            articles = new ArrayList<Article>();
        return articles;
    }

}
