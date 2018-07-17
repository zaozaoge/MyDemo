package com.zaozao.hu.tools.http;

import android.widget.Switch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 胡章孝
 * Date:2018/7/16
 * Describe:初始化Retrofit2.0
 */
public class BaseRetrofitFactory {

    /**
     * 添加header响应头的集合，用于存放追加的header头数据
     */
    private Map<String, String> map = new HashMap<>();
    private MyInterceptor myInterceptor = null;
    private BaseRetrofitTwoService apiFunction;

    private BaseRetrofitFactory(Builder builder) {
        map.clear();
        this.map = builder.map;
        myInterceptor = new MyInterceptor(map);
        apiFunction = getRetrofit().create(BaseRetrofitTwoService.class);
    }

    // private HttpLoggingInterceptor
    public static class Builder {
        Map<String, String> map = new HashMap<>();

        public Builder setHeaders(Map<String, String> map) {
            this.map = map;
            return this;
        }

        public BaseRetrofitFactory build() {
            return new BaseRetrofitFactory(this);
        }
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(BaseRetrofitConfig.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(BaseRetrofitConfig.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(BaseRetrofitConfig.TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(myInterceptor)
                .build();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BaseRetrofitConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getHttpClient())
                .build();
    }

    /**
     * 每次ping 携带请求头 从OkHttp中拦截，Retrofit2只能使用注解方式添加headers
     */
    public class MyInterceptor implements Interceptor {
        private Map<String, String> headers;

        public MyInterceptor(Map<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response;
            synchronized (this) {
                Request.Builder builder = chain.request().newBuilder();
                if (headers != null && headers.size() > 0) {
                    Set<String> keys = headers.keySet();
                    for (String headerKey : keys) {
                        builder.addHeader(headerKey, headers.get(headerKey)).build();
                    }
                }
                response = chain.proceed(builder.build());
            }
            return response;
        }
    }

    public BaseRetrofitTwoService getAPI() {
        return apiFunction;
    }
}
