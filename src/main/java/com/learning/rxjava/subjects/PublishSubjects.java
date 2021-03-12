package com.learning.rxjava.subjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learning.rxjava.util.Utils;

import ch.qos.logback.classic.AsyncAppender;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class PublishSubjects {
	boolean stopEmitting;
	private static final Logger LOGGER = LoggerFactory.getLogger(PublishSubjects.class);

	public Observable<Integer> provideObservable() {
		return PublishSubject.create((ObservableOnSubscribe<Integer>) emitter -> {
			// this will only be called once, for the first subscriber
			emitter.setDisposable(new Disposable() {
				private boolean disposed;

				@Override
				public void dispose() {
					LOGGER.info("subscriber disposed of this; stopping scanning");
					disposed = true;
					stopEmitting = true;
				}

				@Override
				public boolean isDisposed() {
					return disposed;
				}
			});
			startEmitting(emitter);
		}).subscribeOn(Schedulers.computation()).share();
		
	}

	private void startEmitting(Emitter<Integer> emitter) {
		LOGGER.info("starting emitter");
		Action action = () -> {
			for (int i = 0; i < 50; i++) {

				if (stopEmitting) {
					LOGGER.info("stopping emitting");
					return;
				}

				LOGGER.info("Emitting " + i);
				emitter.onNext(i);
				Utils.sleep(100);
			}
			emitter.onComplete();
		};
		
		try {
			action.run();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) throws InterruptedException {
		Observable<Integer> integerObservable = new PublishSubjects().provideObservable();
		Thread.sleep(1000);
		integerObservable.observeOn(Schedulers.computation()).filter(num -> num>20).subscribe(
				integer -> LOGGER.info("consumed on observer1: " + integer),
				error -> LOGGER.info("error on stream: " + error), () -> LOGGER.info("completed stream: "));
		Thread.sleep(3000);
		integerObservable.observeOn(Schedulers.computation()).subscribe(
				integer -> LOGGER.info("consumed on observer2: " + integer),
				error -> LOGGER.info("error on stream: " + error), () -> LOGGER.info("completed stream: "));

		Thread.sleep(8000); 
	}
}
