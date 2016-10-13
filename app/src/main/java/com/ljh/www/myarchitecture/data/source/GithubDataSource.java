package com.ljh.www.myarchitecture.data.source;

import com.ljh.www.myarchitecture.model.Github;

import java.util.List;

/**
 * Created by ljh on 2016/10/12.
 */

public interface GithubDataSource {
    public void githubList(DataCallback<List<Github>> callback);
}
