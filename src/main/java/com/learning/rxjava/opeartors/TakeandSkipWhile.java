package com.learning.rxjava.opeartors;

import com.learning.rxjava.util.Utils;

import io.reactivex.Observable;

public class TakeandSkipWhile {
	public static void main(String[] args) throws InterruptedException {

		/**
		 * takeWhile -> This operator will take emissions while a condition based on
		 * each emission is true.
		 */
		Observable<Integer> takeObservable = Observable.range(0, 10);
		takeObservable.doOnDispose(() -> Utils.print("takeObservable disposed")).takeWhile(x -> x < 5)
				.subscribe(System.out::println);

		/**
		 * skipWhile -> skips emissions while they satisfy a condition. Starting with
		 * the first item which violates the condition, the emissions start to get
		 * through this operator.
		 */
		Observable<Integer> skipObservable = Observable.range(0, 10);
		skipObservable.doOnComplete(() -> Utils.print("skipObservable completed")).skipWhile(x -> x < 5)
				.subscribe(System.out::println);
		
	}
}
