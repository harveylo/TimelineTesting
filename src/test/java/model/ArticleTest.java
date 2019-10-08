package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleTest {
    Article article;

    @BeforeEach
    public void init(){
        article = new Article();
    }

    @Test
    @DisplayName("设置时间为now")
    public void set_article_to_now(){
        article.setTimeStamp("2019-10-08 14:50:00");
        assertEquals("刚刚", article.getTimeStamp());
    }

    @Test
    @DisplayName("设置时间为5分钟前")
    public void set_article_to_five_min_before(){
        article.setTimeStamp("2019-10-08 14:45:00");
        assertEquals("5分钟前", article.getTimeStamp());
    }

    @Test
    @DisplayName("设置时间为5小时前")
    public void set_article_to_five_hours_before(){
        article.setTimeStamp("2019-10-08 09:40:00");
        assertEquals("5小时前", article.getTimeStamp());
    }

    @Test
    @DisplayName("设置时间为一天前")
    public void set_article_to_one_day_before(){
        article.setTimeStamp("2019-10-07 09:40:00");
        assertEquals("昨天 09:40", article.getTimeStamp());
    }

    @Test
    @DisplayName("设置时间为一个月前")
    public void set_article_to_one_month_before(){
        article.setTimeStamp("2019-09-08 09:40:00");
        assertEquals("9月8日9时", article.getTimeStamp());
    }

    @Test
    @DisplayName("设置时间为一年前")
    public void set_article_to_one_year_before(){
        article.setTimeStamp("2018-09-08 09:40:00");
        assertEquals("2018年9月8日", article.getTimeStamp());
    }
}