package politcc2017.tcc_app.Components.Helpers;

import android.os.Build;

/**
 * Created by Jonatas on 26/12/2016.
 */

public final class AndroidHelper {
    public static boolean isLollipop() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
