package com.mvp_auth.data.managers;

import android.content.SharedPreferences;

import com.mvp_auth.utils.ConstantManager;
import com.mvp_auth.utils.MVPApplication;

public class PreferencesManager {

    private SharedPreferences mSharedPreferences;

    public PreferencesManager() {
        this.mSharedPreferences = MVPApplication.getSharedPreferences();
    }

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(ConstantManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, "null");
    }

}
