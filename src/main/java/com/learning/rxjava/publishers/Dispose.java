package com.learning.rxjava.publishers;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class Dispose {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		/**
		 * dispose or disconnect subscription so the emission will stop
		 */
        Observable<Long> infiniteObservable = Observable.interval(1, TimeUnit.SECONDS); // cold observable
        Disposable disposable = infiniteObservable.subscribe(s -> System.out.println(s + " second(s) passed!"));
        Thread.sleep(5500);
        disposable.dispose();
        System.out.println("No more emissions after this!"); 
        
        
        Observable<Long> observable = Observable.rangeLong(0, 10);
        Observer<Long> myCustomObserver = new Observer<Long>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable disposable) {
                this.disposable = disposable;
            }
            @Override
            public void onNext(Long value) {
                System.out.println("Got item: " + value);
            }
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                disposable.dispose();
            }
            @Override
            public void onComplete() {
                System.out.println("Calling dispose() in the Observer!");
                disposable.dispose();
            }
        };
        observable.subscribeWith(myCustomObserver);
        
       
        /**
         * using CompositeDisposable to which add the subscribers to be disposed
         */
        CompositeDisposable disposables = new CompositeDisposable();
        Disposable d1 = infiniteObservable.subscribe(s->System.out.println(s));
        disposables.add(d1);
        Thread.sleep(5500);
        disposables.dispose();
        System.out.println("Disposed "+disposables.isDisposed());
	}

}
