package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dmytrod.cademo.screens.Petcube;

import java.util.List;

/**
 * Created by Dmytro Denysenko on 11/22/17.
 */

class Friend {
    private final User mUser;
    private final List<Petcube> mPetcube;

    public Friend(@NonNull User user, @Nullable List<Petcube> petcube) {
        mUser = user;
        mPetcube = petcube;
    }
}
