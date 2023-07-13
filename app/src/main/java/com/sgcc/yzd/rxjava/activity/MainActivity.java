package com.sgcc.yzd.rxjava.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sgcc.yzd.rxjava.R;
import com.sgcc.yzd.rxjava.entity.ResponseData;
import com.sgcc.yzd.rxjava.retrofit.ApiMethods;
import com.sgcc.yzd.rxjava.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        test1();
//        startActivity(new Intent(this, SecondActivity.class));

        retrofit();
    }


    void initData() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("2");
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    private void test1() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                // 创建事件 发射事件

                System.out.println("subscribe..."+ Thread.currentThread());
                // 模拟网络请求
                Thread.sleep(2000);
                emitter.onNext("aaa");
                emitter.onNext("bbb");
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 建立订阅后立即执行的方法
                        // 在此处进行订阅时的操作，例如显示进度条
                        System.out.println("onSubscribe..."+Thread.currentThread());
                    }
                    @Override
                    public void onNext(Object o) {
                        System.out.println("onNext..." + o + "..." + Thread.currentThread());
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete..." + Thread.currentThread());
                    }

                });
    }


    /**
     * retrofit 结合 rxjava 访问百度云获取鉴权参数 access_token
     */
    private void retrofit() {
        Map<String,String> map = new HashMap<>();
        map.put("grantType", StringUtil.GRANT_TYPE);
        map.put("apiKey",StringUtil.API_KEY);
        map.put("secretKey",StringUtil.SECRET_KEY);
        Observer<ResponseData> observer = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe...");
            }

            @Override
            public void onNext(ResponseData value) {
                System.out.println("onNext:access_token:"+value.getAccess_token());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError...");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete...");
            }
        };
        ApiMethods.newInstance().postAccessToken(observer,map);
    }
}