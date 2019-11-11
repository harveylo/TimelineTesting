package com.ceej.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataBaseUtilityTest {

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    DataBaseUtility dao;

    class DataBaseUtilFake extends DataBaseUtility {
        @Override
        protected Connection createConnection() {
            return connection;
        }
    }

    @BeforeEach
    void setUp() {
        dao = new DataBaseUtilFake();
    }

    @Test
    void check_user_exist() throws Exception {
        String userId = "1";

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        boolean success = dao.isUserExisted(userId);
//        不连上数据库这里不可能返回true吧
        assertFalse(success);

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(statement).setString(eq(1),stringArgumentCaptor.capture());

        assertEquals("1",stringArgumentCaptor.getAllValues().get(0));

//        verify(statement).executeUpdate();
//        verify(connection).commit();
        verify(statement).close();
        verify(connection).close();
    }
}