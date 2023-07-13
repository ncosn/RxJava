package com.sgcc.yzd.rxjava;

public class ObservableMap<T,U> extends AbstractObservableWithUpStream<T,U> {

    Function<T,U> function;

    public ObservableMap(ObservableSource<T> source, Function<T, U> function) {
        super(source);
        this.function = function;
    }

    @Override
    public void subscribeActual(Observer<U> observer) {
        System.out.println("ObservableMap:subscribeActual");
        source.subscribe(new MapObserver<>(observer,function));
        System.out.println("source.subscribe()");
    }


    static class MapObserver<T,U> implements Observer<T> {

        final Observer<U> downStream;

        final Function<T,U> mapper;

        MapObserver(Observer<U> downStream, Function<T, U> mapper) {
            this.downStream = downStream;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe() {
            downStream.onSubscribe();

        }

        @Override
        public void onNext(T t) {
            // map操作符核心
            U u = mapper.apply(t);
            downStream.onNext(u);
        }

        @Override
        public void onComplete() {
            downStream.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {

            downStream.onError(throwable);
        }
    }
}
