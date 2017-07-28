package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/07/2017.
 */

public class RankingActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private GenericData mData;
    private GenericAdapter mAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_ranking);
        setActivityTitle(getResString(R.string.ranking_activity_title));
        recyclerView = (RecyclerView) findViewById(R.id.ranking_activity_list);
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setupRecyclerView(){
        loadData();
        mAdapter = new GenericAdapter(mData, ViewHolderType.RANKING_VIEW_HOLDER, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData(){
        mData = new GenericData();
        ArrayList<String> users = new ArrayList<>();
        users.add("Jaiminho Offenbach");
        users.add("Jurandyr Ross");
        users.add("Pete Rose");
        users.add("Karl Friedrich van Klopstock");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        users.add("Pete Rose");
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(2121312);
        scores.add(123456);
        scores.add(1234);
        scores.add(358);
        ArrayList<Integer> medals = new ArrayList<>();
        medals.add(1);
        medals.add(2);
        mData.addStringsToAllCells(GenericData.USER_NAME, users);
        mData.addIntegersToAllCells(GenericData.USER_SCORE, scores);
        mData.setSpecialTypeCells(medals, "medal");
    }
}