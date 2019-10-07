package controller;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleControllerTest {
    DataBaseUtility mock_dbUtil;
    ArticleController articleController;
    @BeforeEach
    public void mocksetup(){
        mock_dbUtil = mock(DataBaseUtility.class);
        articleController = new ArticleController(mock_dbUtil);
    }

    // testing for publishAnArticle
    @Test
    @DisplayName("publishAnArticle: userID都不能为空")
    public void publishAnArticle_userId_should_not_be_null(){
        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
        assertEquals(false, articleController.publishAnArticle(null,"content","."));
    }
    @Test
    @DisplayName("publishAnArticle: content都不能为空")
    public void publishAnArticle_content_should_not_be_null(){
        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
        when(mock_dbUtil.addArticle("1",null,".")).thenReturn(false);
        assertEquals(false, articleController.publishAnArticle("1",null,"."));
    }
    @Test
    @DisplayName("publishAnArticle: imgUrl都不能为空")
    public void publishAnArticle_imgUrl_should_not_be_null(){
        when(mock_dbUtil.isUserExisted(null)).thenReturn(false);
        when(mock_dbUtil.addArticle("1","content",null)).thenReturn(false);
        assertEquals(false, articleController.publishAnArticle("1","content",null));
    }
    @Test
    @DisplayName("publishAnArticle: 发布的userid存在")
    public void publishAnArticle_with_existed_user(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(true);
        when(mock_dbUtil.addArticle("1","content",".")).thenReturn(true);
        assertEquals(true, articleController.publishAnArticle("1","content","."));
    }
    @Test
    @DisplayName("publishAnArticle: 发布的userid不存在")
    public void publishAnArticle_with_none_existed_user(){
        when(mock_dbUtil.isUserExisted("1")).thenReturn(false);
        when(mock_dbUtil.addArticle("1","content",".")).thenReturn(true);
        assertEquals(false, articleController.publishAnArticle("1","content","."));
    }

    //testing for deleteAnArticle
    @Test
    @DisplayName("deleteAnArticle: ID不能为空")
    public void deleteAnArticle_Id_should_not_be_null(){
        when(mock_dbUtil.isArticleExisted(null)).thenReturn(false);
        assertEquals(false, articleController.deleteAnArticle(null));
    }
    @Test
    @DisplayName("deleteAnArticle: 待删除的article存在")
    public void deleteAnArticle_with_existed_article(){
        when(mock_dbUtil.isArticleExisted("1")).thenReturn(true);
        when(mock_dbUtil.deleteArticle("1")).thenReturn(true);
        assertEquals(true, articleController.deleteAnArticle("1"));
    }
    @Test
    @DisplayName("deleteAnArticle: 待删除的article不存在")
    public void deleteAnArticle_with_none_existed_article(){
        when(mock_dbUtil.isArticleExisted("1")).thenReturn(false);
        when(mock_dbUtil.deleteArticle("1")).thenReturn(true);
        assertEquals(false, articleController.deleteAnArticle("1"));
    }

    //testing retrieveNewestArticles
}