package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import politcc2017.tcc_app.Components.Helpers.FontHelper;

/**
 * Created by Jonatas on 02/11/2016.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getContext()));
    }
}
