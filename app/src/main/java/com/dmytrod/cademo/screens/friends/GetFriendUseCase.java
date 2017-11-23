package com.dmytrod.cademo.screens.friends;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.dmytrod.cademo.UseCase;
import com.dmytrod.cademo.screens.PetcubeRepository;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

class GetFriendUseCase extends UseCase<Friend> {
    private final UserRepository mUserRepository;
    private final PetcubeRepository mPetcubeRepository;
    private long mUserId;

    GetFriendUseCase(@NonNull UserRepository userRepository,
                     @NonNull PetcubeRepository petcubeRepository) {
        mUserRepository = userRepository;
        mPetcubeRepository = petcubeRepository;
    }

    void setUserId(@IntRange(from = 1) long userId) {
        mUserId = userId;
    }

    @Override
    protected Observable<Friend> buildUseCaseObservable() {
        try {
            if (mUserId < 1L) {
                throw new IllegalArgumentException("invalid user id: " + mUserId);
            }
            return Observable.zip(mUserRepository.getUser(mUserId)
                    .subscribeOn(Schedulers.io()),
                    mPetcubeRepository.getPetcubes(mUserId)
                            .switchIfEmpty(Observable.just(null))
                        .subscribeOn(Schedulers.io()),
                    Friend::new);
        } finally {
            mUserId = 0L;
        }
    }
}
