package com.dmytrod.cademo.screens.login;

import android.support.annotation.NonNull;

import com.dmytrod.cademo.UseCase;

import rx.Observable;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

public class GetCurrentUserUseCase extends UseCase<Profile> {

    private final LoginRepository mRepository;

    public GetCurrentUserUseCase(@NonNull LoginRepository repository) {
        mRepository = repository;
    }

    @Override
    protected Observable<Profile> buildUseCaseObservable() {
        return mRepository.getCurrentUser();
    }
}
