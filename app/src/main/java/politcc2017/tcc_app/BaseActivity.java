package politcc2017.tcc_app;

/**
 * Created by Jonatas on 25/10/2016.
 */

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView drawerRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBar baseActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        drawerRecyclerView = (RecyclerView) findViewById(R.id.base_drawer_recycler_view);
        drawerRecyclerView.setHasFixedSize(true);
        drawerRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);

        toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teste");
        baseActionBar = getSupportActionBar();
        baseActionBar.setDisplayHomeAsUpEnabled(true);
        drawerRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.base_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.app_name,R.string.app_name);
        mDrawerToggle.syncState();
    }
}

