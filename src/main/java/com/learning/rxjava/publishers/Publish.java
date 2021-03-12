package com.learning.rxjava.publishers;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

/**
 * Observable publish method
 * 
 *
 */
public class Publish {

	private static final Logger LOGGER = LoggerFactory.getLogger(Publish.class);

	public static void main(String... args) throws InterruptedException {

		Consumer<Disposable> disposable1 = disposable -> LOGGER.info("subscribed observer1 ");
		Consumer<Disposable> disposable2 = disposable -> LOGGER.info("subscribed observer2 ");
		Consumer<Disposable> disposable3 = disposable -> LOGGER.info("subscribed observer3 ");

		Action action1 = () -> LOGGER.info("Observer 1 unsubscribed");
		Action action2 = () -> LOGGER.info("Observer 2 unsubscribed");
		Action action3 = () -> LOGGER.info("Observer 3 unsubscribed");

		/**
		 * A ConnectableObservable is a single observable source for different
		 * observers.To convert an observable to a connectable one, you can use the
		 * publish operator. Publishing the observable will make it hot and will not
		 * replay the items for observers after activation.Calling subscribe on a
		 * ConnectableObserver will not trigger emission which is done by connect
		 * method. Each emission is being propagated to each Observer simultaneously
		 * (this is also called multicasting). This multicasting is rather helpful if
		 * replaying the emissions is expensive and you would like to emit them to all
		 * Observers simultaneously.
		 */
		Observable<Long> myObservable = Observable.interval(1, TimeUnit.SECONDS);
		ConnectableObservable<Long> connectableObservable = myObservable.publish();

		Disposable subscription1 = connectableObservable.doOnSubscribe(disposable1).doOnDispose(action1)
				.subscribe(item -> LOGGER.info("Observer 1->{} ", item));
		connectableObservable.connect();
		Thread.sleep(3000);
		Disposable subscription2 = connectableObservable.doOnSubscribe(disposable2).doOnDispose(action2)
				.subscribe(item -> LOGGER.info("Observer 2->{}", item));
		Thread.sleep(5000);
		subscription1.dispose();
		subscription2.dispose();

		/**
		 * refCount returns an observable that will be connected at subscription of an
		 * observer and will terminate if there are no more observers. When it has no
		 * Observers anymore, it will dispose itself and start over when a new Observer
		 * subscribes.
		 */
		Observable<Long> hotObservable = myObservable.publish().refCount();
		subscription1 = hotObservable.doOnSubscribe(disposable1).doOnDispose(action1)
				.subscribe(item -> LOGGER.info("Observer1->{} ", item));
		Thread.sleep(5000);
		subscription2 = hotObservable.doOnSubscribe(disposable2).doOnDispose(action2)
				.subscribe(item -> LOGGER.info("Observer2->{} ", item));
		Thread.sleep(5000);
		subscription1.dispose();
		subscription2.dispose();

		Disposable subscription3 = hotObservable.doOnSubscribe(disposable3).doOnDispose(action3)
				.subscribe(item -> LOGGER.info("Observer3->{} ", item));
		Thread.sleep(6000);
		subscription3.dispose();

		/**
		 * The share() operator is a shorthand for Observable.publish().refCount()
		 */
		hotObservable = myObservable.share();
		hotObservable.doOnSubscribe(disposable1).doOnDispose(action1).take(4)
				.subscribe(item -> LOGGER.info("Observer1->{} ", item));
		Thread.sleep(2000);
		hotObservable.doOnSubscribe(disposable2).doOnDispose(action2).take(2)
				.subscribe(item -> LOGGER.info("Observer2->{} ", item));
		Thread.sleep(3000);
	}
}
