package com.learning.rxjava.opeartors;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;

/**
 * zip and zipWith combines the elements of the supplied streams, using a
 * pairwise “zip” transformation mapping that you can supply in the form of a
 * Lambda. When either of those streams completes, the zipped stream completes,
 * so any remaining events from the other stream would be lost
 * 
 *
 */
public class RangewithZip {

	private static final Logger LOGGER = LoggerFactory.getLogger(RangewithZip.class);

	public static void main(String[] args) {
		Observable.range(1, 5).subscribe(System.out::println);

		List<String> names = Arrays.asList("Sam", "Tom", "Paul", "Bob");

		/**
		 * by using zip
		 */
		Observable.zip(Observable.fromIterable(names), Observable.range(1, Integer.MAX_VALUE),
				(name, count) -> String.format("%d. %s", count, name)).subscribe(System.out::println);

		/**
		 * by using zipWith
		 */
		Observable.fromIterable(names)
				.zipWith(Observable.range(1, Integer.MAX_VALUE), (name, count) -> String.format("%d. %s", count, name))
				.subscribe(System.out::println);

		/**
		 *source stream transformed before doing zip
		 */
		Observable.fromIterable(names).map(name -> name.toLowerCase())
				.flatMap(word -> Observable.fromArray(word.split(""))).distinct().sorted()
				.zipWith(Observable.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%d. %s", count, string))
				.subscribe(System.out::println);

	}
}
