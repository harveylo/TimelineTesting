package com.harveylo.model;

public class JsonMsg{
    private String code;
    private Object data;
    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code=code;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data=data;
    }
}
