package com.zaozao.hu.module.viewModel;

import com.zaozao.hu.module.model.AppInfo;
import com.zaozao.hu.module.model.BaseEntity;
import com.zaozao.hu.module.view.FunctionListAty;
import com.zaozao.hu.tools.http.BaseRetrofitFactory;
import com.zaozao.hu.tools.http.BaseRetrofitTwoService;
import com.zaozao.hu.tools.http.MyTransformer;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;

/**
 * Created by 胡章孝
 * Date:2018/7/17
 * Describe:
 */
public class FunctionViewModel {


    public void getAppInfo(FunctionListAty aty) {
        BaseRetrofitFactory baseRetrofitFactory = new BaseRetrofitFactory.Builder().build();
        BaseRetrofitTwoService apiService = baseRetrofitFactory.getAPI();
        Observable<BaseEntity<AppInfo>> observable = apiService.post("appInfo.php", null);
        observable.compose(new MyTransformer<>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                }).subscribe(new Observer<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }
}
