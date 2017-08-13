package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/03/2017.
 */

public class NewsActivity extends BaseActivity {
    private CustomTextView mNewsText;
    private CustomSearchToolbar mSearchToolbar;
    private RecyclerView mRecyclerView;
    private ImageView listIcon;
    private FloatingActionsMenu ratingMenu;
    private FloatingActionButton addBookshelfFAB, rateGoodFAB, rateMediumFAB, rateBadFAB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news);
        showToolbarRightIcon();
        listIcon = (ImageView) findViewById(R.id.base_toolbar_righ_icon);
        mSearchToolbar = (CustomSearchToolbar) findViewById(R.id.news_activity_search_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_activity_trendingtopics_recyclerview);
        ratingMenu = (FloatingActionsMenu) findViewById(R.id.news_activity_rating_floating_menu);
        addBookshelfFAB = (FloatingActionButton) findViewById(R.id.news_activity_add_bookshelf_btn);
        rateGoodFAB = (FloatingActionButton) findViewById(R.id.news_activity_rate_good_btn);
        rateMediumFAB = (FloatingActionButton) findViewById(R.id.news_activity_rate_neutral_btn);
        rateBadFAB = (FloatingActionButton) findViewById(R.id.news_activity_rate_bad_btn);

        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchToolbar.setAutoCompleteSearchBar();
        mSearchToolbar.setAdvancedFilter(NewsActivity.this, ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.news_search_advanced_filter));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mSearchToolbar.registerRecyclerViewScrollListener(mRecyclerView, displayMetrics.heightPixels);
        setActivityTitle(getResString(R.string.news_activity_title));

        setSuggestionList();
        mNewsText = (CustomTextView) findViewById(R.id.activity_news_text);
        mNewsText.allowWordContextMenu();
        GenericData mData = new GenericData();
        loadTrendingTopics(mData);
        mAdapter = new GenericAdapter(mData, ViewHolderType.TRENDING_TOPICS_VIEW_HOLDER, getApplicationContext());

        setupListeners();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadTrendingTopics(GenericData data){
        //call server here
        ArrayList<String> topics = new ArrayList<>();
        topics.add("Politics");
        topics.add("Refugees");
        topics.add("Global warming");
        topics.add("North Korea");
        topics.add("G5");
        data.addStringsToAllCells(GenericData.TRENDING_TOPIC, topics);
    }

    private void loadNews(String search){
        //call server here
        mNewsText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        ratingMenu.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mSearchToolbar.setSuggestionText(result.get(0));
                }
                break;
            }

        }
    }

    private void setupListeners(){
        addBookshelfFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Add to bookshelf!", Toast.LENGTH_SHORT).show();
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_ADD_WORD_BOOKSHELF));
            }
        });
        rateGoodFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "God!", Toast.LENGTH_SHORT).show();
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_RATE_TEXT));
            }
        });
        rateMediumFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Medium!", Toast.LENGTH_SHORT).show();
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_RATE_TEXT));
            }
        });
        rateBadFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Bad!", Toast.LENGTH_SHORT).show();
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_RATE_TEXT));
            }
        });
        mSearchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        mSearchToolbar.registerSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNews(mSearchToolbar.getSuggestionText());
            }
        });
        listIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mNewsText.setVisibility(View.GONE);
                ratingMenu.setVisibility(View.GONE);
            }
        });
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, int position) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                mSearchToolbar.setSuggestionText(message);
                loadNews(message);
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setSuggestionList(){
        //load suggestions from server, based on user learning language

        mSearchToolbar.setAutoCompleteSuggestionList(new String[] {
                "turismo", "viagens", "economia", "mercado financeiro", "finanças", "esportes", "lazer",
                "informática", "tecnologia", "TI", "empreendedorismo", "inovação", "ciência", "filosofia"});
    }
}
