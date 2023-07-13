package com.sgcc.yzd.rxjava;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    private final Subject<Object> mBus;

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

    private RxBus() {

        // 除了onSubscribe,onNext,onError,onComplete
        // 支持线程安全
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object event) {
        mBus.onNext(event);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }
}
