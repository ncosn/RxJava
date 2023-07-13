package com.sgcc.yzd.rxjava;

public abstract class Observable<T> implements ObservableSource<T> {
    @Override
    public void subscribe(Observer<T> observer) {
        // 和谁建立订阅？
        // 怎么建立订阅？
        // 为了保证拓展性，交给具体开发人员实现，提供一个抽象方法
        subscribeActual(observer);
    }

    public abstract void subscribeActual(Observer<T> observer);

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<>(source);
    }

    public <R> ObservableMap<T,R> map(Function<T,R> function) {
        return new ObservableMap<>(this, function);
    }
}
