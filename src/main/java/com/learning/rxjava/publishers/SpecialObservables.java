package com.learning.rxjava.publishers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class SpecialObservables {
	public static void main(String[] args) throws InterruptedException {

		/**
		 * Single is an Observable that will emit only one item. onNext and onComplete
		 * merged to single event onSuccess
		 */
		
        Single<String> single = Single.just("Single");
        single.subscribe(System.out::println);
		

		/**
		 * Maybe
		 */
		Maybe<String> empty = Maybe.empty();
		empty.subscribe(s -> System.out.println("Received: " + s), 
				Throwable::printStackTrace, 
				() -> System.out.println("finished!") 
		);
		
		/**
		 * Completable
		 */
		Single<List<String>> single1 = Single.fromCallable(new Callable<List<String>>() {

			@Override
			public List<String> call() throws Exception {
				return Arrays.asList("First", "Second");
			}
		});
		
		Completable.fromSingle(single1);
		single1.subscribe(list -> display(list));
		
		Map<Integer,String> namesMap = new HashMap<>();
		Completable.create(new CompletableOnSubscribe() {

			@Override
			public void subscribe(CompletableEmitter emitter) throws Exception {
				//Thread.sleep(1000);
				namesMap.put(1, "Bob");
				namesMap.put(2, "Smith");
				System.out.println("names Added");
				System.out.println("completed");
				emitter.onComplete();
			}
		}).andThen(Observable.fromIterable(namesMap.entrySet())).subscribe(new Consumer<Map.Entry<Integer, String>>() {

			@Override
			public void accept(Map.Entry<Integer,String> map) throws Exception {
				System.out.println("emitting->"+map.getValue());
			}
		});
	}

	private static void display(List<String> list) {
		list.forEach(System.out::println);
	}
}
