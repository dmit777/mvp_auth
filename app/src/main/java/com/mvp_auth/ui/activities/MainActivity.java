package com.mvp_auth.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import com.mvp_auth.BuildConfig;
import com.mvp_auth.R;
import com.mvp_auth.mvp.presenters.AuthPresenter;
import com.mvp_auth.mvp.presenters.IAuthPresenter;
import com.mvp_auth.mvp.views.IAuthView;
import com.mvp_auth.ui.views.AuthPanel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IAuthView, View.OnClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.auth_wrapper)
    AuthPanel mAuthPanel;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.show_catalog_btn)
    Button mShowCatalogBtn;

    private AuthPresenter mPresenter = AuthPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter.takeView(this);
        mPresenter.initView();

        mLoginBtn.setOnClickListener(this);
        mShowCatalogBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mAuthPanel.isIdle()) {
            mAuthPanel.setCustomState(AuthPanel.IDLE_STATE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_catalog_btn:
                mPresenter.clickOnShowCatalog();
                break;
            case R.id.login_btn:
                mPresenter.login();
                break;
        }

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.main_unknown_error));
        }
    }

    @Override
    public void showLoad() {
        super.showProgress();
    }

    @Override
    public void hideLoad() {
        super.hideProgress();
    }

    @Override
    public IAuthPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoginBtn() {
        mLoginBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginBtn() {
        mLoginBtn.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public AuthPanel getAuthPanel() {
        return mAuthPanel;
    }

    @Override
    public void isUserAuth(boolean condition) {
        if (condition) {
            hideLoginBtn();
        } else {
            showLoginBtn();
        }
    }
}
