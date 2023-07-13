package com.sgcc.yzd.rxjava.retrofit;

import com.sgcc.yzd.rxjava.rxjava.SchedulerTransformer;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class ApiMethods {

    public static ApiMethods newInstance() {
        ApiMethods apiMethods = new ApiMethods();
        return apiMethods;
    }

    /**
     * @param observable 被观察者，网络请求方法
     * @param observer 观察者，调用 ApiMethods 时传入
     */
    private static void apiSubscribe(Observable observable, Observer observer) {
        observable.compose(new SchedulerTransformer())
                .subscribe(observer);
    }

//    /**
//     * @param observable 被观察者，网络请求方法
//     * @param observer 观察者，调用 ApiMethods 时传入
//     */
//    private static void apiSubscribe(Observable observable, Observer observer) {
//        Observable.just(observable)
//                .compose(new SchedulerTransformer())
//                .subscribe(observer);
//    }

    public void postAccessToken(Observer observer, Map<String,String> map) {
        apiSubscribe(Api.getApiService().getAccessToken(map.get("grantType"),map.get("apiKey"),map.get("secretKey")), observer);
    }
}
