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

import java.util.ArrayList;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView drawerRecyclerView;
    GenericAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar baseActionBar;
    int appLanguage;

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
        setSupportActionBar(toolbar);
        baseActionBar = getSupportActionBar();
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

    private void setDrawerData(){
        GenericData data = new GenericData();
        ArrayList<String> drawerItemTexts = ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), appLanguage, R.array.drawer_items);
        data.addStringsToAllCells(GenericData.DRAWER_ITEM_TEXT_KEY, drawerItemTexts);
        mAdapter = new GenericAdapter(data, ViewHolderType.DRAWER_VIEW_HOLDER, R.layout.drawer_item_cell);
        drawerRecyclerView.setAdapter(mAdapter);
        HandleDrawerClicks();
    }

    protected void setActivityTitle(String title){
        if(baseActionBar != null) baseActionBar.setTitle(title);
    }

    protected void setActivityToolbarColor(int colorID){
        toolbar.setBackgroundColor(colorID);
    }

    protected void startOrResumeActivity(Class <? extends BaseActivity> destinationActivity){
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

    protected void HandleDrawerClicks(){
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {
                mDrawerLayout.closeDrawers();
                if(position == 0) startOrResumeActivity(HomeActivity.class);
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

