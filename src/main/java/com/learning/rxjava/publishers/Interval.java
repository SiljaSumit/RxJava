package com.learning.rxjava.publishers;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Interval {
	public static void main(String[] args) throws InterruptedException {

		/**
		 * Observable.interval() needs a separate thread (it uses the
		 * Schedulers.computation()), because it emits infinitely at the specified
		 * interval. This means that our main method will start the Observable, but will
		 * not wait for it to finish, hence we added the Thread.sleep() call.
		 * 
		 * Observable.interval() is a cold observable 
		 */
		Disposable observer = Observable.interval(1, TimeUnit.SECONDS)
				.doOnSubscribe(disposable -> System.out
						.println("Subscribed to observable running in the thread " + Thread.currentThread().getName()))
				.doOnDispose(()->System.out.println("Disposed..."))
				.subscribe(s -> System.out
						.println(s + " second(s) passed! from thread " + Thread.currentThread().getName()));
		Thread.sleep(5500);
		observer.dispose();
	}
}
