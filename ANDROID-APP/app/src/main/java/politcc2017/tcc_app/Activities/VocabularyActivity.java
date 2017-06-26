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

import politcc2017.tcc_app.Components.AutoCompleteEditText;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.GenericViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/03/2017.
 */

public class VocabularyActivity extends BaseActivity {
    CustomSearchToolbar mSearchToolbar;
    RecyclerView mRecyclerView;
    GenericAdapter mAdapter;
    GenericData mData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_vocabulary);

        mSearchToolbar = (CustomSearchToolbar) findViewById(R.id.vocabulary_activity_search_toolbar);
        mSearchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        mSearchToolbar.setAutoCompleteSearchBar();
        mRecyclerView = (RecyclerView) findViewById(R.id.vocabulary_words_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = new GenericData();
        mAdapter = new GenericAdapter(mData, ViewHolderType.VOCABULARY_WORD_VIEW_HOLDER,getApplicationContext());
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
        mRecyclerView.setAdapter(mAdapter);
        setActivityTitle(getResString(R.string.vocabulary_activity_title));

        Intent i = getIntent();
        if(i != null){
            String word = i.getStringExtra(WordContextDialog.CONTEXT_SIMILAR_WORDS);
            mSearchToolbar.setSuggestionText(word);
            if(word != null && word.length() > 0) loadSimilarWordsFromServer(word);
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

}
