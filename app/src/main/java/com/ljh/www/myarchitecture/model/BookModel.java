package com.ljh.www.myarchitecture.model;

/**
 * Created by ljh on 2016/4/29.
 */
public class BookModel {
    public String url;
    public String name;

    public BookModel(){

    }
    public BookModel(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
