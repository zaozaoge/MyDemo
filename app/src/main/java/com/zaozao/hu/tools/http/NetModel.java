package com.zaozao.hu.tools.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 胡章孝
 * Date:2018/7/16
 * Describe:
 */
public class NetModel<T> {

    private Builder builder;

    /**
     * 全局headers
     */
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> dmap = new HashMap<>();

    private NetModel() {

    }

    private NetModel(Builder builder) {
        this.map = builder.map;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static NetModel getInstance() {
        return NetModelHolder.netModel;
    }

    /**
     * 静态内部类，实现线程安全，延迟加载、高效的单例模式
     */
    private static class NetModelHolder {
        private static NetModel netModel = new NetModel();
    }

    public void get(String url,Map<String,String> params){

    }
    public class Builder {
        Map<String, String> map;
        Map<String, String> params;


        public Builder setHeader(Map<String, String> map) {
            this.map = map;
            return this;
        }

        public Builder setMap(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public com.zaozao.hu.tools.http.NetModel build() {
            return new NetModel(this);
        }
    }
}
