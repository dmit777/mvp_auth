package com.mvp_auth.mvp.views;

import android.support.annotation.Nullable;

import com.mvp_auth.mvp.presenters.IAuthPresenter;
import com.mvp_auth.ui.views.AuthPanel;

public interface IAuthView {
    void showMessage(String message);

    void showError(Throwable e);

    void showLoad();

    void hideLoad();

    IAuthPresenter getPresenter();

    void showLoginBtn();

    void hideLoginBtn();

    @Nullable
    AuthPanel getAuthPanel();

    void isUserAuth(boolean condition);
}
