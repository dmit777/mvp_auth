package com.mvp_auth.ui.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mvp_auth.R;
import com.mvp_auth.mvp.presenters.AuthPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthPanel extends LinearLayout implements TextWatcher {

    private static final String TAG = "AuthPanel";

    private static final int ANIMATION_DURATION = 500;

    public static final int LOGIN_STATE = 0;
    public static final int IDLE_STATE = 1;
    private int mCustomState = 1;

    private AuthPresenter mPresenter = AuthPresenter.getInstance();

    private Context mContext;

    @BindView(R.id.auth_card)
    CardView mAuthCard;
    @BindView(R.id.login_email_wrap)
    TextInputLayout mEmailWrap;
    @BindView(R.id.login_password_wrap)
    TextInputLayout mPasswordWrap;
    @BindView(R.id.login_email_et)
    EditText mEmailEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.show_catalog_btn)
    Button mShowCatalogBtn;

    public AuthPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mCustomState == LOGIN_STATE) {
            if (!mPresenter.isValidateEmail() || !mPresenter.isValidatePassword()
                    || getUserEmail().isEmpty()
                    || getUserPassword().isEmpty()) {
                mLoginBtn.setEnabled(false);
            } else {
                mLoginBtn.setEnabled(true);
            }
        }

        if (!mPresenter.isValidateEmail() && !getUserEmail().isEmpty()) {
            mEmailWrap.setErrorEnabled(true);
            mEmailWrap.setError(getResources().getString(R.string.error_mail_hint));
        } else {
            mEmailWrap.setErrorEnabled(false);
        }

        if (!mPresenter.isValidatePassword() && !getUserPassword().isEmpty()) {
            mPasswordWrap.setErrorEnabled(true);
            mPasswordWrap.setError(getResources().getString(R.string.error_short_password_hint));
        } else {
            mPasswordWrap.setErrorEnabled(false);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContext = this.getContext();
        ButterKnife.bind(this);
        showViewFromState();

        validateData();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        ParselSavedState savedState = new ParselSavedState(superState);
        savedState.state = mCustomState;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        ParselSavedState savedState = (ParselSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCustomState(savedState.state);
    }

    public void setCustomState(int state) {
        mCustomState = state;
        showViewFromState();
    }

    private void showLoginState() {
        if (!mPresenter.isValidateEmail()
                || !mPresenter.isValidatePassword()
                || getUserEmail().isEmpty()
                || getUserPassword().isEmpty()) {
            mLoginBtn.setEnabled(false);
        }

        Animation animation = new ScaleAnimation(
                1.0f, 1.0f,
                0.3f, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(ANIMATION_DURATION);
        mAuthCard.setVisibility(VISIBLE);
        mAuthCard.clearAnimation();
        mAuthCard.startAnimation(animation);
        mShowCatalogBtn.setVisibility(GONE);
    }

    private void showIdleState() {
        mLoginBtn.setEnabled(true);

        Animation animation = new ScaleAnimation(
                1.0f, 1.0f,
                1.0f, 0.3f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(ANIMATION_DURATION);
        mAuthCard.clearAnimation();
        mAuthCard.setAnimation(animation);
        mAuthCard.setVisibility(GONE);
        mShowCatalogBtn.setVisibility(VISIBLE);

    }

    private void showViewFromState() {
        if (mCustomState == LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    private void validateData() {
        mEmailEt.addTextChangedListener(this);
        mPasswordEt.addTextChangedListener(this);
    }

    public String getUserEmail() {
        return mEmailEt.getText().toString();
    }

    public String getUserPassword() {
        return mPasswordEt.getText().toString();
    }

    public boolean isIdle() {
        return mCustomState == IDLE_STATE;
    }


    static class ParselSavedState extends BaseSavedState {

        private int state;

        public static final Parcelable.Creator<ParselSavedState> CREATOR = new Parcelable.Creator<ParselSavedState>() {

            @Override
            public ParselSavedState createFromParcel(Parcel source) {
                return new ParselSavedState(source);
            }

            @Override
            public ParselSavedState[] newArray(int size) {
                return new ParselSavedState[size];
            }
        };

        public ParselSavedState(Parcelable superState) {
            super(superState);
        }

        private ParselSavedState(Parcel in) {
            super(in);
            state = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }
    }

}
