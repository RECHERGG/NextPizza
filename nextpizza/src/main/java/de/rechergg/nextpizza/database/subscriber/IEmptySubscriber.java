package de.rechergg.nextpizza.database.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public interface IEmptySubscriber<T> extends Subscriber<T> {

    @Override
    default void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    default void onNext(T t) {

    }

    @Override
    default void onError(Throwable throwable) {

    }

    @Override
    default void onComplete() {

    }
}
