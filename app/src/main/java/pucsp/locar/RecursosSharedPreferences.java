package pucsp.locar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Flavia on 21/05/2016.
 */
public class RecursosSharedPreferences {
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_ID= "userid";
    static final String PREF_TYPE_LOGIN= "typelogin";

    static android.content.SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName, String userID, String typeLogin)
    {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_USER_ID, userID);
        editor.putString(PREF_TYPE_LOGIN, typeLogin);
        editor.apply();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserID(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static String getTypeLogin(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_TYPE_LOGIN, "");
    }

    public static void clearUserName(Context ctx)
    {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}
