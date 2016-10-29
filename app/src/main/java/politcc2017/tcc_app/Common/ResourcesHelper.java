package politcc2017.tcc_app.Common;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;

import politcc2017.tcc_app.R;

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

    public static String getString(Context c, int language, int referenceArrayID){
        return c.getResources().getString(c.getResources().obtainTypedArray(referenceArrayID).getResourceId(language, 0));
    }
}