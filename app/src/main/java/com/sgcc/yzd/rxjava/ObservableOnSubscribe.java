package com.sgcc.yzd.rxjava;

public interface ObservableOnSubscribe<T> {

    void subscribe(Emitter<T> emitter);

}
