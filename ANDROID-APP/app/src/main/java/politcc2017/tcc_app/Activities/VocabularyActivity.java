package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import politcc2017.tcc_app.Components.AutoCompleteEditText;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/03/2017.
 */

public class VocabularyActivity extends BaseActivity {
    CustomSearchToolbar mSearchToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_vocabulary);

        mSearchToolbar = (CustomSearchToolbar) findViewById(R.id.vocabulary_activity_search_toolbar);
        mSearchToolbar.setAutoCompleteSearchBar();
        SetSuggestionList();
    }

    private void SetSuggestionList(){
        //load suggestions from server, based on user learning language

        mSearchToolbar.setAutoCompleteSuggestionList(new String[] {
                "turismo", "viagens", "economia", "mercado financeiro", "finanças", "esportes", "lazer",
        "informática", "tecnologia", "TI", "empreendedorismo", "inovação", "ciência", "filosofia"});
    }

}
