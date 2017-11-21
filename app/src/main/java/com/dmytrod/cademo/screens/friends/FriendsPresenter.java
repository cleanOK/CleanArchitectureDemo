package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dmytrod.cademo.screens.BasePresenter;
import com.dmytrod.cademo.screens.ErrorHandler;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */
class FriendsPresenter extends BasePresenter<FriendsContract.View> implements FriendsContract.Presenter {

    private final GetUserUseCase mGetUserUseCase;
    private final GetFriendsStatusesUseCase mGetFriendsStatusesUseCase;
    private final ErrorHandler mErrorHandler;
    private final Set<Subscriber<User>> mSubscribers = new HashSet<>();

    @Inject
    public FriendsPresenter(@NonNull GetUserUseCase getUserUseCase,
                            @NonNull GetFriendsStatusesUseCase getFriendsStatusesUseCase,
                            @NonNull ErrorHandler errorHandler) {
        mGetUserUseCase = getUserUseCase;
        mGetFriendsStatusesUseCase = getFriendsStatusesUseCase;
        mErrorHandler = errorHandler;
    }

    @Override
    public void showFriend(long userId) {
        Subscriber<User> friendSubscriber = new UserSubscriber(userId);
        if (mSubscribers.add(friendSubscriber)) {
            mGetUserUseCase.setUserId(userId);
            mGetUserUseCase.execute(friendSubscriber);
        }
    }

    @Override
    public void destroy() {
        for (Subscriber<User> subscriber : mSubscribers) {
            subscriber.unsubscribe();
        }
        super.destroy();
    }

    private class UserSubscriber extends Subscriber<User> {
        private final long mUserId;

        private UserSubscriber(long userId) {
            mUserId = userId;
        }

        @Override
        public void onCompleted() {
            /*NOP*/
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "User loading failed, id =" + mUserId, e);
            e = mErrorHandler.handleException(e);
            getView().showError(e.getMessage());
        }

        @Override
        public void onNext(User user) {
            getView().displayFriend(user);
        }
    }
}
