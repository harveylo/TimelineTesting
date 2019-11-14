package com.ceej.controller;

import com.ceej.model.Article;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@WebAppConfiguration
class ArticleControllerTest {
    DataBaseUtility mock_dbUtil;
    FileOperator mock_fileOp;
    ArticleController articleController;

    private MockMvc mockMvc;


    @BeforeEach
    public void mocksetup(){
        mock_dbUtil = mock(DataBaseUtility.class);
        mock_fileOp = mock(FileOperator.class);


        mockMvc = MockMvcBuilders.standaloneSetup(new ArticleController(mock_dbUtil, mock_fileOp)).build();
//        articleController = new ArticleController(mock_dbUtil, mock_fileOp);
    }

    // testing for publishAnArticle
    @Test
    @DisplayName("publishAnArticle: userID不能为空")
    public void publishAnArticle_userId_should_not_be_null() throws Exception{
        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
        String jsonBase = "{\n" +
                "\t\"content\":\"随便打的\"\n" +
                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/article/tryPost")
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .content(jsonBase);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        // TODO: 将Response中的Body提取成Json并且在下方嵌入判断
        assertEquals("404", articleController.publishAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("publishAnArticle: content不能为空")
    public void publishAnArticle_content_should_not_be_null(){
        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
        when(mock_dbUtil.addArticle("1",null,".")).thenReturn(false);

        String jsonBase = "{\n" +
                "\t\"userID\":\"1\"\n" +
                "}";
        assertEquals("404", articleController.publishAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("publishAnArticle: img可以为空")
    public void publishAnArticle_img_can_be_null(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(true);
        when(mock_dbUtil.addArticle("1","随便写写","")).thenReturn(true);

        String jsonBase = "{\n" +
                "\t\"userID\":\"1\",\n" +
                "\t\"content\": \"随便写写\"\n" +
                "}";
        assertEquals("205", articleController.publishAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("publishAnArticle: imgData不能被解析生成imgUrl")
    public void publishAnArticle_imgData_cannot_be_decoded(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(true);
        when(mock_dbUtil.addArticle("1","content","")).thenReturn(true);
        when(mock_fileOp.getImage(anyString(), anyString())).thenReturn("false");
        String jsonBase = "{\n" +
                "\t\"userID\":\"1\",\n" +
                "\t\"content\": \"content\",\n" +
                "\t\"imgData\":\"xxx\"\n" +
                "}";
        assertEquals("404", articleController.publishAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("publishAnArticle: imgData能被解析生成imgUrl")
    public void publishAnArticle_imgData_can_be_decoded(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(true);
        when(mock_dbUtil.addArticle(anyString(),anyString(),anyString())).thenReturn(true);
        when(mock_fileOp.getImage(anyString(), anyString())).thenReturn("");
        String jsonBase = "{\n" +
                "\t\"userID\":\"1\",\n" +
                "\t\"content\": \"content\",\n" +
                "\t\"imgData\":\"xxx\"\n" +
                "}";
        assertEquals("205", articleController.publishAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("publishAnArticle: 发布的userid存在")
    public void publishAnArticle_with_existed_user(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(true);
        when(mock_dbUtil.addArticle("1","content","")).thenReturn(true);
        String jsonBase = "{\n" +
                "\t\"userID\":\"1\",\n" +
                "\t\"content\": \"content\"\n" +
                "}";
        assertEquals("205", articleController.publishAnArticle(jsonBase).getCode());
    }

    @Test
    @Disabled
    @DisplayName("publishAnArticle: 发布的userid不存在")
    public void publishAnArticle_with_none_existed_user(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(false);
        when(mock_dbUtil.addArticle("1","content","")).thenReturn(false);
        String jsonBase = "{\n" +
                "\t\"userID\":\"1\",\n" +
                "\t\"content\": \"content\"\n" +
                "}";
        assertEquals("404", articleController.publishAnArticle(jsonBase).getCode());
    }

    //testing for deleteAnArticle
    @Test
    @Disabled
    @DisplayName("deleteAnArticle: ID不能为空")
    public void deleteAnArticle_Id_should_not_be_null(){
        when(mock_dbUtil.isArticleExisted(null)).thenReturn(false);
            String jsonBase = "{}";
        assertEquals("404", articleController.deleteAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("deleteAnArticle: 待删除的article存在")
    public void deleteAnArticle_with_existed_article(){
        when(mock_dbUtil.isArticleExisted("1")).thenReturn(true);
        when(mock_dbUtil.deleteArticle("1")).thenReturn(true);
        String jsonBase = "{\n" +
                "\t\"articleID\":\"1\"\n" +
                "}";
        assertEquals("205", articleController.deleteAnArticle(jsonBase).getCode());
    }
    @Test
    @Disabled
    @DisplayName("deleteAnArticle: 待删除的article不存在")
    public void deleteAnArticle_with_none_existed_article(){
        when(mock_dbUtil.isArticleExisted("1")).thenReturn(false);
        when(mock_dbUtil.deleteArticle("1")).thenReturn(true);
        String jsonBase = "{\n" +
                "\t\"articleID\":\"1\"\n" +
                "}";
        assertEquals("404", articleController.deleteAnArticle(jsonBase).getCode());
    }

    //testing retrieveNewestArticles
    @Test
    @Disabled
    public void retrieveNewestArticles_query_articles_failed_return_num_0() {
        when(mock_dbUtil.getCurrentArticles(anyInt(), anyInt())).thenReturn(null);
        String jsonBase = "{\n" +
                "\t\"front_article_id\":\"0\",\n" +
                "\t\"max_num\":\"10\"\n" +
                "}";
        assertEquals(0, ((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).size());
    }
    @Test
    @Disabled
    public void retrieveNewestArticles_return_correct_number_of_article_which_id_greater_than_front(){
        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
        a1.setArticleID("1");
        a2.setArticleID("2");
        a3.setArticleID("3");
        a4.setArticleID("4");
        a5.setArticleID("5");
        ArrayList<Article> al = new ArrayList<Article>();
        al.add(a5);al.add(a4);
        when(mock_dbUtil.getCurrentArticles(3, 10)).thenReturn(al);
        String jsonBase = "{\n" +
                "\t\"front_article_id\":\"3\",\n" +
                "\t\"max_num\":\"10\"\n" +
                "}";

        assertAll(
                ()->{assertEquals(2,((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).size());},
                ()->{assertEquals("5",((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).get(0).getArticleID());},
                ()->{assertEquals("4",((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).get(1).getArticleID());}
                );
    }
    @Test
    @Disabled
    public void retrieveNewestArticles_return_correct_number_of_article_which_maximum_equals_to_num(){
        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
        a1.setArticleID("1");
        a2.setArticleID("2");
        a3.setArticleID("3");
        a4.setArticleID("4");
        a5.setArticleID("5");
        ArrayList<Article> al = new ArrayList<Article>();
        al.add(a5);al.add(a4);
        when(mock_dbUtil.getCurrentArticles(1, 2)).thenReturn(al);
        String jsonBase = "{\n" +
                "\t\"front_article_id\":\"1\",\n" +
                "\t\"max_num\":\"2\"\n" +
                "}";
        assertAll(
                ()->{assertEquals(2,((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).size());},
                ()->{assertEquals("5",((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).get(0).getArticleID());},
                ()->{assertEquals("4",((ArrayList<Article>)(articleController.retrieveNewestArticles(jsonBase).getData())).get(1).getArticleID());}
        );
    }

    //testing retrievePreviousArticles
    @Test
    public void retrievePreviousArticles_query_articles_failed_return_num_0() {
        when(mock_dbUtil.getPreviousArticles(anyInt(), anyInt())).thenReturn(null);
        String jsonBase = "{\n" +
                "\t\"tail_article_id\":\"0\",\n" +
                "\t\"max_num\":\"100\"\n" +
                "}";
        assertEquals(0, ((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).size());
    }
    @Test
    public void retrievePreviousArticles_return_correct_number_of_article_which_id_greater_than_front(){
        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
        a1.setArticleID("1");
        a2.setArticleID("2");
        a3.setArticleID("3");
        a4.setArticleID("4");
        a5.setArticleID("5");
        ArrayList<Article> al = new ArrayList<Article>();
        al.add(a2);al.add(a1);
        when(mock_dbUtil.getPreviousArticles(3, 10)).thenReturn(al);
        String jsonBase = "{\n" +
                "\t\"tail_article_id\":\"3\",\n" +
                "\t\"max_num\":\"10\"\n" +
                "}";
        assertAll(
                ()->{assertEquals(2,((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).size());},
                ()->{assertEquals("2",((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).get(0).getArticleID());},
                ()->{assertEquals("1",((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).get(1).getArticleID());}
        );
    }
    @Test
    public void retrievePreviousArticles_return_correct_number_of_article_which_maximum_equals_to_num(){
        Article a1=new Article(),a2=new Article(),a3=new Article(),a4=new Article(),a5=new Article();
        a1.setArticleID("1");
        a2.setArticleID("2");
        a3.setArticleID("3");
        a4.setArticleID("4");
        a5.setArticleID("5");
        ArrayList<Article> al = new ArrayList<Article>();
        al.add(a4);al.add(a3);
        when(mock_dbUtil.getPreviousArticles(5, 2)).thenReturn(al);
        String jsonBase = "{\n" +
                "\t\"tail_article_id\":\"5\",\n" +
                "\t\"max_num\":\"2\"\n" +
                "}";
        assertAll(
                ()->{assertEquals(2,((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).size());},
                ()->{assertEquals("4",((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).get(0).getArticleID());},
                ()->{assertEquals("3",((ArrayList<Article>)(articleController.retrievePreviousArticles(jsonBase).getData())).get(1).getArticleID());}
        );
    }
}