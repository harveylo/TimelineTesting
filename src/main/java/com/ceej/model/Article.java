package com.ceej.model;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private String nickname;
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String userID;
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String timeStamp;
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String pushTime) {
//        System.out.println(pushTime);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
        this.timeStamp = getTimeDif(LocalDateTime.parse(pushTime,df));
    }

    private String articleID;
    public String getArticleID() {
        return articleID;
    }
    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    private String imageURL;
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        if(imageURL==null) imageURL="";
        this.imageURL = imageURL;
    }

    public static String getTimeDif(LocalDateTime past) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(past, now);


        if (past.getYear() != now.getYear()) {
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy年M月d日");
            return dtf1.format(past);
        }
        else if (past.getMonth() != now.getMonth()||duration.toDays()>1) {
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("M月d日H时");
            return dtf2.format(past);
        }
        else if (duration.toDays() == 1) {
            DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("HH:mm");
            return "昨天 " + dtf3.format(past);
        }
        else if (duration.toHours() >= 1) {
            return duration.toHours() + "" + "小时前";
        }
        else if (duration.toMinutes() > 0) {
            return duration.toMinutes() + "" + "分钟前";
        }
        else return "刚刚";
    }
}
