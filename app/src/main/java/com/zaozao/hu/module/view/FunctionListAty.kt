package com.zaozao.hu.module.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zaozao.hu.R
import com.zaozao.hu.adapter.FunctionAdapter
import com.zaozao.hu.module.viewModel.FunctionItemViewModel
import com.zaozao.hu.module.viewModel.FunctionViewModel
import kotlinx.android.synthetic.main.activity_function_list_aty.*

class FunctionListAty : BaseActivity() {

    private var functionItemViewModel: FunctionItemViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_list_aty)
        init()
    }

    private fun init() {
        functionItemViewModel = FunctionItemViewModel(FunctionListAty@ this)
        val functionAdapter = FunctionAdapter(FunctionListAty@ this, functionItemViewModel!!.functionItems)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        function_list.layoutManager = linearLayoutManager
        function_list.adapter = functionAdapter
        FunctionViewModel().getAppInfo(this)
//        val appInfo = AppInfo()
//        appInfo.app_version_code = "2"
//        appInfo.app_download_url = "http://106.12.39.79/apk/app-xiaomi-release.apk"
//
//        UpdateManager(FunctionListAty@ this, appInfo).checkUpdate()
    }
}
