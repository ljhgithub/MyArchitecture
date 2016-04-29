package com.ljh.www.myarchitecture.data;

/**
 * Created by ljh on 2016/4/24.
 */
public enum  HttpServiceTag {
     YB_ASSISTANT_TASK_TYPELIST("GET","/yb/assistant/task/typelist"),
        ;

    private String method;
    private String path;
    HttpServiceTag(String method, String path) {
        this.method=method;
        this.path=path;
    }
    public String getMethod(){
        return this.method;
    }
    public String getPath(){
        return this.path;
    }
}
