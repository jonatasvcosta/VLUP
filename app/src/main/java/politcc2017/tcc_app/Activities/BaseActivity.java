package politcc2017.tcc_app.Activities;

/**
 * Created by Jonatas on 25/10/2016.
 */

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
import java.util.Arrays;

import politcc2017.tcc_app.R;
import politcc2017.tcc_app.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.RecyclerView.ViewHolders.DrawerViewHolder;
import politcc2017.tcc_app.RecyclerView.ViewHolders.ViewHolderType;

public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView drawerRecyclerView;
    GenericAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar baseActionBar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        drawerRecyclerView = (RecyclerView) findViewById(R.id.base_drawer_recycler_view);
        drawerRecyclerView.setHasFixedSize(true);
        GenericData data = new GenericData();
        ArrayList<String> drawerItemTexts = new ArrayList<>();
        String[] itemTexts = getResources().getStringArray(R.array.drawer_items);
        for(int i = 0; i < itemTexts.length; i++) drawerItemTexts.add(itemTexts[i]);
        data.addStringsToAllCells(GenericData.DRAWER_ITEM_TEXT_KEY, drawerItemTexts);
        mAdapter = new GenericAdapter(data, ViewHolderType.DRAWER_VIEW_HOLDER, R.layout.drawer_item_cell);
        drawerRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);

        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);
        baseActionBar = getSupportActionBar();
        baseActionBar.setDisplayHomeAsUpEnabled(true);
        drawerRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.base_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.app_name,R.string.app_name);
        mDrawerToggle.syncState();
    }

    protected void setActivityTitle(String title){
        if(baseActionBar != null) baseActionBar.setTitle(title);
    }
}

