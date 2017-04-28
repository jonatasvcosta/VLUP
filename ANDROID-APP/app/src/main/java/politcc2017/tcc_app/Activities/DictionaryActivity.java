package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

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
        super.setContentView(R.layout.activity_navigate);
        SCROL_DY = getResources().getDimension(R.dimen.margin_extra_large);
        setActivityTitle(getResources().getString(R.string.navigate_activity_title));
        mRecyclerView = (RecyclerView) findViewById(R.id.dictionary_activity_list);
        searchToolbar = (CustomSearchToolbar) findViewById(R.id.dictionary_activity_search_toolbar);
        searchToolbar.registerSearchListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        searchToolbar.registerRecyclerViewScrollListener(mRecyclerView, displayMetrics.heightPixels);
        SetSuggestionListData();
    }

    private void SetSuggestionListData(){
        GenericData data = new GenericData();

        //These fake data will be replaced by data from server:
        ArrayList<String> linkArray = new ArrayList<>();
        ArrayList<String> titleArray = new ArrayList<>();
        ArrayList<String> descriptionArray = new ArrayList<>();
        ArrayList<String> imageArray = new ArrayList<>();

        linkArray.add("http://www.bbc.com/news");
        titleArray.add("BBC");
        descriptionArray.add("A British Broadcasting Corporation é uma emissora pública de rádio e televisão do Reino Unido fundada em 1922. Possui uma boa reputação nacional e internacional. Por vezes, é chamada afetuosamente pelos ingleses como Beeb, The Corporation ou Auntie.");
        imageArray.add("https://www.bbc.co.uk/news/special/2015/newsspec_11063/brasil_1024x576.png");
        linkArray.add("http://www.dw.com/en/top-stories/s-9097");
        titleArray.add("Deutsche Welle");
        descriptionArray.add("Deutsche Welle é uma empresa de radiodifusão da Alemanha, com sedes em Bonn e Berlim, que transmite para o exterior programas de rádio, além de oferecer uma programação televisiva e um amplo portal de conteúdo online em 30 línguas.");
        imageArray.add("http://2.bp.blogspot.com/-oK-e8hUeW1M/Tyfi-bDXxiI/AAAAAAAAD_o/h5Msp1u-tLs/s1600/Deutsche+Welle+logo.png");
        linkArray.add("https://www.nytimes.com/");
        titleArray.add("The New York Times");
        descriptionArray.add("The New York Times: Find breaking news, multimedia, reviews & opinion on Washington, business, sports, movies, travel, books, jobs, education, real estate...");
        imageArray.add("http://vignette4.wikia.nocookie.net/turtledove/images/9/9a/New_York_Times_logo_500.gif/revision/latest?cb=20151126221853");
        linkArray.add("https://www.bloomberg.com/");
        titleArray.add("Bloomberg");
        descriptionArray.add("Bloomberg delivers business and markets news, data, analysis, and video to the world, featuring stories from Businessweek and Bloomberg News.");
        imageArray.add("http://www.coinbuzz.com/wp-content/uploads/2014/05/Bloomberg.png");

        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_LINK, linkArray);
        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_TITLE, titleArray);
        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_DESCRIPTION, descriptionArray);
        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_IMAGE, imageArray);

        //end of fake data

        mAdapter = new GenericAdapter(data, ViewHolderType.BROWSER_SUGGESTION_ITEM_VIEW_HOLDER);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.RegisterClickListener(new CellClickListener() {

            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClick(final ImageView v, String link) {
                ServerRequestHelper.imageAbsoluteURLRequest(getApplicationContext(), link, new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        v.setImageBitmap(response.getBitmap());
                    }
                });
            }

            @Override
            public void onClick(String message, int position) {

            }

            @Override
            public void onLinkClick(String link) {
                searchToolbar.setSearchUrl(link);
                loadNewWebsite();
            }

            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
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
