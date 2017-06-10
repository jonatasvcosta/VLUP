package politcc2017.tcc_app.Components.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Jonatas on 19/11/2016.
 */

public class SharedPreferencesHelper {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static final String USER_DATA_PREFERENCES = "USER_DATA";
    public static final String EMAIL_KEY = "EMAIL_KEY";
    public static final String NATIVE_LANGUAGE_KEY = "NATIVE_LANGUAGE_KEY";
    public static final String LEARNING_LANGUAGE_KEY = "LEARNING_LANGUAGE_KEY";
    public static final String PASSWORD_KEY = "PASSWORD_KEY";
    public static final String AUTOMATIC_AUTHENTICATION_KEY = "AUTOMATIC_AUTHENTICATION_KEY";
    public static final String LOCALE_KEY = "LOCALE_KEY";
    public static final String BOOKSHELF_BD_CHANGED_KEY = "BOOKSHELF_BD_CHANGED_KEY";
    public static final String BOOKSHELF_BD_LOCALE_KEY = "BOOKSHELF_BD_LOCALE_KEY";


    public static void Initialize(Context context){
        mSharedPreferences = context.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(AUTOMATIC_AUTHENTICATION_KEY, false);
        mEditor.commit();
    }

    public static void addString(String key, String value){
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getString(String key){
        return mSharedPreferences.getString(key, "");
    }

    public static String getString(String key, Context c){
        if(mSharedPreferences == null) mSharedPreferences = c.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(key, "");
    }

    public static void addInt(String key, int value){
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getInt(String key){
        return mSharedPreferences.getInt(key, -1);
    }

    public static int getInt(String key, Context c)
    {
        if(mSharedPreferences == null) mSharedPreferences = c.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(key, -1);
    }

    public static void addStringArray(String key, String[] stringArray){
        mEditor.putStringSet(key, new HashSet<String>(Arrays.asList(stringArray)));
        mEditor.commit();
    }

    public static ArrayList<String> getStringArrayList(String key){
        HashSet<String> hash = (HashSet<String>) mSharedPreferences.getStringSet(key, null);
        if(hash == null) return null;
        return (new ArrayList<String>(hash));
    }

    public static void addBoolean(String key, boolean value){
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static boolean getBoolean(String key, Context c){
        if(mSharedPreferences == null) mSharedPreferences = c.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(key, false);
    }
}
