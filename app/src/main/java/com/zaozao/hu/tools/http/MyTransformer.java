package com.zaozao.hu.tools.http;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 胡章孝
 * Date:2018/7/17
 * Describe:
 */
public class MyTransformer<T> implements Observable.Transformer<T, T> {


    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
