package view;

import controller.ArticleController;
import model.Article;

import java.util.ArrayList;
import java.util.Scanner;

public class TimelineSimpleInteractiveInterface {
    public static ArticleController controller = new ArticleController();
    public static ArrayList<Article> current_articles = new ArrayList<Article>();
    public static int presented_front = 0;
    public static int presented_back = 10000;

    public static void handleIntro(){
        System.out.println("Timeline用户界面，请按照指示输入：");
        System.out.println("1. 添加一篇文章");
        System.out.println("2. 删除一篇文章");
        System.out.println("3. 刷新最新动态");
        System.out.println("4. 更多过去动态");
        System.out.println("5. 退出Timeline");
    }
    public static void handleError(){
        System.out.println("错误输入，请按照指示输入：");
        System.out.println("1. 添加一篇文章");
        System.out.println("2. 删除一篇文章");
        System.out.println("3. 刷新最新动态");
        System.out.println("4. 更多过去动态");
        System.out.println("5. 退出Timeline");
    }
    public static void handleAdding(Scanner sc){
        String id,cont,url;
        System.out.println("请输入用户id");
        id = sc.next();
        System.out.println("请输入文章内容");
        cont = sc.next();
        System.out.println("请输入图片Url");
        url = sc.next();
        if(controller.publishAnArticle(id,cont,url))
            System.out.println("发布文章成功");
        else
            System.out.println("发布文章失败");
    }
    public static void handleDeleting(Scanner sc){
        String id;
        System.out.println("请输入文章id");
        id = sc.next();
        if(controller.deleteAnArticle(id))
            System.out.println("删除文章成功");
        else
            System.out.println("删除文章失败");
    }
    public static void handleRefreshing(Scanner sc){
        int num = 100;
        ArrayList<Article> ats = controller.retrieveNewestArticles(presented_front,100);
        if(ats.size() != 0){
            presented_front = Integer.valueOf(ats.get(0).getArticleID());
        }
        ats.addAll(current_articles);
        current_articles = ats;
        System.out.println("列出现有文章");
        for(Article a : current_articles){
            System.out.println(a.getNickname()+"-"+a.getContent()+"-"+a.getContent()+"-"+a.getImageURL());
        }
    }
    public static void handleReviewing(Scanner sc){
        int num = 10;
        ArrayList<Article> ats = controller.retrievePreviousArticles(presented_back,10);
        if(ats.size() != 0){
            presented_back = Integer.valueOf(ats.get(ats.size()-1).getArticleID());
        }
        current_articles.addAll(ats);
        System.out.println("列出现有文章");
        for(Article a : current_articles){
            System.out.println(a.getNickname()+"-"+a.getContent()+"-"+a.getContent()+"-"+a.getImageURL());
        }
    }

    public static void main(String[] args) {
        handleIntro();
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
            String input = sc.next();
            if(input.equals("1")){
                handleAdding(sc);handleIntro();
            }else if(input.equals("2")){
                handleDeleting(sc);handleIntro();
            }else if(input.equals("3")){
                handleRefreshing(sc);handleIntro();
            }else if(input.equals("4")){
                handleReviewing(sc);handleIntro();
            }else if(input.equals("5")){
                break;
            }else{
                handleError();
            }
        }
    }
}
