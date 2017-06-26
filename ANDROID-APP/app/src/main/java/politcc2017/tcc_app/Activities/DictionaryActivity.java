package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 28/04/2017.
 */

public class DictionaryActivity extends BaseActivity implements View.OnClickListener{
    private WebView mWebView;
    private RecyclerView mRecyclerView;
    private CustomSearchToolbar searchToolbar;
    private String baseUrl = "https://www.google.com/search?q=";
    private ImageView toolbarSearchIcon, toolbarListIcon;
    private float SCROL_DY;
    private boolean contextWordDialogOpened = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_dictionary);
        SCROL_DY = getResources().getDimension(R.dimen.margin_extra_large);
        setActivityTitle(getResources().getString(R.string.navigate_activity_title));
        mRecyclerView = (RecyclerView) findViewById(R.id.dictionary_activity_list);
        searchToolbar = (CustomSearchToolbar) findViewById(R.id.dictionary_activity_search_toolbar);
        searchToolbar.registerSearchListener(this);
        searchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        searchToolbar.registerRecyclerViewScrollListener(mRecyclerView, displayMetrics.heightPixels);
        SetSuggestionListData();
    }

    private void SetSuggestionListData(){
        GenericData data = new GenericData();

        //These fake data will be replaced by data from server:
        ArrayList<String> content = new ArrayList<>();
        content.add("<p><strong>program</strong></p>\n" +
                "<p><em>[proh-gram, -gruh m]</em></p>\n" +
                "<p><span style=\"text-decoration: underline;\">noun</span></p>\n" +
                "<ul>\n" +
                "<li>A plan of action to accomplish a specified end: a school lunch program.</li>\n" +
                "</ul>\n" +
                "<p>&nbsp;</p>\n" +
                "<ul>\n" +
                "<li>a plan or schedule of activities, procedures, etc., to be followed.</li>\n" +
                "</ul>\n" +
                "<p>&nbsp;</p>\n" +
                "<ul>\n" +
                "<li>a broadcasted television or radio production or similar Internet-based content produced for distribution. 4. a list of items, pieces, performers, etc., in a musical, theatrical, or other entertainment.</li>\n" +
                "</ul>\n" +
                "<p>&nbsp;</p>\n" +
                "<ul>\n" +
                "<li>an entertainment with reference to its pieces or numbers: a program of American and French music.</li>\n" +
                "</ul>\n" +
                "<p>&nbsp;</p>\n" +
                "<ul>\n" +
                "<li>a planned, coordinated group of activities, procedures, etc., often for a specific purpose, or a facility offering such a series of activities: a drug rehabilitation program; a graduate program in linguistics.</li>\n" +
                "</ul>\n" +
                "<p>&nbsp;</p>\n" +
                "<ul>\n" +
                "<li>a prospectus or syllabus: a program of courses being offered.</li>\n" +
                "</ul>");
        content.add("<p><em>[substantivo]</em></p> programa");
        content.add("<p><em>[verbo]</em></p> programar");
        //end of fake data
        data.addStringsToAllCells(GenericData.DICTIONARY_CELL_CONTENT, content);
        mAdapter = new GenericAdapter(data, ViewHolderType.DICTIONARY_CELL_VIEW_HOLDER);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchToolbar.setSuggestionText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), searchToolbar.getSearchUrl(), Toast.LENGTH_SHORT).show();
        loadNewWebsite();
    }

    private void loadNewWebsite(){
        if(searchToolbar.getSearchUrl() != null && !searchToolbar.getSearchUrl().equals("")) {
            mRecyclerView.setVisibility(View.GONE);
            showToolbarRightIcons();
            searchToolbar.setVisibility(View.GONE);
        }
    }
}
