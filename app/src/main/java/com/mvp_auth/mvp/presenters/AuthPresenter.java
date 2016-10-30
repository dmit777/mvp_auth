package com.mvp_auth.mvp.presenters;

import android.support.annotation.Nullable;

import com.mvp_auth.mvp.models.AuthModel;
import com.mvp_auth.mvp.views.IAuthView;
import com.mvp_auth.ui.views.AuthPanel;

public class AuthPresenter implements IAuthPresenter {

    private static final String TAG = "AuthPresenter";

    private static AuthPresenter mInstance = new AuthPresenter();
    private AuthModel mAuthModel;
    private IAuthView mAuthView;

    private AuthPresenter() {
        mAuthModel = new AuthModel();
    }

    public static AuthPresenter getInstance() {
        return mInstance;
    }

    @Override
    public void takeView(IAuthView authView) {
        mAuthView = authView;
    }

    @Override
    public void dropView() {
        mAuthView = null;
    }

    @Override
    public void initView() {
        if (getView() != null) {
            getView().isUserAuth(checkUserAuth());
        }
    }

    @Nullable
    @Override
    public IAuthView getView() {
        return mAuthView;
    }

    @Override
    public void login() {
        if (getView() != null && getView().getAuthPanel() != null) {
            if (getView().getAuthPanel().isIdle()) {
                getView().getAuthPanel().setCustomState(AuthPanel.LOGIN_STATE);
            } else {
                mAuthModel.loginUser(getView().getAuthPanel().getUserEmail(), getView().getAuthPanel().getUserPassword());
                getView().showMessage("trying of user authentification");
            }
        }
    }

    @Override
    public void clickOnFb() {
        if (getView() != null) {
            getView().showMessage("click on Fb");
        }
    }

    @Override
    public void clickOnVk() {
        if (getView() != null) {
            getView().showMessage("click on Vk");
        }
    }

    @Override
    public void clickOnTwitter() {
        if (getView() != null) {
            getView().showMessage("click on Twitter");
        }
    }

    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            getView().showMessage("Показать каталог");
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }

    @Override
    public boolean isValidateEmail() {
        if (getView() != null && getView().getAuthPanel() != null) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(getView().getAuthPanel().getUserEmail()).matches();
        }
        return false;
    }

    @Override
    public boolean isValidatePassword() {
        if (getView() != null && getView().getAuthPanel() != null) {
            return getView().getAuthPanel().getUserPassword().length() >= 6;
        }
        return false;
    }
}
