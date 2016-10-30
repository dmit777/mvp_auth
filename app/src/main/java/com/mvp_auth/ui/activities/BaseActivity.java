package com.mvp_auth.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mvp_auth.R;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;

    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress_splash);
    }

    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void showError(String message, Exception error) {
        showToast(message);
        Log.d(TAG, String.valueOf(error));
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
