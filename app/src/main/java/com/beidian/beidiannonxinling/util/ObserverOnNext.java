package com.beidian.beidiannonxinling.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/2515:48
 * @描述: ------------------------------------------
 */

public abstract class ObserverOnNext<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }


}

