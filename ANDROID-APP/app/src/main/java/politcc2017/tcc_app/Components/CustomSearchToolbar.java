package politcc2017.tcc_app.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.Helpers.AndroidHelper;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 14/12/2016.
 */

public class CustomSearchToolbar extends LinearLayout {
    private EditText mEditText;
    private AutoCompleteEditText mAutoCompleteEditText;
    private CustomButton advancedFilter;
    private Toolbar mToolbar;
    private OnClickListener searchListener, microphoneListener;
    private ImageView searchIcon, microphoneIcon;
    private ArrayList<String> filterList;
    private static final String baseAdress = "http://";
    private static final String secureBaseAdress = "https://";
    private static final float TOOLBAR_ELEVATION = 14f;

    private int verticalOffset;
    // Determines the scroll UP/DOWN offset
    private int scrollingOffset;

    public String getRawURL(){
        String ret = mEditText.getText().toString();
        if(ret != null && ret.length() > 0 && ret.contains(baseAdress)) return ret.substring(baseAdress.length());
        return ret;
    }

    public String getBaseAdress(){
        return baseAdress;
    }

    public void registerRecyclerViewScrollListener(final RecyclerView r, final int screenHeight){
            r.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (scrollingOffset > 0) {
                            if (verticalOffset > mToolbar.getHeight()) {
                                toolbarAnimateHide();
                            } else {
                                toolbarAnimateShow(verticalOffset);
                            }
                        } else if (scrollingOffset < 0) {
                            if (mToolbar.getTranslationY() < mToolbar.getHeight() * -0.6 && verticalOffset > mToolbar.getHeight()) {
                                toolbarAnimateHide();
                            } else {
                                toolbarAnimateShow(verticalOffset);
                            }
                        }
                    }
                }

                @Override
                public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    verticalOffset = r.computeVerticalScrollOffset();
                    scrollingOffset = dy;
                    int toolbarYOffset = (int) (dy - mToolbar.getTranslationY());
                    mToolbar.animate().cancel();
                    if (scrollingOffset > 0) {
                        if (toolbarYOffset < mToolbar.getHeight()) {
                            if (verticalOffset > mToolbar.getHeight()) {
                                toolbarSetElevation(TOOLBAR_ELEVATION);
                            }
                            mToolbar.setTranslationY(-toolbarYOffset);
                        } else {
                            toolbarSetElevation(0);
                            mToolbar.setTranslationY(-mToolbar.getHeight());
                        }
                    } else if (scrollingOffset < 0) {
                        if (toolbarYOffset < 0) {
                            if (verticalOffset <= 0) {
                                toolbarSetElevation(0);
                            }
                            mToolbar.setTranslationY(0);
                        } else {
                            if (verticalOffset > mToolbar.getHeight()) {
                                toolbarSetElevation(TOOLBAR_ELEVATION);
                            }
                            mToolbar.setTranslationY(-toolbarYOffset);
                        }
                    }
                }
            });
    }


    public void registerSearchListener(OnClickListener listener){
        searchListener = listener;
    }

    public void registerMicrophoneListener(OnClickListener listener){
        microphoneListener = listener;
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
        mAutoCompleteEditText = (AutoCompleteEditText) findViewById(R.id.search_toolbar_edit_text_autocomplete);
        mEditText.setVisibility(VISIBLE);
        mAutoCompleteEditText.setVisibility(GONE);
        searchIcon = (ImageView) findViewById(R.id.search_toolbar_search_icon);
        microphoneIcon = (ImageView) findViewById(R.id.search_toolbar_microphone_icon);
        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        advancedFilter = (CustomButton) findViewById(R.id.advanced_filter_link);
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
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        microphoneIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(microphoneListener != null) microphoneListener.onClick(microphoneIcon);
            }
        });
    }

    public void notifySearchComplete(){
        if(searchListener != null) searchListener.onClick(searchIcon);
    }

    public void setAutoCompleteSearchBar(){
        mEditText.setVisibility(GONE);
        mAutoCompleteEditText.setVisibility(VISIBLE);
    }

    public void setAutoCompleteSuggestionList(String[] suggestions){
        mAutoCompleteEditText.setAutoCompleteList(suggestions);
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    public String getSearchUrl(){
        String ret = mEditText.getText().toString();
        if(ret.contains(baseAdress)) return ret;
        return (baseAdress+ret);
    }

    public String getSuggestionText(){
        return mAutoCompleteEditText.getText().toString();
    }

    public void setSuggestionText(String text){
        mAutoCompleteEditText.setText(text);
        notifySearchComplete();
    }

    public void setNonSearchableSuggestionText(String text){
        mAutoCompleteEditText.setText(text);
    }

    public void setSearchUrl(String url){
        if(url.contains(secureBaseAdress)){
            url = url.replace(secureBaseAdress, baseAdress);
            mEditText.setText(url);
        }
        else if(!url.contains(baseAdress))mEditText.setText(baseAdress+url);
        else mEditText.setText(url);
    }

    private void toolbarAnimateShow(final int verticalOffset) {
        mToolbar.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbarSetElevation(verticalOffset == 0 ? 0 : TOOLBAR_ELEVATION);
                    }
                });
    }

    private void toolbarAnimateHide() {
        mToolbar.animate()
                .translationY(-mToolbar.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toolbarSetElevation(float elevation) {
        // setElevation() only works on Lollipop
        if (AndroidHelper.isLollipop()) {
            mToolbar.setElevation(elevation);
        }
    }

    public ArrayList<String> getAdvancedFilterList(){
        return filterList;
    }

    public void setAdvancedFilter(final Context context, final ArrayList<String> list){
        advancedFilter.setVisibility(VISIBLE);
        advancedFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.ListMultiChoiceDialog(context, getResources().getString(R.string.advanced_filter_text), list, getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {

                        if(filterList == null) filterList = new ArrayList<String>();
                        String filter = "";
                        for(int i = 0; i < text.length; i++){
                            filterList.add(text[i].toString());
                            filter += text[i]+" ";
                        }
                        Toast.makeText(context, filter, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }).show();
            }
        });
    }

}
