package com.ceej.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ResourceUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOperator {
    public String getImage(String imgStr, String imgname) {
        if (imgStr == null)
            return "false";
        //判断图片类型，处理base64前缀
        String type=".jpg";
        String suffix[]={".jpg",".gif",".png",".jpg"};
        String prefix[]={"data:image/jpg;base64,","data:image/gif;base64,","data:image/png;base64,","data:image/jpeg;base64,"};
        for(int i=0;i<prefix.length;i++)
        {
            if(imgStr.indexOf(prefix[i])>=0) {
                imgStr=imgStr.replace(prefix[i], "");
                type=suffix[i];
            }
        }

        Base64 decoder = new Base64();
        try {
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String realPath=DataBaseUtility.res_url;
            String path = ResourceUtils.getURL("classpath:").getPath();
            String imgFilePath = path + "res/"+ imgname + type;

            OutputStream out = new FileOutputStream(imgFilePath);
            try {
                out.write(b);
                out.flush();
            }finally {
                out.close();
            }
            return type;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";

        }
    }
}