package com.ljh.www.myarchitecture.data.net;

import com.ljh.www.myarchitecture.data.RemoteDataSource;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ljh on 2016/4/20.
 */
public interface HttpService {
    @GET("/yb/assistant/task/typelist")
    Call<RemoteDataSource> typeList(
            @Header("Yb-assitant-Token") String token,
            @Query("t") long timestamp);

    @GET("/yb/assistant/task/typelist")
    Observable<RemoteDataSource> rxTypeList(
            @Header("Yb-assitant-Token") String token,
            @Query("t") long timestamp);

    @FormUrlEncoded
    @POST("/yb/ehr/ehrpatient/list")
    Call<RemoteDataSource> patientList(
            @Field("start") int start,
            @Field("count") int count,
            @Header("Yb-assitant-Token") String token);

    @POST("/yb/ehr/pathogenesisRecord/updatePatientPresent")
    Call<RemoteDataSource> updateComplaint(
            @Header("Yb-assitant-Token") String token,
            @Body RequestBody body
    );


    @Multipart
    @POST("/yb/ehr/uploadImage")
    Call<RemoteDataSource> uploadImage(
            @Part("Yb-assitant-Token") RequestBody token,
            @Part("imageList\"; filename=\"image.png\"") RequestBody imgs
    );

    @Multipart
    @POST("/yb/ehr/uploadImage")
    Call<RemoteDataSource> uploadImages(
            @Part("Yb-assitant-Token") RequestBody token,
            @PartMap Map<String, RequestBody> params
    );

    @GET
    Call<ResponseBody> downloadImage(@Url String url
    );

    @GET
    Observable<ResponseBody> rxDownloadImage(@Url String url
    );

    @GET
    Call<ResponseBody> downloadFile(@Url String url
    );

    @GET("/helloworld.txt")
    Call<ResponseBody> testInterceptor(
    );

    @POST("/yb/assistant/homepage/info")
    Observable<RemoteDataSource> homepaeInfo(
            @Header("Yb-assitant-Token") String token
    );

    @GET
    Observable<RemoteDataSource> google(@Url String url
    );

    @GET("/")
    Observable<ResponseBody> github(
    );
}
