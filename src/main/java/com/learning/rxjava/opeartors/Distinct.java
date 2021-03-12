package com.learning.rxjava.opeartors;

import com.learning.rxjava.util.Utils;

import io.reactivex.Observable;

public class Distinct {
	public static void main(String[] args) {

		Observable<String> distinctObservable1 = Observable.just("ab", "ab", "ba");
		distinctObservable1.doOnComplete(() -> Utils.print("distinctObservable1 completed")).distinct()
				.subscribe(System.out::println);

		Observable<String> distinctObservable2 = Observable.just("a", "aa", "bb", "bbb", "c", "cccc");
		distinctObservable2.doOnComplete(() -> Utils.print("distinctObservable2 completed")).distinct(String::length)
				.subscribe(System.out::println);

		/**
		 * The distinctUntilChanged() operator ignores duplicate consecutive emissions.
		 * This means that it will ignore repetitions of an item until they change: if
		 * the same item is being emitted multiple times repeatedly, the duplicates will
		 * be ignored, until a new item is emitted. Then the duplicates of this new item
		 * will be ignored until the emitted item changes again.
		 */
		Observable<String> distinctObservable3 = Observable.just("ab", "ab", "ba", "ab", "ab", "ba");
		distinctObservable3.doOnComplete(() -> Utils.print("distinctObservable3 completed")).distinctUntilChanged()
				.subscribe(System.out::println);

		Observable<String> distinctObservable4 = Observable.just("a", "aa", "aa", "b", "bb", "aa", "aa", "aa");
		distinctObservable4.doOnComplete(() -> Utils.print("distinctObservable4 completed"))
				.distinctUntilChanged(String::length).subscribe(System.out::println);

	}
}
