package com.mvp_auth.mvp.presenters;

import android.support.annotation.Nullable;

import com.mvp_auth.mvp.views.IAuthView;

public interface IAuthPresenter {

    void takeView(IAuthView authView);

    void dropView();

    void initView();

    @Nullable
    IAuthView getView();

    void login();

    void clickOnFb();

    void clickOnVk();

    void clickOnTwitter();

    void clickOnShowCatalog();

    boolean checkUserAuth();

    boolean isValidateEmail();

    boolean isValidatePassword();


}
