package com.zaozao.hu.tools.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡章孝
 * Date:2018/7/16
 * Describe:
 */
public class BaseRetrofitConfig {

    /**
     * 总路径
     */
    public static final String BASE_URL = "http://106.12.39.79/api/";
    /**
     * 默认超时时间
     */
    public static final int TIMEOUT = 15;
    /**
     * 需要追加的公共参数
     * param->未访问服务器前，把此map拼接到上传的参数中
     * header->访问服务器前，把此map拼接到header响应头中
     */
    private static Map<String, String> param = new HashMap<>();
    private static Map<String, String> header = new HashMap<>();


}
