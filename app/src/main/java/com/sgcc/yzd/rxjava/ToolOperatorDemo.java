package com.sgcc.yzd.rxjava;

import android.util.Log;

import java.io.IOError;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class ToolOperatorDemo {

    public static void main(String[] args) {
        System.out.println("================================");
        ToolOperatorDemo demo = new ToolOperatorDemo();
        demo.test3();
        System.out.println("================================");
    }

    private void test4() {
        PublishSubject<Object> objectAsyncSubject = PublishSubject.create();
        objectAsyncSubject.onNext("A");
        objectAsyncSubject.onNext("B");
        objectAsyncSubject.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("accept:"+o);
            }
        });
        objectAsyncSubject.onNext("C");
        objectAsyncSubject.onNext("D");
        objectAsyncSubject.onComplete();
    }

    /** 手写 Observable  */
    private void test3() {

        com.sgcc.yzd.rxjava.Observable.create(new com.sgcc.yzd.rxjava.ObservableOnSubscribe() {
            @Override
            public void subscribe(Emitter emitter) {
                emitter.onNext("123");
                emitter.onNext("456");
                emitter.onComplete();
                emitter.onNext("789");
                emitter.onError(new Exception());
                emitter.onNext(789);
                emitter.onComplete();
            }
        }).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                if (o instanceof String) {
                    o += "1";
                } else if (o instanceof Integer) {
                    o = (Integer) o + 1;
                }
                return o;
            }
        })
                .subscribe(new com.sgcc.yzd.rxjava.Observer() {
            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe...");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext..." + o);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete...");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError...");
            }
        });
    }

    private void test2() {
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                System.out.println("onNext..." + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observable.range(1,10)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer<5;
                    }
                })
                .subscribe(observer);
    }

    private void test1() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                // 创建事件 发射事件

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
}
