package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dmytrod.cademo.UseCase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

class GetFriendsStatusesUseCase extends UseCase<UserStatus> {
    private static final String TAG = "GetFriendsStatuses";
    private static final long POLLING_DELAY = 5;
    private static final long POLLING_TIMEOUT = 300;

    private final UserRepository mUserRepository;
    private final PublishSubject<UserStatus> mStatusPublishSubject = PublishSubject.create();
    private final Map<Long, Subscription> mSubscribtionsIdMap = new HashMap<>();

    GetFriendsStatusesUseCase(@NonNull UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    protected Observable<UserStatus> buildUseCaseObservable() {
        return mStatusPublishSubject.asObservable();
    }

    void addUserId(final long userId) {
            if (mSubscribtionsIdMap.get(userId) == null) {
                final Subscription subscription = Observable.interval(POLLING_DELAY, TimeUnit.SECONDS)
                        .flatMap(aLong -> mUserRepository.getUserStatus(userId))
                        .filter(userStatus -> mSubscribtionsIdMap.containsKey(userStatus.getId()))
                        .retry()
                        .distinct()
                        .timeout(POLLING_TIMEOUT, TimeUnit.SECONDS)
                        .subscribe(mStatusPublishSubject::onNext,
                                throwable -> Log.e(TAG, "user status load failed, id = " + userId, throwable));
                mSubscribtionsIdMap.put(userId, subscription);
            }
    }

    void removeUserId(long userId) {
        Subscription subscription = mSubscribtionsIdMap.remove(userId);
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void unsubscribe() {
        for (Subscription subscription : mSubscribtionsIdMap.values()) {
            subscription.unsubscribe();
        }
        mSubscribtionsIdMap.clear();
        super.unsubscribe();
    }
}
