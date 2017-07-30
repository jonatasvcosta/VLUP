package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/03/2017.
 */

public class NewsActivity extends BaseActivity {
    private CustomTextView mNewsText;
    private CustomSearchToolbar mSearchToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news);
        mSearchToolbar = (CustomSearchToolbar) findViewById(R.id.news_activity_search_toolbar);
        mSearchToolbar.setAutoCompleteSearchBar();
        mSearchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        SetSuggestionList();
        mSearchToolbar.registerSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNews(mSearchToolbar.getSuggestionText());
            }
        });
        mNewsText = (CustomTextView) findViewById(R.id.activity_news_text);
        mNewsText.allowWordContextMenu();
    }

    private void loadNews(String search){
        mNewsText.setVisibility(View.VISIBLE);
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

    private void SetSuggestionList(){
        //load suggestions from server, based on user learning language

        mSearchToolbar.setAutoCompleteSuggestionList(new String[] {
                "turismo", "viagens", "economia", "mercado financeiro", "finanças", "esportes", "lazer",
                "informática", "tecnologia", "TI", "empreendedorismo", "inovação", "ciência", "filosofia"});
    }
}
