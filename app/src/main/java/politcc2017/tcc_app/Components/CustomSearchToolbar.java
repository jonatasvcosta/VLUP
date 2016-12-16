package politcc2017.tcc_app.Components;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 14/12/2016.
 */

public class CustomSearchToolbar extends LinearLayout {
    private EditText mEditText;
    private Toolbar mToolbar;
    private OnClickListener searchListener;
    private ImageView searchIcon;
    private static final String baseAdress = "http://";

    public void registerSearchListener(OnClickListener listener){
        searchListener = listener;
    }

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
        searchIcon = (ImageView) findViewById(R.id.search_toolbar_search_icon);
        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        mEditText.setTypeface(FontHelper.get(FontHelper.TTF_FONT, c));
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(searchListener != null) searchListener.onClick(searchIcon);
                    return true;
                }
                return false;
            }
        });
        searchIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchListener != null) searchListener.onClick(searchIcon);
            }
        });
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    public String getSearchUrl(){
        String ret = mEditText.getText().toString();
        if(ret.contains(baseAdress)) return ret;
        return (baseAdress+ret);
    }

    public void setSearchUrl(String url){
        mEditText.setText(baseAdress+url);
    }
}
