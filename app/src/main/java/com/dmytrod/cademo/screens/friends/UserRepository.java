package com.dmytrod.cademo.screens.friends;

import rx.Observable;

/**
 * Created by Dmytro Denysenko on 11/20/17.
 */

interface UserRepository {
    Observable<User> getUser(long userId);

    Observable<UserStatus> getUserStatus(long userId);
}
