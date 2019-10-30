package com.ceej.controller;

import com.ceej.model.Article;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleControllerTest {
//    DataBaseUtility mock_dbUtil;
//    ArticleController articleController;
//    @BeforeEach
//    public void mocksetup(){
//        mock_dbUtil = mock(DataBaseUtility.class);
//        articleController = new ArticleController(mock_dbUtil);
//    }
//
//    // testing for publishAnArticle
//    @Test
//
//    @DisplayName("publishAnArticle: userID都不能为空")
//    public void publishAnArticle_userId_should_not_be_null(){
//        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
//        assertEquals(false, articleController.publishAnArticle(null,"content","."));
//    }
//    @Test
//
//    @DisplayName("publishAnArticle: content都不能为空")
//    public void publishAnArticle_content_should_not_be_null(){
//        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
//        when(mock_dbUtil.addArticle("1",null,".")).thenReturn(false);
//        assertEquals(false, articleController.publishAnArticle("1",null,"."));
//    }
//    @Test
//
//    @DisplayName("publishAnArticle: imgUrl都不能为空")
//    public void publishAnArticle_imgUrl_should_not_be_null(){
//        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
//        when(mock_dbUtil.addArticle("1","content",null)).thenReturn(false);
//        assertEquals(false, articleController.publishAnArticle("1","content",null));
//    }
//    @Test
//
//    @DisplayName("publishAnArticle: 发布的userid存在")
//    public void publishAnArticle_with_existed_user(){
//        when(mock_dbUtil.isUserExisted("1")).thenReturn(true);
//        when(mock_dbUtil.addArticle("1","content",".")).thenReturn(true);
//        assertEquals(true, articleController.publishAnArticle("1","content","."));
//    }
//    @Test
//
//    @DisplayName("publishAnArticle: 发布的userid不存在")
//    public void publishAnArticle_with_none_existed_user(){
//        when(mock_dbUtil.isUserExisted("1")).thenReturn(false);
//        when(mock_dbUtil.addArticle("1","content",".")).thenReturn(true);
//        assertEquals(false, articleController.publishAnArticle("1","content","."));
//    }
//
//    //testing for deleteAnArticle
//    @Test
//
//    @DisplayName("deleteAnArticle: ID不能为空")
//    public void deleteAnArticle_Id_should_not_be_null(){
//        when(mock_dbUtil.isArticleExisted(null)).thenReturn(false);
//        assertEquals(false, articleController.deleteAnArticle(null));
//    }
//    @Test
//
//    @DisplayName("deleteAnArticle: 待删除的article存在")
//    public void deleteAnArticle_with_existed_article(){
//        when(mock_dbUtil.isArticleExisted("1")).thenReturn(true);
//        when(mock_dbUtil.deleteArticle("1")).thenReturn(true);
//        assertEquals(true, articleController.deleteAnArticle("1"));
//    }
//    @Test
//
//    @DisplayName("deleteAnArticle: 待删除的article不存在")
//    public void deleteAnArticle_with_none_existed_article(){
//        when(mock_dbUtil.isArticleExisted("1")).thenReturn(false);
//        when(mock_dbUtil.deleteArticle("1")).thenReturn(true);
//        assertEquals(false, articleController.deleteAnArticle("1"));
//    }
//
//    //testing retrieveNewestArticles
//    @Test
//
//    public void retrieveNewestArticles_query_articles_failed_return_num_0() {
//        when(mock_dbUtil.getCurrentArticles(anyInt(), anyInt())).thenReturn(null);
//        assertEquals(0, articleController.retrieveNewestArticles(0,100).size());
//    }
//    @Test
//
//    public void retrieveNewestArticles_return_correct_number_of_article_which_id_greater_than_front(){
//        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
//        a1.setArticleID("1");
//        a2.setArticleID("2");
//        a3.setArticleID("3");
//        a4.setArticleID("4");
//        a5.setArticleID("5");
//        ArrayList<Article> al = new ArrayList<Article>();
//        al.add(a5);al.add(a4);
//        when(mock_dbUtil.getCurrentArticles(3, 10)).thenReturn(al);
//        assertAll(
//                ()->{assertEquals(2,articleController.retrieveNewestArticles(3,10).size());},
//                ()->{assertEquals("5",articleController.retrieveNewestArticles(3,10).get(0).getArticleID());},
//                ()->{assertEquals("4",articleController.retrieveNewestArticles(3,10).get(1).getArticleID());}
//                );
//    }
//    @Test
//
//    public void retrieveNewestArticles_return_correct_number_of_article_which_maximum_equals_to_num(){
//        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
//        a1.setArticleID("1");
//        a2.setArticleID("2");
//        a3.setArticleID("3");
//        a4.setArticleID("4");
//        a5.setArticleID("5");
//        ArrayList<Article> al = new ArrayList<Article>();
//        al.add(a5);al.add(a4);
//        when(mock_dbUtil.getCurrentArticles(1, 2)).thenReturn(al);
//        assertAll(
//                ()->{assertEquals(2,articleController.retrieveNewestArticles(1,2).size());},
//                ()->{assertEquals("5",articleController.retrieveNewestArticles(1,2).get(0).getArticleID());},
//                ()->{assertEquals("4",articleController.retrieveNewestArticles(1,2).get(1).getArticleID());}
//        );
//    }
//
//    //testing retrievePreviousArticles
//    @Test
//    public void retrievePreviousArticles_query_articles_failed_return_num_0() {
//        when(mock_dbUtil.getCurrentArticles(anyInt(), anyInt())).thenReturn(null);
//        assertEquals(0, articleController.retrievePreviousArticles(0,100).size());
//    }
//    @Test
//    public void retrievePreviousArticles_return_correct_number_of_article_which_id_greater_than_front(){
//        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
//        a1.setArticleID("1");
//        a2.setArticleID("2");
//        a3.setArticleID("3");
//        a4.setArticleID("4");
//        a5.setArticleID("5");
//        ArrayList<Article> al = new ArrayList<Article>();
//        al.add(a2);al.add(a1);
//        when(mock_dbUtil.getPreviousArticles(3, 10)).thenReturn(al);
//        assertAll(
//                ()->{assertEquals(2,articleController.retrievePreviousArticles(3,10).size());},
//                ()->{assertEquals("2",articleController.retrievePreviousArticles(3,10).get(0).getArticleID());},
//                ()->{assertEquals("1",articleController.retrievePreviousArticles(3,10).get(1).getArticleID());}
//        );
//    }
//    @Test
//    public void retrievePreviousArticles_return_correct_number_of_article_which_maximum_equals_to_num(){
//        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
//        a1.setArticleID("1");
//        a2.setArticleID("2");
//        a3.setArticleID("3");
//        a4.setArticleID("4");
//        a5.setArticleID("5");
//        ArrayList<Article> al = new ArrayList<Article>();
//        al.add(a4);al.add(a3);
//        when(mock_dbUtil.getPreviousArticles(5, 2)).thenReturn(al);
//        assertAll(
//                ()->{assertEquals(2,articleController.retrievePreviousArticles(5,2).size());},
//                ()->{assertEquals("4",articleController.retrievePreviousArticles(5,2).get(0).getArticleID());},
//                ()->{assertEquals("3",articleController.retrievePreviousArticles(5,2).get(1).getArticleID());}
//        );
//    }
}