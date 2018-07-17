package com.zaozao.hu.module.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import com.zaozao.hu.R
import com.zaozao.hu.config.Constants
import com.zaozao.hu.module.model.FunctionItem
import kotlinx.android.synthetic.main.activity_web_page.*

class WebPageActivity : BaseActivity() {

    private val tag: String = WebPageActivity@ this.javaClass.simpleName
    private var functionItem: FunctionItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_page)
        functionItem = intent.getSerializableExtra(Constants.FunctionItem) as FunctionItem?
        initWebView()
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun initWebView() {
        webView.addJavascriptInterface(this, "android")
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChromeClient
        webView.settings.javaScriptEnabled = true //允许使用js
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE //不使用缓存，只从网络获取数据
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true //支持屏幕缩放
        webView.loadUrl(functionItem!!.itemContent)
    }

    //帮助webView处理各种通知，请求事件
    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.i(tag, "页面加载完成")
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.i(tag, "页面开始加载")
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            Log.i(tag, "页面加载失败")
        }
    }

    //帮助webView处理与js的交互
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            Log.i(tag, "网页标题$title")
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Log.i(tag, "进度回调$newProgress")
        }
    }

    /**
     * 重写返回键的逻辑
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 页面销毁时销毁WebView
     */
    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }

    @JavascriptInterface
    private fun A(str: String) {
        //JS调用Android的方法
        runOnUiThread {
            TODO("更新UI必须在UI线程中处理")
        }
    }

    private fun B() {
        //Android调用JS的方法 假设C是Js中的方法
        webView.loadUrl("javascript:C()")
    }
}
