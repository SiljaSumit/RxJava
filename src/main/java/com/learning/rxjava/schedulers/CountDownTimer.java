package com.learning.rxjava.schedulers;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learning.rxjava.util.Utils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CountDownTimer {

	private TimeUnit timeUnit;
	private Long startValue;
	private Disposable disposable;

	private static final Logger LOGGER = LoggerFactory.getLogger(CountDownTimer.class);

	public CountDownTimer(Long startValue, TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		this.startValue = startValue;
	}

	public void onTick(long tickValue) {
		LOGGER.info("value->{}", tickValue);
	}

	public void onFinish() {
		LOGGER.info("completed");
	}

	public void start() {
		Observable
				.zip(Observable.range(0, startValue.intValue()), Observable.interval(1, timeUnit), (integer, aLong) -> {
					Long l = startValue - integer;
					//LOGGER.info("Obsverable thread on " + Thread.currentThread().getName());
					return l;
				})
				.observeOn(Schedulers.computation())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {
						disposable = d;
					}

					@Override
					public void onNext(Long aLong) {
						onTick(aLong);
					}

					@Override
					public void onError(Throwable e) {
						e.printStackTrace();
					}

					@Override
					public void onComplete() {
						onFinish();
					}
				});
        
	}

	public void cancel() {
		if (disposable != null)
			disposable.dispose();
	}

	public static void main(String[] args) throws InterruptedException {
		new CountDownTimer(5L, TimeUnit.SECONDS).start();
		Utils.sleep(6000);
	}
}
