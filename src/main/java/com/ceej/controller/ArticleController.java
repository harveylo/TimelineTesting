package com.ceej.controller;


import com.ceej.model.Article;
import com.ceej.model.JsonMsg;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/article")
public class ArticleController {

    DataBaseUtility dbutil;
    FileOperator fileOperator;

    public ArticleController() {
        dbutil = new DataBaseUtility();
        fileOperator = new FileOperator();
    }

    public ArticleController( DataBaseUtility dbutil, FileOperator fop) {
        this.dbutil = dbutil;fileOperator = fop;
    }

    public boolean hasJsonKey(JSONObject json, String key){
        if(!json.has(key)) return false;
        return true;
    }

    @RequestMapping(value="/ArticlePublish",method= RequestMethod.POST)
    @ResponseBody
    public JsonMsg publishAnArticle(@RequestBody String json){

        String userID; String content; String imgData="";String imgUrl="";
        JSONObject object = JSONObject.fromObject(json);

        JsonMsg jsonMsg=new JsonMsg();
        if(!hasJsonKey(object, "userID") || !hasJsonKey(object, "content")  ){
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        userID = object.getString("userID");
        content = object.getString("content");
        if(hasJsonKey(object, "imgData")){
            imgData = object.getString("imgData");
            //生成图片名
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("ddHHssSSS");
            Date date = new Date();
            String str = simpleDateFormat.format(date);
            Random random = new Random();
            int rannum = random.nextInt() * (99999 - 10000 + 1) + 10000;// 获取5位随机数
            String imgname = rannum + "" + str;
            String url = "http://"+DataBaseUtility.res_url + imgname;
            //String imgPath = new String();
            String type= fileOperator.getImage(imgData, imgname);
            if (type != "false") imgUrl= url+type;
            else {
                jsonMsg.setCode("404");
                return jsonMsg;
            }
        }

        jsonMsg.setCode("205");
        Article article = new Article();
        article.setUserID(userID);
        article.setContent(content);
        article.setImageURL(imgUrl);
        if(!dbutil.isUserExisted(userID)){
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        if(!dbutil.addArticle(article.getUserID(), article.getContent(), article.getImageURL())) {
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        jsonMsg.setData(true);
        return jsonMsg;
    }

    @RequestMapping(value="/ArticleDelete",method= RequestMethod.POST)
    @ResponseBody
    public JsonMsg deleteAnArticle(@RequestBody String json){
        JSONObject object = JSONObject.fromObject(json);
        String articleID;

        JsonMsg jsonMsg = new JsonMsg();
        if(!hasJsonKey(object, "articleID")   ){
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        articleID = object.getString("articleID");

        jsonMsg.setCode("205");
        Article article = new Article();
        article.setArticleID(articleID);
        if(!dbutil.isArticleExisted(article.getArticleID())){
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        boolean suc = dbutil.deleteArticle(article.getArticleID());
        jsonMsg.setData(suc);
        return jsonMsg;
    }

    @RequestMapping(value="/GetNewArticle",method= RequestMethod.POST)
    @ResponseBody
    public JsonMsg retrieveNewestArticles(@RequestBody String json){
        JSONObject object = JSONObject.fromObject(json);
        int front_article_id; int max_num;
        JsonMsg jsonMsg = new JsonMsg();

        if(!hasJsonKey(object, "front_article_id") || !hasJsonKey(object, "max_num")  ){
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        front_article_id = object.getInt("front_article_id");
        max_num = object.getInt("max_num");

        jsonMsg.setCode("205");
        ArrayList<Article> articles=dbutil.getCurrentArticles(front_article_id,max_num);
        if(articles == null)
            articles = new ArrayList<Article>();
        jsonMsg.setData(articles);
        return jsonMsg;
    }

    @RequestMapping(value="/GetOldArticle",method= RequestMethod.POST)
    @ResponseBody
    public JsonMsg retrievePreviousArticles(@RequestBody String json){
        JSONObject object = JSONObject.fromObject(json);
        int tail_article_id; int max_num;
        JsonMsg jsonMsg = new JsonMsg();

        if(!hasJsonKey(object, "tail_article_id") || !hasJsonKey(object, "max_num")  ){
            jsonMsg.setCode("404");
            return jsonMsg;
        }
        tail_article_id = object.getInt("tail_article_id");
        max_num = object.getInt("max_num");

        jsonMsg.setCode("205");
        ArrayList<Article> articles=dbutil.getPreviousArticles(tail_article_id,max_num);
        if(articles == null)
            articles = new ArrayList<Article>();
        jsonMsg.setData(articles);
        return jsonMsg;
    }

}
