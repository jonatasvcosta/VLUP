package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.AutoCompleteEditText;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.GenericViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
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
        mSearchToolbar.setAutoCompleteSearchBar();
        mRecyclerView = (RecyclerView) findViewById(R.id.vocabulary_words_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mData = new GenericData();
        mAdapter = new GenericAdapter(mData, ViewHolderType.VOCABULARY_WORD_VIEW_HOLDER,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        SetSuggestionList();

        mSearchToolbar.registerSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWords(mSearchToolbar.getSuggestionText());
            }
        });
    }

    private void loadWords(String category){
        category = category.trim().toLowerCase();
        //load words from server base on category
        if(category == null || category.equals("")) return;
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
