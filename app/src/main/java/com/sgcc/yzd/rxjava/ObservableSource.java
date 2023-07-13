package com.sgcc.yzd.rxjava;

public interface ObservableSource<T> {
    public void subscribe(Observer<T> observer);
}
