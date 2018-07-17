package com.zaozao.hu.tools;

import android.databinding.BindingAdapter;
import android.webkit.WebView;
import android.widget.ImageView;

/**
 * Created by 胡章孝
 * Date:2018/7/6
 * Describle:
 */
public class DatabindingUtil {

//    @BindingAdapter({"bind:image"})
//    public static void loadImage(ImageView imageView, String url) {
//
//    }

    @BindingAdapter({"bind:webUrl"})
    public static void loadWebUrl(WebView webView, String webUrl) {
        webView.loadUrl(webUrl);
    }
}
