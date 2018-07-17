package com.zaozao.hu.module.model;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * Created by 胡章孝
 * Date:2018/7/4
 * Describle:
 */
public class FunctionItem extends BaseObservable implements Serializable {

    private String itemTitle;
    private String itemContent;
    private String itemInsertDate;


    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemInsertDate() {
        return itemInsertDate;
    }

    public void setItemInsertDate(String itemInsertDate) {
        this.itemInsertDate = itemInsertDate;
    }
}
