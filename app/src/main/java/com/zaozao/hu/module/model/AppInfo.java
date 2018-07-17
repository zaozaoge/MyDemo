package com.zaozao.hu.module.model;

/**
 * Created by 胡章孝
 * Date:2018/7/13
 * Describle:
 */
public class AppInfo {


    /**
     * code : 200
     * message : 获取成功
     * app_name : Android????
     * app_version_name : 1.0
     * app_version_code : 10
     * app_download_url : http://192.168.2.1
     * app_update_content : ?????bug???bug??
     */
    private String code;
    private String message;
    private String app_name;
    private String app_version_name;
    private String app_version_code;
    private String app_download_url;
    private String app_update_content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_version_name() {
        return app_version_name;
    }

    public void setApp_version_name(String app_version_name) {
        this.app_version_name = app_version_name;
    }

    public String getApp_version_code() {
        return app_version_code;
    }

    public void setApp_version_code(String app_version_code) {
        this.app_version_code = app_version_code;
    }

    public String getApp_download_url() {
        return app_download_url;
    }

    public void setApp_download_url(String app_download_url) {
        this.app_download_url = app_download_url;
    }

    public String getApp_update_content() {
        return app_update_content;
    }

    public void setApp_update_content(String app_update_content) {
        this.app_update_content = app_update_content;
    }
}
