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

import java.util.ArrayList;

import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

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
        GenericData data = getDataFromServer();
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

    private void HandleCellClicks(String message, int position){
        Toast.makeText(getContext(), message+" : "+Integer.toString(position), Toast.LENGTH_SHORT).show(); //replace with proper actions
        if(message.equals("cardlayout")){
            startClassDetailActivity(position);
        }
    }

    private void startClassDetailActivity(int position){
        Intent i = new Intent(getContext(), BeAProClassDetailActivity.class);
        i.putExtra(GenericData.CUSTOM_CARD_TITLE, data.getValue(position).get(GenericData.CUSTOM_CARD_TITLE).toString());
        i.putExtra(GenericData.CUSTOM_CARD_CATEGORIES, data.getValue(position).get(GenericData.CUSTOM_CARD_CATEGORIES).toString());
        i.putExtra(GenericData.CUSTOM_CARD_CONTENT, data.getValue(position).get(GenericData.CUSTOM_CARD_CONTENT).toString());
        i.putExtra(GenericData.CUSTOM_CARD_VOTES, data.getValue(position).get(GenericData.CUSTOM_CARD_VOTES).toString());
        i.putExtra(GenericData.CUSTOM_CARD_URL, data.getValue(position).get(GenericData.CUSTOM_CARD_URL).toString());
        startActivity(i);
    }

    private GenericData getDataFromServer(){
        data = new GenericData(); //replace with proper call to server
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Expressões Linguísticas");
        titles.add("Lista de filmes legais");
        titles.add("Nice song");
        titles.add("Learn german!");
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("<blockquote>Express&otilde;es&nbsp;Lingu&iacute;sticas:</blockquote></p><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul>");
        descriptions.add("<blockquote>Blah b</blockquote><u>List</u>:<br><br><ul><li>a</li></ul><ul><li>b</li></ul><ul><li>c</li></ul><ul><li>d</li></ul>");
        descriptions.add("Check this video!");
        descriptions.add("Check this video!");
        ArrayList<String> categories = new ArrayList<>();
        categories.add("expressões, linguagem popular");
        categories.add("filmes");
        categories.add("songs");
        categories.add("video classes");
        ArrayList<String> votes = new ArrayList<>();
        votes.add("#1221");
        votes.add("#3");
        votes.add("#998");
        votes.add("#7");
        ArrayList<String> movieURL = new ArrayList<>();
        movieURL.add("");
        movieURL.add("");
        movieURL.add(getValidatedInput("https://www.youtube.com/watch?v=w9j3-ghRjBs"));
        movieURL.add(getValidatedInput("https://www.youtube.com/watch?v=tPEE9ZwTmy0"));

        data.addStringsToAllCells(GenericData.CUSTOM_CARD_TITLE, titles);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CONTENT, descriptions);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CATEGORIES, categories);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_VOTES, votes);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_URL, movieURL);

        return data;
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