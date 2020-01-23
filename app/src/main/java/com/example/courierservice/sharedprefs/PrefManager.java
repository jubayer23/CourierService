package com.example.courierservice.sharedprefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.example.courierservice.BuildConfig;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.model.PendingOrder;
import com.example.courierservice.model.User;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jubayer on 6/6/2017.
 */


public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static Gson GSON = new Gson();
    // Sharedpref file name
    private static final String PREF_NAME = BuildConfig.APPLICATION_ID;

    private static final String KEY_USER = "user";

    private static final String KEY_RECEIVED_CARD_OBJ = "received_card_obj";
    private static final String KEY_LOG = "key_log";
    private static final String KEY_EMAIL_CACHE = "key_email_cache";
    private static final String KEY_ONGOING_ORDER = "key_ongoing_order";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public void setEmailCache(String obj) {
        editor = pref.edit();

        editor.putString(KEY_EMAIL_CACHE, obj);

        // commit changes
        editor.commit();
    }
    public String getEmailCache() {
        return pref.getString(KEY_EMAIL_CACHE,"");
    }



    public void setUser(User obj) {
        editor = pref.edit();

        String json = MydApplication.gson.toJson(obj);

        editor.putString(KEY_USER, json);

        // commit changes
        editor.commit();
    }

    public User getUserInfo() {
        String json = pref.getString(KEY_USER, "");

        if (!json.isEmpty()) {
            return MydApplication.gson.fromJson(json, User.class);
        } else
            return null;
    }


    public void setOnGoingOrder(PendingOrder obj) {
        editor = pref.edit();

        String json = MydApplication.gson.toJson(obj);

        editor.putString(KEY_ONGOING_ORDER, json);

        // commit changes
        editor.commit();
    }

    public PendingOrder getOnGoingOrder() {
        String json = pref.getString(KEY_ONGOING_ORDER, "");

        if (!json.isEmpty()) {
            return MydApplication.gson.fromJson(json, PendingOrder.class);
        } else
            return null;
    }


}