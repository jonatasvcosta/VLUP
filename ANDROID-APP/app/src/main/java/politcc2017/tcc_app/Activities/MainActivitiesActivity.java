package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Activities.BeAPro.BeAProFragment;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Listeners.FragmentListener;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 06/04/2017.
 */

public class MainActivitiesActivity extends BaseActivity implements FragmentListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsFragment newsFragment;
    private VocabularyFragment vocabularyFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home_tab_layout);
        setActivityTitle(getResString(R.string.be_a_pro_activity_title));
        newsFragment = new NewsFragment(this);
        vocabularyFragment = new VocabularyFragment(this);
        homeFragment = new HomeFragment(this);
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                if(tab.getPosition() == 0) hideRightIcons();
                else{
                    showToolbarRightIcon();
                    ImageView listIcon =(ImageView) findViewById(R.id.base_toolbar_righ_icon);
                    listIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(tab.getPosition() == 1) newsFragment.switchRecyclerView();
                            else if(tab.getPosition() == 2) vocabularyFragment.switchRecyclerViews();
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Intent i = getIntent();
        if(i != null) {
            String word = i.getStringExtra(WordContextDialog.CONTEXT_SIMILAR_WORDS);
            String defaultActivity = i.getStringExtra("parameter");
            if(defaultActivity != null && defaultActivity.equals("VOCABULARY_ACTIVITY")) viewPager.setCurrentItem(2);
            if(defaultActivity != null && defaultActivity.equals("NEWS_ACTIVITY")) viewPager.setCurrentItem(1);
            if(word != null && word.length() > 0){
                viewPager.setCurrentItem(2);
                vocabularyFragment.loadSimilarWordsFromServer(word);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(homeFragment, "Home");
        adapter.addFragment(newsFragment, getResString(R.string.news_activity_title));
        adapter.addFragment(vocabularyFragment, getResString(R.string.vocabulary_activity_title));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onMessageSent(String sender, String message) {
        if(message.equals(SqlHelper.RULE_CHECK_SIMILAR_WORDS)) this.scorePoints("+"+getScoringPoints(SqlHelper.RULE_CHECK_SIMILAR_WORDS));
        if(message.equals(SqlHelper.RULE_ADD_WORD_BOOKSHELF)) this.scorePoints("+"+getScoringPoints(SqlHelper.RULE_ADD_WORD_BOOKSHELF));
        if(message.equals(SqlHelper.RULE_RATE_TEXT)) this.scorePoints("+"+getScoringPoints(SqlHelper.RULE_RATE_TEXT));
    }

    @Override
    public void onMessageSent(String sender, String message, String value) {
        if(sender.equals("HOME_FRAGMENT") && message.equals("SWITCH_VOCABULARY") && value != null){
            viewPager.setCurrentItem(2);
            vocabularyFragment.setupTrendingWords(value);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(final int position) {
            //setActivityTitle(mFragmentTitleList.get(position));
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
