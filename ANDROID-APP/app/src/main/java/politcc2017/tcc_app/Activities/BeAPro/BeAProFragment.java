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

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

public class BeAProFragment extends Fragment{
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
        startActivity(i);
    }

    private GenericData getDataFromServer(){
        data = new GenericData(); //replace with proper call to server
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Expressões Linguísticas");
        titles.add("Lista de filmes legais");
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("<p>Express&otilde;es&nbsp;Lingu&iacute;sticas:</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<ul>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n"+
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n" +
                "<li>Enfiar o p&eacute; na jaca - exagerar</li>\n" +
                "<li>Botar pra quebrar&nbsp;- exagerar</li>\n" +
                "<li>Badernar&nbsp;- fazer bagun&ccedil;a</li>\n" +
                "<li>Encher a cara - embriagar-se</li>\n" +
                "<li>Ir para o&nbsp;olho da rua - ser demitido</li>\n"+
                "</ul>");
        descriptions.add("<ul>\n" +
                "<li>Star wars</li>\n" +
                "<li>Senhor dos aneis</li>\n" +
                "<li>Matrix</li>\n" +
                "<li>Star wars</li>\n" +
                "<li>Senhor dos aneis</li>\n" +
                "<li>Matrix</li>\n" +
                "<li>Star wars</li>\n" +
                "<li>Senhor dos aneis</li>\n" +
                "<li>Matrix</li>\n" +
                "<li>Star wars</li>\n" +
                "<li>Senhor dos aneis</li>\n" +
                "<li>Matrix</li>\n" +
                "</ul>");
        ArrayList<String> categories = new ArrayList<>();
        categories.add("expressões, linguagem popular");
        categories.add("filmes");
        ArrayList<String> votes = new ArrayList<>();
        votes.add("#1221");
        votes.add("#3");

        data.addStringsToAllCells(GenericData.CUSTOM_CARD_TITLE, titles);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CONTENT, descriptions);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CATEGORIES, categories);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_VOTES, votes);

        return data;
    }

}