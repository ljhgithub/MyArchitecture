package com.ljh.www.myarchitecture.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ljh.www.myarchitecture.R;
import com.ljh.www.myarchitecture.data.net.HttpService;
import com.ljh.www.myarchitecture.data.RemoteDataSource;
import com.ljh.www.myarchitecture.http.RetrofitProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ljh on 2016/4/26.
 */
public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(AppCompatActivity appCompatActivity) {
        Intent it = new Intent(appCompatActivity, RxJavaActivity.class);
        appCompatActivity.startActivity(it);
    }

    private ImageView imageView;
    private TextView tvResult;

    private static String IMAGE_URL = "http://img01.lianzhong.com/upload/newbbs/2013/03/06/562/128564395004891.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        findViewById(R.id.btn_test).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.btn_scheduler).setOnClickListener(this);
        findViewById(R.id.btn_map).setOnClickListener(this);
        findViewById(R.id.btn_rxjava_retrofit).setOnClickListener(this);
        tvResult = (TextView) findViewById(R.id.tv_result);
        findViewById(R.id.btn_flat_map).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                testRxJava();
                break;
            case R.id.btn_scheduler:
                scheduler();
                break;
            case R.id.btn_map:
                map(IMAGE_URL);
                break;
            case R.id.btn_rxjava_retrofit:
                rxJavaRetrofitDownloadImage();
                break;
            case R.id.btn_flat_map:
                rxJavaFlatMap();
                break;
            default:
                break;
        }
    }

    private void rxJavaFlatMap() {
        HttpService service = RetrofitProvider.getRetrofitRxJava().create(HttpService.class);
        service.homepaeInfo(RetrofitActivity.TOKEN)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<RemoteDataSource, Observable<List<ListItem>>>() {
                    @Override
                    public Observable<List<ListItem>> call(RemoteDataSource remoteDataSource) {
                        Log.d("tag", "flatMap " + remoteDataSource.info);
                        return getListItemObservable();
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ListItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("tag", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<ListItem> listItems) {
                        tvResult.setText(listItems.get(0).toString());
                    }
                });
    }

    protected class ListItem {
        Long createTime;
        String name;
        Integer id;

        @Override
        public String toString() {
            return "createTime:" + createTime + "name:" + name + "id:" + id;
        }
    }

    private Observable<List<ListItem>> getListItemObservable() {
        HttpService service = RetrofitProvider.getRetrofitRxJava().create(HttpService.class);
        Observable<List<ListItem>> observable = service.rxTypeList("5ff74b642d045d0ad33093d367b16f3d", System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d("tag", "doOnSubscribe " + Thread.currentThread().getName() + Thread.currentThread().getId());

                    }
                })
                .map(new Func1<RemoteDataSource, List<ListItem>>() {
                    @Override
                    public List<ListItem> call(RemoteDataSource remoteDataSource) {
                        Log.d("tag", "map call " + Thread.currentThread().getName() + Thread.currentThread().getId());
                        List<ListItem> listItems = null;
                        try {
                            JSONObject dataJson = new JSONObject(remoteDataSource.data);
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<ListItem>>() {
                            }.getType();
                            listItems = gson.fromJson(dataJson.optString("list"), type);
                            Log.d("tag", "listItems" + listItems.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return listItems;
                    }
                });
        return observable;
    }

    private void rxJavaRetrofitDownloadImage() {
        HttpService service = RetrofitProvider.getRetrofitRxJava().create(HttpService.class);
        Observable<List<ListItem>> observable = service.rxTypeList("5ff74b642d045d0ad33093d367b16f3d", System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.d("tag", "doOnSubscribe " + Thread.currentThread().getName() + Thread.currentThread().getId());

                    }
                })
                .map(new Func1<RemoteDataSource, List<ListItem>>() {
                    @Override
                    public List<ListItem> call(RemoteDataSource remoteDataSource) {
                        Log.d("tag", "map call " + Thread.currentThread().getName() + Thread.currentThread().getId());
                        List<ListItem> listItems = null;
                        try {
                            JSONObject dataJson = new JSONObject(remoteDataSource.data);
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<ListItem>>() {
                            }.getType();
                            listItems = gson.fromJson(dataJson.optString("list"), type);
                            Log.d("tag", "listItems" + listItems.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return listItems;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber<List<ListItem>>() {

            @Override
            public void onCompleted() {
                Log.d("tag", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("tag", "subscribe onError: " + e.getMessage());
            }

            @Override
            public void onNext(List<ListItem> listItems) {
                Log.d("tag", "onNext " + Thread.currentThread().getName() + Thread.currentThread().getId());
                tvResult.setText(listItems.get(0).toString());
            }
        });
    }

    private void map(final String url) {
        Observable.just(url)
                .observeOn(Schedulers.io())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String str) {
                        return getBitmap(str);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        if (null != bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }

                    }
                });
    }

    private Bitmap getBitmap(String url) {
        try {
            HttpService service = RetrofitProvider.getRetrofit().create(HttpService.class);
            Response<ResponseBody> responseBody = service.downloadImage(url).execute();
            Log.d("tag", "response" + responseBody.isSuccessful() + responseBody.code() + responseBody.message());
            if (HttpURLConnection.HTTP_OK == responseBody.code()) {
                byte[] b = responseBody.body().bytes();
                return BitmapFactory.decodeByteArray(b, 0, b.length);

            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scheduler() {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = getBitmap(IMAGE_URL);
                if (null == bitmap) {
                    subscriber.onError(new Throwable("not found image"));
                } else {
                    subscriber.onNext(bitmap);
                    subscriber.onCompleted();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("tag", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    private void testRxJava() {


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d("tag", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("tag", "onError" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d("tag", "onNext" + s);
            }
        };


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d("tag", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("tag", "onError" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d("tag", "onNext" + s);
            }
        };
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("tag", "call" + s);
            }
        };

        Action1<Throwable> errorAction1 = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d("tag", "call Throwable" + throwable.getMessage());
            }
        };
        Action0 completeAction0 = new Action0() {
            @Override
            public void call() {
                Log.d("tag", "call complete");
            }
        };
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("subscriber");
//            }
//        });

//        Observable<String> observable=Observable.just("1","2","3");
//        Observable<String> observable = Observable.from(new String[]{"11", "22", "33"});
//        observable.subscribe(subscriber);
//        observable.subscribe(action1,errorAction1,completeAction0);

        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                HttpService service = RetrofitProvider.getRetrofit().create(HttpService.class);
                service.downloadImage(IMAGE_URL).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("tag", "response" + response.isSuccessful() + response.code());
                        if (HttpURLConnection.HTTP_OK == response.code()) {
                            try {
                                byte[] b = response.body().bytes();
                                subscriber.onNext(BitmapFactory.decodeByteArray(b, 0, b.length));
                                subscriber.onCompleted();
                            } catch (IOException e) {
                                e.printStackTrace();
                                subscriber.onError(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        subscriber.onError(t);
                    }
                });

//
            }
        }).subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("tag", e.getMessage());
            }

            @Override
            public void onNext(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
