package com.zaozao.hu.tools.http;

import com.zaozao.hu.module.model.BaseEntity;

import rx.Observer;

/**
 * Created by 胡章孝
 * Date:2018/7/16
 * Describle:
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {

    }
}
