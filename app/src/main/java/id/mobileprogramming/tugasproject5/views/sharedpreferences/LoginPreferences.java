package id.mobileprogramming.tugasproject5.views.sharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LoginPreferences {
    private SharedPreferences.Editor editor;
    private static LoginPreferences instance;

    static final String STATUS_KEY = "status";
    static final String USERNAME_KEY = "username";
    static final String PASSWORD_KEY = "password";

    public static LoginPreferences getInstance(){
        if (instance == null){
            instance = new LoginPreferences();
        }

        return instance;
    }

    public SharedPreferences getShared(Context context){
        return context.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    public void setLocation(String location){
        editor.putString("Location", location);
        editor.apply();
    }

    public void prevLocation(String location){
        editor.putString("prevLocation", location);
        editor.apply();
    }

    public void init(Context context){
        editor = getShared(context).edit();
    }

    public void setData(boolean status, String username, String password){
        editor.putBoolean(STATUS_KEY, status);
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    public void logOut(){
        editor.clear();
        editor.apply();
    }
}
