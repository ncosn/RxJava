package com.sgcc.yzd.rxjava;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 手写 RxLifecycle 核心部分
 *
 * @param <T>
 */
public class RxLifecycle<T> implements LifecycleObserver, ObservableTransformer<T,T> {

    final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        Log.i("TAG", "onDestroy: ");
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        // 调用 subscribe 方法时，会触发订阅操作，然后 doOnSubscribe 方法就会被执行
        return upstream.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                compositeDisposable.add(disposable);
            }
        });
    }

    public static <T> RxLifecycle<T> bindRxLifecycle(LifecycleOwner lifecycleOwner) {
        RxLifecycle<T> lifecycle = new RxLifecycle();
        lifecycleOwner.getLifecycle().addObserver(lifecycle);
        return lifecycle;
    }
}
