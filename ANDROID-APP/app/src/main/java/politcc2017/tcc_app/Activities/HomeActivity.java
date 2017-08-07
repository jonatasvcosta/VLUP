package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import politcc2017.tcc_app.Activities.BeAPro.BeAProClassDetailActivity;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

import static java.security.AccessController.getContext;

/**
 * Created by Jonatas on 06/08/2017.
 */

public class HomeActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private GenericData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.home_cards_list);
        PopulateRecyclerView();
        setActivityTitle(getResString(R.string.app_name));
    }

    private void PopulateRecyclerView(){
        GenericData data = getDataFromServer();
        GenericAdapter mAdapter = new GenericAdapter(data, ViewHolderType.HOME_CARD_VIEW_HOLDER, getApplicationContext());
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void HandleCellClicks(String message, int position){
        Toast.makeText(getApplicationContext(), message+" : "+Integer.toString(position), Toast.LENGTH_SHORT).show(); //replace with proper actions
        if(message.equals("cardlayout")){
        //    startClassDetailActivity(position);
        }
    }

    private void startClassDetailActivity(int position){
        /*Intent i = new Intent(getContext(), BeAProClassDetailActivity.class);
        i.putExtra(GenericData.CUSTOM_CARD_TITLE, data.getValue(position).get(GenericData.CUSTOM_CARD_TITLE).toString());
        i.putExtra(GenericData.CUSTOM_CARD_CATEGORIES, data.getValue(position).get(GenericData.CUSTOM_CARD_CATEGORIES).toString());
        i.putExtra(GenericData.CUSTOM_CARD_CONTENT, data.getValue(position).get(GenericData.CUSTOM_CARD_CONTENT).toString());
        i.putExtra(GenericData.CUSTOM_CARD_VOTES, data.getValue(position).get(GenericData.CUSTOM_CARD_VOTES).toString());
        i.putExtra(GenericData.CUSTOM_CARD_URL, data.getValue(position).get(GenericData.CUSTOM_CARD_URL).toString());
        startActivity(i);*/
    }

    private GenericData getDataFromServer(){
        data = new GenericData(); //replace with proper call to server
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Trump to North Korea: Be very, very nervous");
        titles.add("Expressões Linguísticas");
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.add("<b> President Donald Trump has warned North Korea it should be \"very, very nervous\" if it does anything to the US</b>.\n" +

                "<p>He said the regime would be in trouble \"like few nations have ever been\" if they do not \"get their act together</p>\".\n" +

                "<p>His comments came after Pyongyang announced it had a plan to fire four missiles near the US territory of Guam.</p");
        descriptions.add("<blockquote>Express&otilde;es&nbsp;Lingu&iacute;sticas:</blockquote></p><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul><ul><li>Enfiar o p&eacute; na jaca - exagerar</li></ul><ul><li>Botar pra quebrar&nbsp;- exagerar</li></ul><ul><li>Badernar&nbsp;- fazer bagun&ccedil;a</li></ul><ul><li>Encher a cara - embriagar-se</li></ul><ul><li>Ir para o&nbsp;olho da rua - ser demitido</li></ul>");
        ArrayList<String> categories = new ArrayList<>();
        categories.add("conflito, política");
        categories.add("expressões, linguagem popular");

        ArrayList<String> votes = new ArrayList<>();
        votes.add("#15211");
        votes.add("#1221");

        ArrayList<String> cardType = new ArrayList<>();
        cardType.add(GenericData.NEWS);
        cardType.add(GenericData.MINI_CLASS);

        data.addStringsToAllCells(GenericData.CUSTOM_CARD_TITLE, titles);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CONTENT, descriptions);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CATEGORIES, categories);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_VOTES, votes);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_TYPE, cardType);

        return data;
    }
}
