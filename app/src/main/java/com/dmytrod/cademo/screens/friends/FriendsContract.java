package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

public class FriendsContract {
    interface View {

        void displayFriend(@NonNull User user);

        void showError(String message);
    }
    interface Presenter {

        void showFriend(long userId);
    }
}
