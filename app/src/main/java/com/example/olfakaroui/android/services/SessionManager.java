package com.example.olfakaroui.android.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.User;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(_context.getResources().getString(R.string.prefs), PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, User user, boolean createdAccount) {

        editor.putString(_context.getResources().getString(R.string.user_name), user.getFirstName());
        editor.putInt(_context.getResources().getString(R.string.user_id), user.getId());
        editor.putString(_context.getResources().getString(R.string.user_lastname), user.getLastName());
        editor.putString(_context.getResources().getString(R.string.user_photo), user.getPhoto());
        if(!createdAccount)
        {
            editor.putString(_context.getResources().getString(R.string.user_role), user.getrole());
        }
        editor.putBoolean(_context.getResources().getString(R.string.isconnected), isLoggedIn);
        editor.apply();
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean getLogin(User user) {
        SharedPreferences prefs = _context.getSharedPreferences(_context.getResources().getString(R.string.prefs), PRIVATE_MODE);
        if(prefs.getBoolean(_context.getResources().getString(R.string.isconnected), false))
        {
            user.setFirstName(prefs.getString(_context.getResources().getString(R.string.user_name), null));
            user.setLastName(prefs.getString(_context.getResources().getString(R.string.user_lastname), null));
            user.setId(prefs.getInt(_context.getResources().getString(R.string.user_id), 0));
            user.setRole(prefs.getString(_context.getResources().getString(R.string.user_role), null));
            user.setPhoto(prefs.getString(_context.getResources().getString(R.string.user_photo), null));
            return true;
        }
        return false;

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(_context.getResources().getString(R.string.isconnected), false);
    }
}
