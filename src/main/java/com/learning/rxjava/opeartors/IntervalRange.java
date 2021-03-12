package com.learning.rxjava.opeartors;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class IntervalRange {
	public static void main(String[] args) throws InterruptedException {
		/**
		 * intervalRange - emit values from range 1 to 5 in 2s interval
		 */
		Observable<Long> observable = Observable.intervalRange(1, 5, 0, 2, TimeUnit.SECONDS);
		observable.subscribe(System.out::println);
		Thread.sleep(8000);
	}
}
