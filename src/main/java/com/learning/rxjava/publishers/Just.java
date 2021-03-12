package com.learning.rxjava.publishers;

import com.learning.rxjava.util.Utils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Just {
	
	public static void main(String... args) throws InterruptedException {
		/**
		 * just is overloaded for up to 10 input parameters. 
		 */
		Observable.just("a", "b", "c").subscribe(Utils.getObserver(String.class));

		/**
		 * Schedulers (observeOn -> for downstream operations, subscribeOn -> instruct
		 * the source observable)
		 */
		Observable.just("first", "second", "thrird", "fourth")
				.observeOn(Schedulers.newThread()).map(String::length).subscribe(length -> System.out
						.println("item length " + length + "processed on " + Thread.currentThread().getName()));

		Thread.sleep(3000);
	}
}
