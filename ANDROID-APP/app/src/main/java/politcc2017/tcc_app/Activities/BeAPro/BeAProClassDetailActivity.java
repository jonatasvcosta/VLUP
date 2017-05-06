package politcc2017.tcc_app.Activities.BeAPro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Components.CustomCard;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 06/05/2017.
 */

public class BeAProClassDetailActivity extends BaseActivity{
    private CustomCard Class;
    private String classContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.be_a_pro_class_detail);

        Class = (CustomCard) findViewById(R.id.be_a_pro_class_detail);
        Class.setUnlimitedLines();
        Class.setContentMarkable();
        Intent i = getIntent();
        if(i != null){
            classContent = i.getStringExtra(GenericData.CUSTOM_CARD_CONTENT);
            Class.setTitle(i.getStringExtra(GenericData.CUSTOM_CARD_TITLE));
            Class.setVotes(i.getStringExtra(GenericData.CUSTOM_CARD_VOTES));
            Class.setCategory(i.getStringExtra(GenericData.CUSTOM_CARD_CATEGORIES));
            Class.setContent(classContent);
            setActivityTitle(i.getStringExtra(GenericData.CUSTOM_CARD_TITLE));
        }
        Class.setEditIconClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, int position) {
                Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
    }

}
