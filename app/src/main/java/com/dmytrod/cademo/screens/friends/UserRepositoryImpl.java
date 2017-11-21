package com.dmytrod.cademo.screens.friends;

import rx.Observable;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

class UserRepositoryImpl implements UserRepository {

    @Override
    public Observable<User> getUser(long userId) {
        return null;
    }

    @Override
    public Observable<UserStatus> getUserStatus(long userId) {
        return null;
    }
}
