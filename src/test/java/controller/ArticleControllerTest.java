package controller;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ArticleControllerTest {
    DataBaseUtility mock_dbUtil;

    @BeforeAll
    public void mocksetup(){
        mock_dbUtil = mock(DataBaseUtility.class);
    }

    @Test
    @DisplayName("user")

}