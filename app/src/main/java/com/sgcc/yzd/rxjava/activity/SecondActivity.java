package com.sgcc.yzd.rxjava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.sgcc.yzd.rxjava.R;
import com.sgcc.yzd.rxjava.RxLifecycle;
import com.sgcc.yzd.rxjava.SchedulerTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {
    Disposable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        /* 订阅中进行耗时操作， activity退出，解除订阅并捕获异常 */
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Log.i("TAG", "subscribe: 开始请求数据");
                Thread.sleep(5000);
                Log.i("TAG", "subscribe: 数据请求结束");
                emitter.onNext("Success");
                emitter.onComplete();
            }
        }).compose(new SchedulerTransformer<>())
                .compose(RxLifecycle.bindRxLifecycle(this))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.i("TAG", "accept: " + o);
                    }
                });
    }
}