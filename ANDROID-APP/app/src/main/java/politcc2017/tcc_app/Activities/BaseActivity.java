package politcc2017.tcc_app.Activities;

/**
 * Created by Jonatas on 25/10/2016.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.speech.RecognizerIntent;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Locale;

import politcc2017.tcc_app.Activities.BeAPro.BeAProListClassesActivity;
import politcc2017.tcc_app.Activities.Bookshelf.BookshelfActivity;
import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.TextToSpeechComponent;
import politcc2017.tcc_app.R;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class BaseActivity extends AppCompatActivity {
    public static boolean LANGUAGE_SET = false;
    public static final int POS_HOME = 0, POS_VOCABULARY = 1, POS_NEWS = 2, POS_NAVIGATE = 3, POS_BE_A_PRO = 4, POS_BOOKSHELF = 5, POS_DICTIONARY = 6, POS_CAMERA = 7, POS_SETTINGS = 9;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Toolbar toolbar;
    CustomTextView toolbarTitle;
    RecyclerView drawerRecyclerView;
    GenericAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar baseActionBar;
    private int appLanguage = 0;
    private int learningLanguage = 0;
    ImageView rightIcon, rightMostIcon, leftMostIcon, flagIcon;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        drawerRecyclerView = (RecyclerView) findViewById(R.id.base_drawer_recycler_view);
        setDrawerData();
        mLayoutManager = new LinearLayoutManager(this);
        drawerRecyclerView.setLayoutManager(mLayoutManager);
        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        toolbarTitle = (CustomTextView) findViewById(R.id.base_toolbar_title);
        rightIcon = (ImageView) findViewById(R.id.base_toolbar_righ_icon);
        rightMostIcon = (ImageView) findViewById(R.id.base_toolbar_rightmost_icon);
        leftMostIcon = (ImageView) findViewById(R.id.base_toolbar_leftmost_icon);
        flagIcon = (ImageView) findViewById(R.id.base_toolbar_language_icon);
        setSupportActionBar(toolbar);
        baseActionBar = getSupportActionBar();
        baseActionBar.setTitle("");
        if(baseActionBar != null) baseActionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.base_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,R.string.app_name,R.string.app_name){
            public void onDrawerClosed(View view) {
            }
            public void onDrawerOpened(View drawerView) {
            }
        };
        mDrawerToggle.syncState();
        setLearningLanguage();
        flagIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLearningLanguageChoice(BaseActivity.this);
            }
        });
    }

    protected void hideActionBar(){
        if(baseActionBar != null) baseActionBar.hide();
    }

    protected void showActionBar(){
        if(baseActionBar != null) baseActionBar.show();
    }

    protected void setDefaultBaseSupportActionBar(){
        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        toolbarTitle = (CustomTextView) findViewById(R.id.base_toolbar_title);
        setSupportActionBar(toolbar);
        baseActionBar = getSupportActionBar();
        baseActionBar.setTitle("");
    }

    protected void handleLearningLanguageChange(){} //each activity must handle this method

    private void setDrawerData(){
        GenericData data = new GenericData();
        String[] texts = getResources().getStringArray(R.array.drawer_items);
        ArrayList<String> drawerItemTexts = new ArrayList<>();
        for(int i = 0; i < texts.length; i++) drawerItemTexts.add(i, texts[i]);
        ArrayList<Integer> drawerItemIcons = ResourcesHelper.getIntArrayAsArrayList(getBaseContext(), R.array.drawer_items_icons);
        data.addStringsToAllCells(GenericData.DRAWER_ITEM_TEXT_KEY, drawerItemTexts);
        data.addIntegersToAllCells(GenericData.DRAWER_ITEM_ICON_KEY, drawerItemIcons);
        mAdapter = new GenericAdapter(data, ViewHolderType.DRAWER_VIEW_HOLDER);
        drawerRecyclerView.setAdapter(mAdapter);
        HandleDrawerClicks();
    }

    protected void setActivityTitle(String title){
        if(baseActionBar != null) toolbarTitle.setText(title);
    }

    protected void setActivityToolbarColor(int colorID){
        toolbar.setBackgroundColor(colorID);
    }

    protected void promptSpeechInput() {
        ArrayList<String> locales = ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.locale_array);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale(locales.get(learningLanguage)));
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Speech not supported", Toast.LENGTH_SHORT).show();
        }
    }

    public void startOrResumeActivity(Class <? extends BaseActivity> destinationActivity, String parameter, int id){
        hideRightIcons();
        Intent intent = new Intent(getBaseContext(), destinationActivity);
        intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        if(parameter != null) intent.putExtra("parameter", parameter);
        if(id != -1) intent.putExtra("id", id);
        startActivity(intent);
    }

    public void startOrResumeActivity(Class <? extends BaseActivity> destinationActivity){
        startOrResumeActivity(destinationActivity, null, -1);
    }

    public void startOrResumeActivity(Class <? extends BaseActivity> destinationActivity, boolean closeAllPreviousActivities){
        hideRightIcons();
        Intent intent = new Intent(getBaseContext(), destinationActivity);
        if(closeAllPreviousActivities) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        else intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    protected void hideRightIcons(){
        if(rightIcon.getVisibility() != View.GONE) rightIcon.setVisibility(View.GONE);
        if(rightMostIcon.getVisibility() != View.GONE) rightMostIcon.setVisibility(View.GONE);
        if(leftMostIcon.getVisibility() != View.GONE) leftMostIcon.setVisibility(View.GONE);
    }

    protected void showToolbarRightIcons(){
        if(rightIcon.getVisibility() != View.VISIBLE) rightIcon.setVisibility(View.VISIBLE);
        if(rightMostIcon.getVisibility() != View.VISIBLE) rightMostIcon.setVisibility(View.VISIBLE);
    }

    protected void setupEditorToolbarRightIcons(final ContextMenuClickListener listener){
        if(rightIcon.getVisibility() != View.VISIBLE) {
            rightIcon.setImageResource(R.drawable.ic_action_undo);
            rightIcon.setVisibility(View.VISIBLE);
        }
        if(rightMostIcon.getVisibility() != View.VISIBLE){
            rightMostIcon.setImageResource(R.drawable.ic_action_redo);
            rightMostIcon.setVisibility(View.VISIBLE);
        }
        leftMostIcon.setVisibility(View.VISIBLE);
        if(listener != null){
            rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, "undo");
                }
            });
            rightMostIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, "redo");
                }
            });
            leftMostIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, "save");
                }
            });
        }
    }

    protected void HandleDrawerClicks(){
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {
                mDrawerLayout.closeDrawers();
                if(position == POS_HOME) startOrResumeActivity(HomeActivity.class, true);
                else if(position == POS_VOCABULARY) startOrResumeActivity(VocabularyActivity.class, true);
                else if(position == POS_NEWS) startOrResumeActivity(NewsActivity.class, true);
                else if(position == POS_NAVIGATE) startOrResumeActivity(NavigateActivity.class, true);
                else if(position == POS_BE_A_PRO) startOrResumeActivity(BeAProListClassesActivity.class, true);
                else if(position == POS_BOOKSHELF) startOrResumeActivity(BookshelfActivity.class, true);
                else if(position == POS_DICTIONARY) startOrResumeActivity(DictionaryActivity.class, true);
                else if(position == POS_CAMERA) startOrResumeActivity(CameraActivity.class, true);
                else if(position == POS_SETTINGS) startOrResumeActivity(SettingsActivity.class, true);
                else startOrResumeActivity(MaintenanceActivity.class, true);
            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, int position) {

            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void handleLearningLanguageChoice(Context c){
        final String[] languages = getResources().getStringArray(R.array.languages_array);
        final ArrayList<String> languagesList = new ArrayList<>();
        for(int i = 0; i < languages.length; i++) languagesList.add(i, languages[i]);
        DialogHelper.ListSingleChoiceDialog(c, getResString(R.string.signup_activity_languages_field), languagesList, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                SharedPreferencesHelper.addInt(SharedPreferencesHelper.LEARNING_LANGUAGE_KEY, which);
                changeLearningLanguage(which);
                return true;
            }
        }).show();
    }

    public String getResString(int id){
        return getResources().getString(id);
    }

    public void setAppLanguage(int appLanguage){
        this.appLanguage = appLanguage;
        setDrawerData();
    }

    public void changeAppLanguage(String loc){
        Locale locale = new Locale(loc);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
        recreate();
    }

    public void changeLearningLanguage(int languageIndex){
        if(languageIndex == this.learningLanguage) return;
        ArrayList<Integer> flagIcons = ResourcesHelper.getIntArrayAsArrayList(getBaseContext(), R.array.languages_icons);
        ArrayList<String> locales = ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.locale_array);
        flagIcon.setImageResource(flagIcons.get(languageIndex));
        this.learningLanguage = languageIndex;
        TextToSpeechComponent.setTextToSpeechLocale(getApplicationContext(), new Locale(locales.get(learningLanguage)));
        handleLearningLanguageChange();
    }

    public void setLearningLanguage(){
        int language = SharedPreferencesHelper.getInt(SharedPreferencesHelper.LEARNING_LANGUAGE_KEY, getBaseContext());
        if(language == - 1) language = learningLanguage;
        changeLearningLanguage(language);
    }

    public void setLanguage(){
        String locale = SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY, getBaseContext());
        if(locale != null && locale.length() > 0){
            LANGUAGE_SET = true;
            changeAppLanguage(locale);
        }
    }

    //Restarts the activity after changing the languagse
    private void RestartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

