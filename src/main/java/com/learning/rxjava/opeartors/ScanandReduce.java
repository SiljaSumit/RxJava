package com.learning.rxjava.opeartors;

import java.util.concurrent.TimeUnit;

import com.learning.rxjava.util.Utils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ScanandReduce {
	public static void main(String[] args) throws InterruptedException {

		/**
		 * scan -> apply a function to each item emitted by an Observable, sequentially,
		 * and emit each successive value.
		 */
		Observable<String> observable1 = Observable.just("H", "e", "l", "l", "o");
		observable1.doOnComplete(() -> Utils.print("observable1 completed")).scan((x, y) -> x + y)
				.subscribe(System.out::println);

		Observable<String> observable2 = Observable.just("R", "x", "J", "a", "v", "a");
		observable2.doOnComplete(() -> Utils.print("observable2 completed")).scan(0, (total, next) -> total + 1).skip(1)
				.subscribe(s -> System.out.println("Total number of items so far: " + s));

		Observable<Long> observable3 = Observable.interval(1, TimeUnit.SECONDS);
		Disposable d1 = observable3.scan(0, (total, next) -> total + 1)
				.doOnDispose(() -> Utils.print("observable3 completed")).skip(1)
				.subscribe(s -> System.out.println("Total number of items so far: " + s));
		Thread.sleep(4500);
		d1.dispose();

		/**
		 * reduce -> apply a function to each item emitted by an Observable,
		 * sequentially, and emit the final value
		 */
		Observable<Integer> observable4 = Observable.just(1, 2, 3, 4, 5);
		observable4.reduce((x, y) -> x + y).subscribe(System.out::println);
	}
}
