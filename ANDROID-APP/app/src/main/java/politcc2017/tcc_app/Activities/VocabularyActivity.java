package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/03/2017.
 */

public class VocabularyActivity extends BaseActivity {
    private CustomSearchToolbar mSearchToolbar;
    private RecyclerView wordsRecyclerView, trendingTopicsRecyclerView;
    private GenericAdapter mAdapter, trendingAdapter;
    private GenericData mData, trendingData;
    private ImageView listIcon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_vocabulary);
        showToolbarRightIcon();
        listIcon = (ImageView) findViewById(R.id.base_toolbar_righ_icon);
        mSearchToolbar = (CustomSearchToolbar) findViewById(R.id.vocabulary_activity_search_toolbar);
        mSearchToolbar.setAutoCompleteSearchBar();
        mSearchToolbar.setAdvancedFilter(VocabularyActivity.this, ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.vocabulary_search_advanced_filter));
        wordsRecyclerView = (RecyclerView) findViewById(R.id.vocabulary_words_recyclerview);
        trendingTopicsRecyclerView = (RecyclerView) findViewById(R.id.vocabulary_activity_trendingtopics_recyclerview);
        wordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trendingTopicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trendingTopicsRecyclerView.setVisibility(View.VISIBLE);
        mData = new GenericData();
        trendingData = new GenericData();
        loadTrendingTopics(trendingData);
        trendingAdapter = new GenericAdapter(trendingData, ViewHolderType.TRENDING_TOPICS_VIEW_HOLDER, getApplicationContext());
        wordsRecyclerView.setAdapter(mAdapter);
        mAdapter = new GenericAdapter(mData, ViewHolderType.VOCABULARY_WORD_VIEW_HOLDER,getApplicationContext());
        setupListeners();
        trendingTopicsRecyclerView.setAdapter(trendingAdapter);
        setActivityTitle(getResString(R.string.vocabulary_activity_title));

        Intent i = getIntent();
        if(i != null){
            String word = i.getStringExtra(WordContextDialog.CONTEXT_SIMILAR_WORDS);
            mSearchToolbar.setSuggestionText(word);
            if(word != null && word.length() > 0){
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_CHECK_SIMILAR_WORDS));
                loadSimilarWordsFromServer(word);
            }
        }

        SetSuggestionList();

        mSearchToolbar.registerSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWords(mSearchToolbar.getSuggestionText());
            }
        });
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

    @Override
    public void handleLearningLanguageChange() {
        Toast.makeText(getBaseContext(), "Language was changed, VocabularyActivity must reload all the content", Toast.LENGTH_SHORT).show();
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

    private void loadSimilarWordsFromServer(String word){
        if(word == null || word.length() == 0) return;
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        words.add("similar 1 to " + word);
        words.add("similar 2 to " + word);
        words.add("similar 3 to " + word);
        count.add(2);
        count.add(30);
        count.add(200);
        mData.clearAllCells();
        mData.addStringsToAllCells(GenericData.VOCABULARY_WORD, words);
        mData.addIntegersToAllCells(GenericData.VOCABULARY_COUNT, count);
        mAdapter.notifyDataSetChanged();
    }

    private void loadWords(String category){
        wordsRecyclerView.setVisibility(View.VISIBLE);
        trendingTopicsRecyclerView.setVisibility(View.GONE);
        category = category.trim().toLowerCase();
        //load words from server base on category
        if(category == null || category.equals("")) return;
        mData.clearAllCells();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        if(category.equals("turismo")) {
            words.add("travel");
            words.add("map");
            words.add("guide");
            count.add(20);
            count.add(3);
            count.add(2);
        }
        else if(category.equals("ti")){
            words.add("programming");
            words.add("algorithm");
            count.add(20);
            count.add(312);
        }
        mData.addStringsToAllCells(GenericData.VOCABULARY_WORD, words);
        mData.addIntegersToAllCells(GenericData.VOCABULARY_COUNT, count);
        mAdapter.notifyDataSetChanged();
    }

    private void SetSuggestionList(){
        //load suggestions from server, based on user learning language

        mSearchToolbar.setAutoCompleteSuggestionList(new String[] {
                "turismo", "viagens", "economia", "mercado financeiro", "finanças", "esportes", "lazer",
        "informática", "tecnologia", "TI", "empreendedorismo", "inovação", "ciência", "filosofia"});
    }

    private void setupListeners(){
        mSearchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        listIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trendingTopicsRecyclerView.setVisibility(View.VISIBLE);
                wordsRecyclerView.setVisibility(View.GONE);
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
                WordContextDialog.launchDialog(VocabularyActivity.this, message);
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
        trendingAdapter.RegisterClickListener(new CellClickListener() {
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
                loadWords(message);
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
    }
}
