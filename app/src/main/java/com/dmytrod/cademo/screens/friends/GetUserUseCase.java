package com.dmytrod.cademo.screens.friends;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.dmytrod.cademo.UseCase;

import rx.Observable;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

public class GetUserUseCase extends UseCase<User> {
    private final UserRepository mRepository;
    private long mUserId;

    public GetUserUseCase(@NonNull UserRepository repository) {
        mRepository = repository;
    }

    public void setUserId(@IntRange(from = 1) long userId) {
        mUserId = userId;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        try {
            if (mUserId < 1L) {
                throw new IllegalArgumentException("invalid user id: " + mUserId);
            }
            return mRepository.getUser(mUserId);
        } finally {
            mUserId = 0L;
        }
    }
}
