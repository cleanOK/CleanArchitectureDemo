package com.dmytrod.cademo;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Dmytro Denysenko on 19.11.17.
 */

public abstract class UseCase<T> {

    private final Scheduler mThreadExecutor;
    private final Scheduler mPostExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    public UseCase() {
        mThreadExecutor = Schedulers.io();
        mPostExecutionThread = AndroidSchedulers.mainThread();
    }

    protected UseCase(@NonNull Scheduler threadExecutor, @NonNull Scheduler postExecutionThread) {
        if (threadExecutor == null) {
            throw new IllegalArgumentException("threadExecutor shouldn't be null");
        }
        if (postExecutionThread == null) {
            throw new IllegalArgumentException("postExecutionThread shouldn't be null");
        }

        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    /**
     * Builds an {@link rx.Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<T> buildUseCaseObservable();

    /**
     * Executes the current use case.
     *
     * @param useCaseSubscriber The guy who will be listen to the observable build with {@link #buildUseCaseObservable()}.
     */
    public void execute(Subscriber<T> useCaseSubscriber) {
        subscription = setupObservable()
                .subscribe(useCaseSubscriber);
    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public Observable<T> setupObservable() {
        return buildUseCaseObservable()
                .subscribeOn(mThreadExecutor)
                .observeOn(mPostExecutionThread);
    }

}
