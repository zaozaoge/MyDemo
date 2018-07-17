package com.zaozao.hu.tools.http;

import com.zaozao.hu.module.model.AppInfo;
import com.zaozao.hu.module.model.BaseEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 胡章孝
 * Date:2018/7/16
 * Describe:
 */
public interface BaseRetrofitTwoService {
    /**
     * Get方式请求网络接口
     *
     * @param url 请求地址
     * @param map 请求参数
     * @return
     */
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, String> map);

    /**
     * post方式请求网络参数
     *
     * @param url 请求地址
     * @param map 请求参数
     * @return
     */
    @POST
    Observable<BaseEntity<AppInfo>> post(@Url String url, @QueryMap Map<String, String> map);

    /**
     * post实体
     *
     * @param url  请求地址
     * @param body 请求实体
     * @return
     */
    @POST
    Observable<String> postBody(@Url String url, @Body String body);

    /**
     * 请求表单
     *
     * @param url
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<String> postFiled(@Url String url, @FieldMap Map<String, String> map);

    /**
     * 上传文件
     *
     * @param url  上传地址
     * @param file 上传文件
     * @return
     */
    @Multipart
    @POST
    Observable<String> upLoadFile(@Url String url, @Part MultipartBody.Part file);

    /**
     * 多文件上传
     *
     * @param url         上传地址
     * @param description 上传文件名
     * @param maps        上传文件
     * @return
     */
    @Multipart
    @POST
    Observable<String> upLoadFiles(@Url String url, @Part("fileName") String description, @PartMap Map<String, RequestBody> maps);

    /**
     * 下载文件
     *
     * @param fileUrl 下载地址
     * @return
     */
    @Streaming
    @GET
    Observable<String> downloadFile(@Url String fileUrl);
}
