package politcc2017.tcc_app.Common;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;

/**
 * Created by Jonatas on 29/10/2016.
 * This class is used to get string resources in different languages easier
 */

public class ResourcesHelper {
    public static String[] getStringArray(Context c, int language, int referenceArrayID){
        return c.getResources().getStringArray(c.getResources().obtainTypedArray(referenceArrayID).getResourceId(language, 0));
    }

    public static ArrayList<String> getStringArrayAsArrayList(Context c, int language, int referenceArrayID){
        ArrayList<String> arrayList = new ArrayList<>();
        String[] itemTexts = getStringArray(c, language, referenceArrayID);
        for(int i = 0; i < itemTexts.length; i++) arrayList.add(itemTexts[i]);
        return arrayList;
    }

    public static ArrayList<Integer> getIntArrayAsArrayList(Context c, int referenceArrayID){
        ArrayList<Integer> arrayList = new ArrayList<>();
        TypedArray icons = c.getResources().obtainTypedArray(referenceArrayID);
        for(int i = 0; i < icons.length(); i++) arrayList.add(icons.getResourceId(i, -1));
        icons.recycle();
        return arrayList;
    }

    public static String getString(Context c, int language, int referenceArrayID){
        return c.getResources().getString(c.getResources().obtainTypedArray(referenceArrayID).getResourceId(language, 0));
    }
}