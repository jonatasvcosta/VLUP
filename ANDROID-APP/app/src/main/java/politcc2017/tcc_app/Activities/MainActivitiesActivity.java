package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
    public static final int POS_HOME_TAB = 0, POS_NEWS_TAB = 2, POS_VOCABULARY_TAB = 1;
    private boolean setupSimilarWords = false, setupSearchInput = false, setupNews = false;
    private String similarWord, speechInput, newsText;
    private int reqCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home_tab_layout);
        setActivityTitle(getResString(R.string.home_activity_title));
        newsFragment = new NewsFragment(this);
        vocabularyFragment = new VocabularyFragment(this);
        homeFragment = new HomeFragment(this);
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        setupViewPager(viewPager);
        setupSimilarWords = setupSearchInput = false;
        tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == POS_HOME_TAB) setActivityTitle(getResString(R.string.home_activity_title));
                if(tab.getPosition() == POS_VOCABULARY_TAB) setActivityTitle(getResString(R.string.vocabulary_activity_title));
                if(tab.getPosition() == POS_NEWS_TAB) setActivityTitle(getResString(R.string.news_activity_title));
                setupActionBarIcons(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        this.setActivityListener(this);

        Intent i = getIntent();
        if(i != null) {
            String word = i.getStringExtra(WordContextDialog.CONTEXT_SIMILAR_WORDS);
            String text = i.getStringExtra(WordContextDialog.CONTEXT_ADD_TEXT);
            String defaultActivity = i.getStringExtra("parameter");
            if(defaultActivity != null && defaultActivity.equals("VOCABULARY_ACTIVITY")) viewPager.setCurrentItem(POS_VOCABULARY_TAB);
            else if(defaultActivity != null && defaultActivity.equals("NEWS_ACTIVITY")){
                viewPager.setCurrentItem(POS_NEWS_TAB);
                if(text != null && !text.isEmpty()){
                    setupNews = true;
                    newsText = text;
                }
            }
            else if(defaultActivity != null && defaultActivity.equals("HOME_ACTIVITY")) viewPager.setCurrentItem(POS_HOME_TAB);
            if(word != null && word.length() > 0){
                viewPager.setCurrentItem(POS_VOCABULARY_TAB);
                setupSimilarWords = true;
                similarWord = word;
            }
        }
    }

    private void setupActionBarIcons(final int position){
        if(position == POS_HOME_TAB) hideRightIcons();
        else{
            showToolbarRightIcon();
            ImageView listIcon =(ImageView) findViewById(R.id.base_toolbar_righ_icon);
            listIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == POS_NEWS_TAB) newsFragment.switchRecyclerView();
                    else if(position == POS_VOCABULARY_TAB) vocabularyFragment.switchRecyclerViews();
                }
            });
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(homeFragment, getResString(R.string.home_activity_title));
        adapter.addFragment(vocabularyFragment, getResString(R.string.vocabulary_activity_title));
        adapter.addFragment(newsFragment, getResString(R.string.news_activity_title));
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            this.reqCode = requestCode;
            this.speechInput = result.get(0);
            this.setupSearchInput = true;
            if(reqCode == this.REQ_CODE_NEWS_SPEECH_INPUT) newsFragment.setupSpeechInput(speechInput);
            if(reqCode == this.REQ_CODE_VOCABULARY_SPEECH_INPUT) vocabularyFragment.setupSpeechInput(speechInput);
        }

    }

    @Override
    public void onMessageSent(String sender, String message) {
        if(sender.equals("BASE_ACTIVITY") && message.equals("HOME_ACTIVITY")){
            this.viewPager.setCurrentItem(0);
            setupActionBarIcons(0);
        }
        else if(sender.equals("BASE_ACTIVITY") && message.equals("NEWS_ACTIVITY")){
            this.viewPager.setCurrentItem(POS_NEWS_TAB);
            setupActionBarIcons(POS_NEWS_TAB);
        }
        else if(sender.equals("BASE_ACTIVITY") && message.equals("VOCABULARY_ACTIVITY")){
            this.viewPager.setCurrentItem(POS_VOCABULARY_TAB);
            setupActionBarIcons(POS_VOCABULARY_TAB);
        }
        if(sender.equals("NEWS_FRAGMENT") && message.equals("READY") && setupNews){
            setupNews = false;
            newsFragment.setNewsText(newsText);
        }
        if(sender.equals("VOCABULARY_FRAGMENT") && message.equals("READY") && setupSimilarWords){
            setupSimilarWords = false;
            vocabularyFragment.loadSimilarWordsFromServer(similarWord);
        }
        if(message.equals("READY") && setupSearchInput){
            setupSearchInput = false;
            if(sender.equals("VOCABULARY_FRAGMENT")) vocabularyFragment.setupSpeechInput(speechInput);
            if(sender.equals("NEWS_FRAGMENT")) newsFragment.setupSpeechInput(speechInput);
        }
        if(message.equals("PROMPT_SPEECH")) promptSpeechInput(sender);
        if(message.equals(SqlHelper.RULE_CHECK_SIMILAR_WORDS)) this.scorePoints("+"+getScoringPoints(SqlHelper.RULE_CHECK_SIMILAR_WORDS));
        if(message.equals(SqlHelper.RULE_ADD_WORD_BOOKSHELF)) this.scorePoints("+"+getScoringPoints(SqlHelper.RULE_ADD_WORD_BOOKSHELF));
        if(message.equals(SqlHelper.RULE_RATE_TEXT)) this.scorePoints("+"+getScoringPoints(SqlHelper.RULE_RATE_TEXT));
    }

    @Override
    public void onMessageSent(String sender, String message, String value) {
        if(sender.equals("HOME_FRAGMENT") && message.equals("SWITCH_VOCABULARY") && value != null){
            viewPager.setCurrentItem(POS_VOCABULARY_TAB);
            vocabularyFragment.setupTrendingWords(value);
        }
        if(sender.equals("HOME_FRAGMENT") && message.equals("SWITCH_NEWS") && value != null){
            viewPager.setCurrentItem(POS_NEWS_TAB);
            newsFragment.setNewsText(value);
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
