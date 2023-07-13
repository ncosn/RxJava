package com.sgcc.yzd.rxjava;

public interface Observer<T> {
    void onSubscribe();

    void onNext(T t);

    void onComplete();

    void onError(Throwable throwable);

}
