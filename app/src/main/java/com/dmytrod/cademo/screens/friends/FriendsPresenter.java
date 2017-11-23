package com.dmytrod.cademo.screens.friends;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dmytrod.cademo.screens.BasePresenter;
import com.dmytrod.cademo.screens.ErrorHandler;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

import static android.content.ContentValues.TAG;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */
class FriendsPresenter extends BasePresenter<FriendsContract.View> implements FriendsContract.Presenter {

    private final GetFriendsStatusesUseCase mGetFriendsStatusesUseCase;
    private final GetFriendUseCase mGetFriendUseCase;
    private final ErrorHandler mErrorHandler;
    private final Map<Long, Subscriber<Friend>> mIdSubscribersMap = new HashMap<>();

    @Inject
    public FriendsPresenter(@NonNull GetFriendUseCase getFriendUseCase,
                            @NonNull GetFriendsStatusesUseCase getFriendsStatusesUseCase,
                            @NonNull ErrorHandler errorHandler) {
        mGetFriendUseCase = getFriendUseCase;
        mGetFriendsStatusesUseCase = getFriendsStatusesUseCase;
        mErrorHandler = errorHandler;
    }

    @Override
    public void loadFriend(long userId) {
        Subscriber<Friend> friendSubscriber = mIdSubscribersMap.get(userId);
        if (friendSubscriber == null) {
            friendSubscriber = new UserSubscriber(userId);
            mIdSubscribersMap.put(userId, friendSubscriber);
        }
        mGetFriendUseCase.setUserId(userId);
        mGetFriendUseCase.execute(friendSubscriber);
    }

    @Override
    public void destroy() {
        for (Subscriber<Friend> subscriber : mIdSubscribersMap.values()) {
            subscriber.unsubscribe();
        }
        mIdSubscribersMap.clear();
        mGetFriendUseCase.unsubscribe();
        mGetFriendsStatusesUseCase.unsubscribe();
        super.destroy();
    }

    private class UserSubscriber extends Subscriber<Friend> {
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
            Log.e(TAG, "Friend loading failed, id =" + mUserId, e);
            e = mErrorHandler.handleException(e);
            getView().showError(e.getMessage());
        }

        @Override
        public void onNext(Friend user) {
            getView().displayFriend(user);
        }
    }
}
