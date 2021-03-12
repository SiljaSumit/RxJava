package com.learning.rxjava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Utils {
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	public static <T> void print(T data) {
		System.out.println(data);
	}
	
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static <T extends Object> Observer<T> getObserver(Class<T> type) {
		return new Observer<T>() {
			public void onSubscribe(Disposable d) {
				// TODO Auto-generated method stub
				LOGGER.info("subscribed");
			}

			public void onNext(T t) {
				// TODO Auto-generated method stub
				LOGGER.info("next -> {}", t);
			}

			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				LOGGER.info("error");
			}

			public void onComplete() {
				// TODO Auto-generated method stub
				LOGGER.info("completed");
			}
		};
	}
}
