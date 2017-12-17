package politcc2017.tcc_app.Activities.BeAPro;

/**
 * Created by Jonatas on 06/04/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerConstants;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

public class BeAProFragment extends Fragment{

    com.melnykov.fab.FloatingActionButton createClassFAB;
    private RecyclerView mRecyclerView;
    private GenericData data;

    public BeAProFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.be_a_pro_list_news, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.be_a_pro_new_list);

        createClassFAB = (com.melnykov.fab.FloatingActionButton) getActivity().findViewById(R.id.create_class_fab);
        createClassFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) getActivity()).startOrResumeActivity(BeAProCreateClassActivity.class);
            }
        });
        createClassFAB.attachToRecyclerView(mRecyclerView);
        PopulateRecyclerView();
        return v;
    }

    private void PopulateRecyclerView(){
        String locale = SharedPreferencesHelper.getString(SharedPreferencesHelper.LEARNING_LANGUAGE_LOCALE, getContext());
        ServerRequestHelper.getAuthorizedJSONArrayRequest(getContext(), ServerConstants.CLASSES_LIST_ENDPOINT+"?final_language="+locale, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response == null || response.length() == 0) return;
                data = new GenericData();
                ArrayList<String> titles = new ArrayList<>();
                ArrayList<String> descriptions = new ArrayList<>();
                ArrayList<String> categories = new ArrayList<>();
                ArrayList<String> votes = new ArrayList<>();
                ArrayList<String> movieURL = new ArrayList<>();

                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject miniclass = response.getJSONObject(i);
                        String title = miniclass.getString("title");
                        String vote = miniclass.getString("votes");
                        String url = miniclass.getString("video_url");
                        String tags = miniclass.getString("tags");
                        String content = miniclass.getString("content");
                        if(url.toLowerCase().equals("empty")) url = "";
                        titles.add(title);
                        descriptions.add(content);
                        categories.add(tags);
                        votes.add(vote);
                        movieURL.add(url);

                    } catch (JSONException e) {}
                }

                data.addStringsToAllCells(GenericData.CUSTOM_CARD_TITLE, titles);
                data.addStringsToAllCells(GenericData.CUSTOM_CARD_CONTENT, descriptions);
                data.addStringsToAllCells(GenericData.CUSTOM_CARD_CATEGORIES, categories);
                data.addStringsToAllCells(GenericData.CUSTOM_CARD_VOTES, votes);
                data.addStringsToAllCells(GenericData.CUSTOM_CARD_URL, movieURL);

                GenericAdapter mAdapter = new GenericAdapter(data, ViewHolderType.CUSTOM_CARD_VIEW_HOLDER);
                mAdapter.RegisterClickListener(new CellClickListener() {
                    @Override
                    public void onClick(View v, int position) {

                    }

                    @Override
                    public void onClick(ImageView v, String link) {

                    }

                    @Override
                    public void onClick(String message, int position) {
                        HandleCellClicks(message, position);
                    }

                    @Override
                    public void onLinkClick(String link) {

                    }

                    @Override
                    public void onClick(View view) {

                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

    }

    private void HandleCellClicks(String message, int position){
        Toast.makeText(getContext(), message+" : "+Integer.toString(position), Toast.LENGTH_SHORT).show(); //replace with proper actions
        if(message.equals("cardlayout")){
            startClassDetailActivity(position);
        }
    }

    private void startClassDetailActivity(int position){
        Intent i = new Intent(getContext(), BeAProClassDetailActivity.class);
        Hashtable content = data.getValue(position);
        if(content.containsKey(GenericData.CUSTOM_CARD_TITLE)) i.putExtra(GenericData.CUSTOM_CARD_TITLE, content.get(GenericData.CUSTOM_CARD_TITLE).toString());
        if(content.containsKey(GenericData.CUSTOM_CARD_CATEGORIES)) i.putExtra(GenericData.CUSTOM_CARD_CATEGORIES, content.get(GenericData.CUSTOM_CARD_CATEGORIES).toString());
        if(content.containsKey(GenericData.CUSTOM_CARD_CONTENT)) i.putExtra(GenericData.CUSTOM_CARD_CONTENT, content.get(GenericData.CUSTOM_CARD_CONTENT).toString());
        if(content.containsKey(GenericData.CUSTOM_CARD_VOTES)) i.putExtra(GenericData.CUSTOM_CARD_VOTES, content.get(GenericData.CUSTOM_CARD_VOTES).toString());
        if(content.containsKey(GenericData.CUSTOM_CARD_URL)) i.putExtra(GenericData.CUSTOM_CARD_URL, content.get(GenericData.CUSTOM_CARD_URL).toString());
        startActivity(i);
    }

    private String getValidatedInput(final String input){
        int end = input.length();
        int ini = input.indexOf("?");
        if(ini == -1){
            ini = end-1;
            while(ini > 0 && input.charAt(ini) != '/') ini--;
            if(ini == '/') ini++;
            return input.substring(ini, end);
        }
        while(ini < input.length() && input.charAt(ini) != '=') ini++;
        ini++;
        end = input.indexOf("&", ini);
        if(end == -1) end = input.length();
        return input.substring(ini, end);
    }

}
