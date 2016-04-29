package com.ljh.www.myarchitecture;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ljh on 2016/4/20.
 */
public class ErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    interface MyCallback<T> {
        /** Called for [200, 300) responses. */
        void success(Response<T> response);
        /** Called for 401 responses. */
        void unauthenticated(Response<?> response);
        /** Called for [400, 500) responses, except 401. */
        void clientError(Response<?> response);
        /** Called for [500, 600) response. */
        void serverError(Response<?> response);
        /** Called for network errors while making the call. */
        void networkError(IOException e);
        /** Called for unexpected errors while making the call. */
        void unexpectedError(Throwable t);
    }
    interface MyCall<T> {
        void cancel();
        void enqueue(MyCallback<T> callback);
        MyCall<T> clone();

        // Left as an exercise for the reader...
        // TODO MyResponse<T> execute() throws MyHttpException;
    }
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Log.d("tag",returnType.toString()+""+annotations.length);
        if (getRawType(returnType) != MyCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "MyCall must have generic type (e.g., MyCall<ResponseBody>)");
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        final Executor callbackExecutor = retrofit.callbackExecutor();
        return new CallAdapter<MyCall<?>>() {
            @Override public Type responseType() {
                Log.d("tag","responseType"+responseType);
                return responseType;
            }

            @Override public <R> MyCall<R> adapt(Call<R> call) {
                Log.d("tag","adapt");
                return new MyCallAdapter<>(call, callbackExecutor);
            }
        };
    }

    static class MyCallAdapter<T> implements MyCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;

        MyCallAdapter(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
        }

        @Override public void cancel() {
            call.cancel();
        }

        @Override public void enqueue(final MyCallback<T> callback) {
            call.enqueue(new Callback<T>() {
                @Override public void onResponse(Call<T> call, Response<T> response) {
                    // TODO if 'callbackExecutor' is not null, the 'callback' methods should be executed
                    // on that executor by submitting a Runnable. This is left as an exercise for the reader.

                    int code = response.code();
                    Log.d("tag","response.code"+code);
                    if (code >= 200 && code < 300) {
                        callback.success(response);
                    } else if (code == 401) {
                        callback.unauthenticated(response);
                    } else if (code >= 400 && code < 500) {
                        callback.clientError(response);
                    } else if (code >= 500 && code < 600) {
                        callback.serverError(response);
                    } else {
                        callback.unexpectedError(new RuntimeException("Unexpected response " + response));
                    }
                }

                @Override public void onFailure(Call<T> call, Throwable t) {
                    // TODO if 'callbackExecutor' is not null, the 'callback' methods should be executed
                    // on that executor by submitting a Runnable. This is left as an exercise for the reader.

                    if (t instanceof IOException) {
                        callback.networkError((IOException) t);
                    } else {
                        callback.unexpectedError(t);
                    }
                }
            });
        }

        @Override public MyCall<T> clone() {
            return new MyCallAdapter<>(call.clone(), callbackExecutor);
        }
    }
}
