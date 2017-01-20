package politcc2017.tcc_app.Activities;

/**
 * Created by Jonatas on 25/10/2016.
 */

import android.content.Intent;
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

import java.util.ArrayList;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class BaseActivity extends AppCompatActivity {
    public static final int POS_HOME = 0, POS_NAVIGATE = 3;
    Toolbar toolbar;
    CustomTextView toolbarTitle;
    RecyclerView drawerRecyclerView;
    GenericAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar baseActionBar;
    int appLanguage;
    ImageView rightIcon, rightMostIcon;

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

    private void setDrawerData(){
        GenericData data = new GenericData();
        ArrayList<String> drawerItemTexts = ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), appLanguage, R.array.drawer_items);
        ArrayList<Integer> drawerItemIcons = ResourcesHelper.getIntArrayAsArrayList(getBaseContext(), R.array.drawer_items_icons);
        data.addStringsToAllCells(GenericData.DRAWER_ITEM_TEXT_KEY, drawerItemTexts);
        data.addIntegersToAllCells(GenericData.DRAWER_ITEM_ICON_KEY, drawerItemIcons);
        mAdapter = new GenericAdapter(data, ViewHolderType.DRAWER_VIEW_HOLDER, R.layout.drawer_item_cell);
        drawerRecyclerView.setAdapter(mAdapter);
        HandleDrawerClicks();
    }

    protected void setActivityTitle(String title){
        if(baseActionBar != null) toolbarTitle.setText(title);
    }

    protected void setActivityToolbarColor(int colorID){
        toolbar.setBackgroundColor(colorID);
    }

    protected void startOrResumeActivity(Class <? extends BaseActivity> destinationActivity){
        hideRightIcons();
        Intent intent = new Intent(getBaseContext(), destinationActivity);
        intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    protected void startOrResumeActivity(Class <? extends BaseActivity> destinationActivity, boolean closeAllPreviousActivities){
        Intent intent = new Intent(getBaseContext(), destinationActivity);
        if(closeAllPreviousActivities) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        else intent.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    protected void hideRightIcons(){
        if(rightIcon.getVisibility() != View.GONE) rightIcon.setVisibility(View.GONE);
        if(rightMostIcon.getVisibility() != View.GONE) rightMostIcon.setVisibility(View.GONE);
    }

    protected void showRightIcons(){
        if(rightIcon.getVisibility() != View.VISIBLE) rightIcon.setVisibility(View.VISIBLE);
        if(rightMostIcon.getVisibility() != View.VISIBLE) rightMostIcon.setVisibility(View.VISIBLE);
    }

    protected void HandleDrawerClicks(){
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {
                mDrawerLayout.closeDrawers();
                if(position == POS_HOME) startOrResumeActivity(HomeActivity.class);
                else if(position == POS_NAVIGATE) startOrResumeActivity(NavigateActivity.class);
                else startOrResumeActivity(MaintenanceActivity.class, true);
            }

            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setAppLanguage(int appLanguage){
        this.appLanguage = appLanguage;
        setDrawerData();
    }
}

