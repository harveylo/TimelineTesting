package com.harveylo.controller;

import com.harveylo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DataBaseUtilityTest {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    DataBaseUtility dao;

    class DataBaseUtilFake extends DataBaseUtility {
        @Override
        protected Connection createConnection() {
            return connection;
        }

        @Override
        public boolean addArticle(String userID, String content, String imageURL) {
            PreparedStatement pstm = null;
            ResultSet rs = null;
            Connection con = createConnection();
            boolean existed = isUserExisted(userID);
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
    }

    @BeforeEach
    void setUp() {
        dao = new DataBaseUtilFake();
    }

    @Test
    @DisplayName("User Existence Test")
    void is_user_exist() throws Exception {
        String userId = "1";

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        boolean success = dao.isUserExisted(userId);
        assertFalse(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement).setString(eq(1), stringArgumentCaptor.capture());

        assertEquals("1", stringArgumentCaptor.getAllValues().get(0));

//        verify(statement).executeUpdate();
//        verify(connection).commit();
        verify(statement).close();
        verify(connection).close();
    }

    @Test
    @DisplayName("Add User Test")
    void add_user() throws SQLException {
        User user = new User();
        user.setNickname("test1");
        user.setPassword("123456");
        user.setUserID("42");

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        boolean success = dao.addUser(user.getUserID(), user.getNickname(), user.getPassword());
        assertTrue(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(statement, times(4)).setString(integerArgumentCaptor.capture(), stringArgumentCaptor.capture());

        assertAll(
                ()->assertEquals("42",stringArgumentCaptor.getAllValues().get(1)),
                ()->assertEquals("test1",stringArgumentCaptor.getAllValues().get(2)),
                ()->assertEquals("123456",stringArgumentCaptor.getAllValues().get(3))
        );

        verify(statement).executeUpdate();
//        这个可以不调用吗 不提交的话好像是不会修改数据库的吧
//        verify(connection).commit();
        verify(statement,times(2)).close();
        verify(connection,times(2)).close();
    }

    @Test
    @DisplayName("Correct Password Test")
    void is_pwd_correct() throws SQLException {
        String userId = "1";
        String password = "123456";
        when(connection.prepareStatement(anyString())).thenReturn(statement);
//        when(statement.executeQuery()).thenReturn(new ResultSetImpl());
//        这里要怎么才能让这个返回值为true啊
//        如果不连数据库的话 必定是false的 调用后返回的resultSet为null
        boolean success = dao.isPwdCorrect(userId, password);
        assertFalse(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement).setString(eq(1), stringArgumentCaptor.capture());

        assertEquals("1", stringArgumentCaptor.getAllValues().get(0));

        verify(statement).close();
        verify(connection).close();
    }

    @Test
    @DisplayName("Get Nickname Test")
    void get_nickname() throws SQLException {
        String userId = "1";
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        String result = dao.getNickname(userId);
        assertNull(result);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement).setString(eq(1), stringArgumentCaptor.capture());

        assertEquals("1", stringArgumentCaptor.getAllValues().get(0));

        verify(statement).close();
        verify(connection).close();
    }

    @Test
    @DisplayName("Article Existence Test")
    void is_article_existed() throws SQLException {
        String articleId = "1";

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        boolean success = dao.isArticleExisted(articleId);
        assertTrue(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement).setString(eq(1), stringArgumentCaptor.capture());

        assertEquals("1", stringArgumentCaptor.getAllValues().get(0));

        verify(statement).close();
        verify(connection).close();
    }

    @Test
    @DisplayName("Add Article Test")
    void add_article() throws SQLException {
        String userId = "1";
        String content = "this is an article for testing.";
        String imageUrl = "res/test1.jpg";

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        boolean success = dao.addArticle(userId,content,imageUrl);
        assertTrue(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(statement, times(4)).setString(integerArgumentCaptor.capture(), stringArgumentCaptor.capture());

        assertAll(
                ()->assertEquals("1",stringArgumentCaptor.getAllValues().get(1)),
                ()->assertEquals("this is an article for testing.",stringArgumentCaptor.getAllValues().get(2)),
                ()->assertEquals("res/test1.jpg",stringArgumentCaptor.getAllValues().get(3))
        );

        verify(statement).executeUpdate();
//        这个可以不调用吗 不提交的话好像是不会修改数据库的吧
//        verify(connection).commit();
        verify(statement,times(2)).close();
        verify(connection,times(2)).close();
    }

    @Test
    @DisplayName("Get Current Article Test")
    void get_current_articles() throws SQLException {
        int front = 12;
        int num = 3;
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        assertNotNull(dao.getCurrentArticles(front,num));

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(statement).setInt(eq(1), integerArgumentCaptor.capture());

        assertEquals(12, integerArgumentCaptor.getAllValues().get(0));

        verify(statement).close();
        verify(connection).close();
    }

    @Test
    @DisplayName("Get Previous Article Test")
    void get_previous_articles() throws SQLException {
        int tail = 12;
        int num = 3;
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        assertNotNull(dao.getCurrentArticles(tail,num));

        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(statement).setInt(eq(1), integerArgumentCaptor.capture());

        assertEquals(12, integerArgumentCaptor.getAllValues().get(0));

        verify(statement).close();
        verify(connection).close();
    }

    @Test
    @DisplayName("Delete Article Test")
    void delete_article() throws SQLException {
        String articleId = "1";

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        boolean success = dao.deleteArticle(articleId);
        assertTrue(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement, times(2)).setString(eq(1), stringArgumentCaptor.capture());

        assertEquals("1",stringArgumentCaptor.getAllValues().get(0));
        assertEquals("1",stringArgumentCaptor.getAllValues().get(1));

        verify(statement).executeUpdate();
//        这个可以不调用吗 不提交的话好像是不会修改数据库的吧
//        verify(connection).commit();
        verify(statement,times(2)).close();
        verify(connection,times(2)).close();
    }

    @Test
    @DisplayName("Get Image Url of An Article Test")
    void get_image_url_of_article() throws SQLException {
        String articleId = "1";
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        String result = dao.getImageUrlOfArticle(articleId);
        assertNull(result);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement,times(2)).setString(eq(1), stringArgumentCaptor.capture());

        assertEquals("1", stringArgumentCaptor.getAllValues().get(0));
        assertEquals("1", stringArgumentCaptor.getAllValues().get(1));
        verify(statement,times(2)).close();
        verify(connection,times(2)).close();
    }
}