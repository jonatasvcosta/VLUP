package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 14/12/2016.
 */

public class CustomSearchToolbar extends LinearLayout {
    EditText mEditText;
    public CustomSearchToolbar(Context context) {
        super(context);
        ComponentSetup(context, null);
    }

    public CustomSearchToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        ComponentSetup(context, attrs);
    }

    public CustomSearchToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ComponentSetup(context, attrs);
    }

    protected void ComponentSetup(Context c, AttributeSet attrs){
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_search_toolbar, this, true);
        mEditText = (EditText) findViewById(R.id.search_toolbar_edit_text);
        mEditText.setTypeface(FontHelper.get(FontHelper.TTF_FONT, c));
    }
}
