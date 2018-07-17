package com.zaozao.hu.module.viewModel;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;

import com.zaozao.hu.R;
import com.zaozao.hu.config.Constants;
import com.zaozao.hu.module.model.FunctionItem;
import com.zaozao.hu.module.view.BaseActivity;
import com.zaozao.hu.module.view.WebPageActivity;

/**
 * Created by 胡章孝
 * Date:2018/7/5
 * Describle:点击事件的处理
 */
public class MyHandler {
    private BaseActivity activity;

    public MyHandler(BaseActivity activity) {
        this.activity = activity;
    }

    public void onClick(View view, Object object) {
        Class cls = object.getClass();
        switch (cls.getSimpleName()) {
            case "FunctionItem":
                FunctionItem item = (FunctionItem) object;
                functionJump(item, view);
                break;
        }
    }

    private void functionJump(FunctionItem item, View view) {
        String itemTitle = item.getItemTitle();
        String itemContent = item.getItemContent();
        if (!TextUtils.isEmpty(itemContent) && itemContent.startsWith("http")) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (activity, view, activity.getResources().getString(R.string.webView));
            Intent intent = new Intent(activity, WebPageActivity.class);
            intent.putExtra(Constants.FunctionItem, item);
            activity.startActivity(intent, optionsCompat.toBundle());
        }
    }
}
