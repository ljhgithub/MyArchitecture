package com.ljh.www.myarchitecture.data.source;

import com.ljh.www.myarchitecture.data.source.remote.RemoteGithubDataSource;
import com.ljh.www.myarchitecture.model.Github;
import com.ljh.www.myarchitecture.util.log.LogUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljh on 2016/10/13.
 */

public class GithubDataStore {

    private static final String TAG = LogUtils.makeLogTag(GithubDataStore.class.getSimpleName());
    private Map<String, Github> mCachedGithubs;
    private boolean mCacheIsExpire = true;

    public void getGithubList(DataCallback<List<Github>> callback) {
        LogUtils.LOGD(TAG, "" + mCachedGithubs + mCacheIsExpire);
        if (null != mCachedGithubs && !mCacheIsExpire) {

            callback.onSuccess(new ArrayList<>(mCachedGithubs.values()));
            return;
        }

        if (mCacheIsExpire) {
            fetchRemoteGithubData(callback);
        } else {
            //TODO 获取磁盘数据
        }

    }

    private void fetchRemoteGithubData(final DataCallback<List<Github>> callback) {
        new RemoteGithubDataSource().githubList(new DataCallback<List<Github>>() {
            @Override
            public void onError(int code, String msg) {
                callback.onError(code, msg);
            }

            @Override
            public void onSuccess(List<Github> githubs) {
                refreshCache(githubs);
                refreshLocalCache(githubs);
            }


        });
    }

    private void refreshGithubs() {
        mCacheIsExpire = true;
    }

    private void refreshLocalCache(List<Github> githubs) {
        //TODO 磁盘缓存操作
    }

    private void refreshCache(List<Github> githubs) {
        if (null == mCachedGithubs) {
            mCachedGithubs = new LinkedHashMap<>(githubs.size());
        }
        mCachedGithubs.clear();
        for (Github g : githubs) {
            mCachedGithubs.put(g.current_user_url, g);
        }
        mCacheIsExpire = false;
    }
}
