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
    public static final String NAME_KEY = "NAME_KEY";
    public static final String AGE_KEY = "AGE_KEY";
    public static final String EMAIL_KEY = "EMAIL_KEY";
    public static final String MOTHERLANGUAGE_KEY = "MOTHERLANGUAGE_KEY";
    public static final String COUNTRY_KEY = "COUNTRY_KEY";
    public static final String CITY_KEY = "CITY_KEY";
    public static final String NEIGHBORHOOD_KEY = "NEIGHBORHOOD_KEY";
    public static final String PASSWORD_KEY = "PASSWORD_KEY";
    public static final String GENDER_KEY = "GENDER_KEY";
    public static final String LANGUAGES_KEY = "LANGUAGES_KEY";
    public static final String LOCALE_KEY = "LOCALE_KEY";
    public static final String AUTOMATIC_AUTHENTICATION_KEY = "AUTOMATIC_AUTHENTICATION_KEY";


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

    public static boolean getBoolean(String key){
        return mSharedPreferences.getBoolean(AUTOMATIC_AUTHENTICATION_KEY, false);
    }
}
