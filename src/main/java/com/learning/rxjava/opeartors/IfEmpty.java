package com.learning.rxjava.opeartors;

import io.reactivex.Observable;

public class IfEmpty {
	public static void main(String[] args) {
		/**
		 * The defaultIfEmpty() operator can be used if no emissions occured before the
		 * onComplete() was called
		 */
		Observable<String> observable1 = Observable.just("R", "x", "J", "a", "v", "a");
		observable1.filter(s -> s.equals("b")).defaultIfEmpty("No element 'b' was found!")
				.subscribe(System.out::println);

		/**
		 * The switchIfEmpty() operator is somewhat similar to the defaultIfEmpty()
		 * operator, it allows you to use a different Observable in case the original
		 * source is empty
		 */
		Observable<String> observable2 = Observable.just("a", "b", "c");
		Observable<String> fallbackObservable = Observable.just("x", "y", "z");
		observable2.filter(s -> s.equals("x")).switchIfEmpty(fallbackObservable).subscribe(System.out::println);
	}
}
