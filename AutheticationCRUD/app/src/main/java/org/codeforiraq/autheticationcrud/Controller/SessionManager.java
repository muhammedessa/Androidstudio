package org.codeforiraq.autheticationcrud.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.codeforiraq.autheticationcrud.Model.User;
import org.codeforiraq.autheticationcrud.UI.LoginActivity;

public class SessionManager {

    private static final String SHARED_PREF_NAME = "userToken";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "user_id";

    private  static  SessionManager mInstance;
    private static Context mCtx;

    public SessionManager(Context context) {
        mCtx = context;
    }

    public static synchronized SessionManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SessionManager(context);
        }
        return  mInstance;
    }


    public void userLogin(User user){
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME,user.getName());
        editor.putString(KEY_EMAIL,user.getEmail());
        editor.putString(KEY_TOKEN,user.getToken());
        editor.apply();
    }


    public  boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN,null) != null;
    }


    public User getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_TOKEN,null)
        );
    }


    public void userLogout(){
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }



    public User getUser(User user){
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return new User(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_NAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_TOKEN,null)
        );

    }


}
