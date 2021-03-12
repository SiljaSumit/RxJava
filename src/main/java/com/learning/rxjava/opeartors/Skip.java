package com.learning.rxjava.opeartors;

import java.util.concurrent.TimeUnit;

import com.learning.rxjava.util.Utils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Skip {
	public static void main(String[] args) throws InterruptedException {
		
		/**
		 * skipLast - skip the first 3seconds emitted values.
		 * emission is happening in every 1second.
		 * Here skipped values 0 and 1.
		 */
		Observable<Long> myObservable = Observable.interval(1, TimeUnit.SECONDS);
		Disposable disposable1 = myObservable.doOnDispose(()->Utils.print("disposed")).skip(3, TimeUnit.SECONDS)
		.subscribe(System.out::println);
		Thread.sleep(8000);
		disposable1.dispose();
		
		Observable<Integer> observable = Observable.range(1, 100);
		Disposable disposable2 = observable.doOnDispose(()->Utils.print("disposed")).skipLast(10, TimeUnit.MICROSECONDS)
		.subscribe(System.out::println);
		Thread.sleep(8000);
		disposable2.dispose();
	}
}
