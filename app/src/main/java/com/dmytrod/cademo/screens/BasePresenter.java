package com.dmytrod.cademo.screens;

import android.support.annotation.NonNull;

/**
 * Created by Dmytro Denysenko on 20.11.17.
 */

public abstract class BasePresenter<T> {
    private T mView;

    public void setView(@NonNull T view) {
        mView = view;
    }

    @NonNull
    public T getView() {
        return mView;
    }

    public void destroy() {
        mView = null;
    }


}
