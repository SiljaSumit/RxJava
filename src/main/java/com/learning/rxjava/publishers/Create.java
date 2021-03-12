package com.learning.rxjava.publishers;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * observable.subscribe(nextConsumer, errorConsumer, completedAction,
 * onSubscribeConsumer);
 *
 */
public class Create {

	private static final Logger LOGGER = LoggerFactory.getLogger(Create.class);
	private static Integer[] numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public static void main(String[] args) throws InterruptedException {
		/**
		 * creating observable using FI ObservableOnSubscribe. Abstract method subscribe
		 * may override methods like onNext, onComplete, onError
		 */
		Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				// TODO Auto-generated method stub
				emitter.onNext("Hi");
			}
		});
		observable.subscribe(s -> System.out.println(s.toLowerCase()));

		/**
		 * create using lamda
		 */
		Observable.create((ObservableEmitter<String> subscriber) -> {
			subscriber.onNext("Hello World!");
			subscriber.onComplete();
		}).subscribe(System.out::println);

		/**
		 * emitted item is transformed using map
		 */
		Observable.create(observer -> {
			observer.onNext("learning rxjava");
		}).map(s -> ((String) s).toUpperCase()).subscribe(System.out::println, System.out::println);

		/**
		 * Observable emits buffers of items
		 */
		Observable.create(observer -> observer.onNext(totalOf(numbers)))
				.subscribe(n -> LOGGER.info(n + " in thread " + Thread.currentThread().getName()), System.out::println);

		/**
		 * grouping the emitted numbers into two groups of observable
		 * 
		 * System.out.println("------GroubBy------------");
		 * Observable.fromArray(numbers).groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
		 * .subscribe((group) -> group.subscribe((number) -> {
		 * System.out.println(group.getKey() + " : " + number); }));
		*/
		ExecutorService executor = Executors.newFixedThreadPool(1);

		Observable.fromIterable(Arrays.asList(1, 2, 3, 4)).doOnSubscribe(d -> System.out.println("We just subscribe!"))
				.flatMap(number -> Observable.just(number) 
						.doOnNext(n -> System.out.println(
								String.format("Executed in thread:%s number %s", Thread.currentThread().getName(), n))))
				.observeOn(Schedulers.from(executor))
				.doAfterTerminate(()->{System.out.println("executor shutdown");executor.shutdown();})
				.subscribe(num -> System.out.println("consumed " +num + " on " + Thread.currentThread().getName()));

		 
	}

	private static Integer totalOf(Integer[] numbers) {
		return Observable.fromArray(numbers).buffer(4).map(l -> l.stream().reduce((x, y) -> x + y))
				.doOnNext(n -> LOGGER.info("Thread:" + Thread.currentThread().getName())).blockingLast().get();

	}
}
