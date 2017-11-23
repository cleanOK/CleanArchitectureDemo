package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

class FriendsContract {
    interface View {

        void displayFriend(@NonNull Friend user);

        void showError(String message);
    }
    interface Presenter {

        void loadFriend(long userId);
    }
}
