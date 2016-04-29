package com.ljh.www.myarchitecture.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ljh.www.myarchitecture.R;
import com.ljh.www.myarchitecture.data.HttpService;
import com.ljh.www.myarchitecture.data.RemoteDataSource;
import com.ljh.www.myarchitecture.http.RetrofitHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener, Callback<RemoteDataSource> {

    public static final String TOKEN = "5ff74b642d045d0ad33093d367b16f3d";
    private ImageView iv;

    public static void start(AppCompatActivity appCompatActivity){
        Intent it = new Intent(appCompatActivity, RetrofitActivity.class);
        appCompatActivity.startActivity(it);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_request_body).setOnClickListener(this);
        findViewById(R.id.btn_upload_image).setOnClickListener(this);
        findViewById(R.id.btn_download_image).setOnClickListener(this);
        findViewById(R.id.btn_download_file).setOnClickListener(this);
        findViewById(R.id.btn_interceptor).setOnClickListener(this);
        findViewById(R.id.btn_authenticate).setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.image);
//        try {
//          Source source= Okio.source(new File("/storage/emulated/0/DCIM/1.jpg" ));
////           BufferedSource bufferedSource= Okio.buffer(source);
//
//           Sink sink= Okio.sink(new File("/storage/emulated/0/DCIM/55.jpg" ));
//            BufferedSink bufferedSink=Okio.buffer(sink);
//            bufferedSink.writeAll(source);
//            bufferedSink.close();
//            source.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_post:
                post();
                break;
            case R.id.btn_request_body:
                requestBody();
                break;
            case R.id.btn_upload_image:
                uploadImage();
//                uploadImages();
                break;
            case R.id.btn_download_image:
                downloadImage();
                break;
            case R.id.btn_download_file:
                downloadFile();
                break;
            case R.id.btn_interceptor:
                testInterceptor();
                break;
            case R.id.btn_authenticate:
                testAuthenticate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(Call<RemoteDataSource> call, Response<RemoteDataSource> response) {
        Log.d("tag", "request header" + call.request().headers() + call.request().tag());
        Log.d("tag", "response header" + response.headers());
        if (HttpURLConnection.HTTP_OK == response.code()) {
            if (0 == response.body().status) {
                Log.d("tag", "data" + response.code() + call.request().url());
                Log.d("tag", "code" + response.code() + "status" + response.body().status + response.body().info);
            } else {
                Log.d("tag", "status" + response.body().status);
            }
        }

        try {
            Log.d("tag", "code" + response.code()+response.errorBody().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<RemoteDataSource> call, Throwable t) {
        Log.d("tag", "onFailure" + t.getMessage());
    }

    private void get() {
        HttpService service = RetrofitHelper.getRetrofit().create(HttpService.class);
        Call<RemoteDataSource> call = service.typeList(TOKEN, System.currentTimeMillis());
        call.enqueue(this);
    }

    private void post() {
        HttpService service = RetrofitHelper.getRetrofit().create(HttpService.class);
        Call<RemoteDataSource> call = service.patientList(0, 10, TOKEN);
        call.enqueue(this);
    }

    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }


    private void uploadImage() {
        HttpService service = RetrofitHelper.getRetrofit().create(HttpService.class);
        MediaType TEXT = MediaType.parse("application/text; charset=utf-8");
        RequestBody token = RequestBody.create(TEXT, TOKEN);
        MediaType IMAGE = MediaType.parse("image/*");
        File file1 = new File("/storage/emulated/0/DCIM/1.jpg");
        RequestBody image = RequestBody.create(IMAGE, file1);
        Call<RemoteDataSource> call = service.uploadImage(token, image);
        call.enqueue(this);
    }

    private void uploadImages() {
        HttpService service = RetrofitHelper.getRetrofit().create(HttpService.class);
        MediaType TEXT = MediaType.parse("application/text; charset=utf-8");
        RequestBody token = RequestBody.create(TEXT, TOKEN);
        MediaType IMAGE = MediaType.parse("image/*");
        File file1 = new File("/storage/emulated/0/DCIM/1.jpg");
        File file2 = new File("/storage/emulated/0/DCIM/2.jpg");
        RequestBody image1 = RequestBody.create(IMAGE, file1);
        RequestBody image2 = RequestBody.create(IMAGE, file1);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("imageList\"; filename=\"" + file1.getName() + "", image1);
        params.put("imageList\"; filename=\"" + file2.getName() + "", image2);
        Call<RemoteDataSource> call = service.uploadImages(token, params);
        call.enqueue(this);
    }

    private void requestBody() {
        JSONObject p = new JSONObject();
        try {
            p.put("recordId", 597);
            p.put("recordItemId", 2);
            JSONObject patient = new JSONObject();
            patient.put("recordId", "597");
            patient.put("mainSymptom", "aaaa");
            patient.put("historyTreatment", "aaaa");
            patient.put("requirement", "8");
            JSONArray imgs = new JSONArray();
            imgs.put("201604211019041v89Y8.png");
            p.put("patientPresent", patient);
            p.put("imageKeyList", imgs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, p.toString());

        HttpService service = RetrofitHelper.getRetrofit().create(HttpService.class);
        Call<RemoteDataSource> call = service.updateComplaint(TOKEN, body);

        call.enqueue(this);
    }

    private void downloadImage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://img1.3lian.com")
                .build();
        HttpService service = retrofit.create(HttpService.class);
        service.downloadImage("http://img01.lianzhong.com/upload/newbbs/2013/03/06/562/128564395004891.png").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("tag", response.code() + response.message() + call.request().url());
                try {
                    byte[] b = response.body().bytes();
                    Log.d("tag", b.length + "");
                    iv.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }


    private void downloadFile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://img1.3lian.com")
                .build();
        HttpService service = retrofit.create(HttpService.class);
        service.downloadFile("http://www.wandoujia.com/apps/cn.damai/binding?source=wandoujia-web_direct_binded").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("tag", response.code() + response.message() + call.request().url());
                BufferedInputStream fis = new BufferedInputStream(response.body().byteStream());
                getFile(fis, Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + "app.apk");
                Log.d("tag", fis.toString() + "" + Environment.getExternalStorageDirectory().getPath());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

    public void getFile(BufferedInputStream fis, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath, fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte[] b = new byte[1024];
            while (fis.read(b) != -1) {
                bos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void testInterceptor() {
        Retrofit retrofit = RetrofitHelper.getRetrofit("http://www.publicobject.com");
        HttpService service = retrofit.create(HttpService.class);
        Call<ResponseBody> call = service.testInterceptor();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("tag", "request" + call.request().headers().toString());
                Log.d("tag", "response" + response.headers().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void testAuthenticate(){
       OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override public Request authenticate(Route route, okhttp3.Response response) throws IOException {
                        Log.d("tag","Authenticating for response: " + response.request().header("Authorization"));
                        Log.d("tag","Challenges: " + response.challenges());
                        String credential = Credentials.basic("jesse", "password1");
                        if (credential.equals( response.request().header("Authorization"))){
                            return null;
                        }
                        return response.request().newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                })
                .build();

        final Request request = new Request.Builder()
                .url("http://publicobject.com/secrets/hellosecret.txt")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("tag"," Failure: " );
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("tag",call.request().headers()+"Authenticating for header"+response.request().headers());
                Log.d("tag"," response"+response.headers()+response.code()+response.message());
            }
        });

    }

}
