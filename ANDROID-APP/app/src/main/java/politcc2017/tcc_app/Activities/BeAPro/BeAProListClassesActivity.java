package politcc2017.tcc_app.Activities.BeAPro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 06/04/2017.
 */

public class BeAProListClassesActivity extends BaseActivity{
    FloatingActionButton createClassFAB;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.be_a_pro_list_classes);
        setActivityTitle(getResString(R.string.be_a_pro_activity_title));
        createClassFAB = (FloatingActionButton) findViewById(R.id.create_class_fab);
        createClassFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOrResumeActivity(BeAProCreateClassActivity.class);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.be_a_pro_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.be_a_pro_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BeAProFragment(), "News");
        adapter.addFragment(new BeAProFragment(), "Favorites");
        adapter.addFragment(new BeAProFragment(), "My Classes");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
