package politcc2017.tcc_app.Components;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Jonatas on 02/11/2016.
 */
public class FontHelper {
    public static final String TTF_FONT = "fonts/Nasalization.ttf";
    public static final String FONT_NAME = "Nasalization.ttf";
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}