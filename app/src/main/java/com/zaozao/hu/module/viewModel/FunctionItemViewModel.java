package com.zaozao.hu.module.viewModel;

import android.databinding.ObservableArrayList;

import com.zaozao.hu.module.model.FunctionItem;
import com.zaozao.hu.module.view.FunctionListAty;

/**
 * Created by 胡章孝
 * Date:2018/7/5
 * Describle:
 */
public class FunctionItemViewModel {
    public ObservableArrayList<FunctionItem> functionItems = new ObservableArrayList<>();

    public FunctionItemViewModel(FunctionListAty functionListAty) {

        FunctionItem item = new FunctionItem();
        item.setItemTitle("Http协议");
        item.setItemContent("https://blog.csdn.net/green703338130/article/details/79402405");
        functionItems.add(item);
    }
}
