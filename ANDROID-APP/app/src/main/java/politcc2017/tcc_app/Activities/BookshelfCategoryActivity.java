package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 08/02/2017.
 */

public class BookshelfCategoryActivity extends BaseActivity {
    private String title = "";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bookshelf);
        setupToolbar();
    }

    private void setupToolbar(){
        Intent intent = getIntent();
        if(intent != null) title = intent.getStringExtra("parameter");
        setActivityTitle(title);
        loadData();
    }

    private void loadData(){

    }

    protected void onResume() {
        setupToolbar();
        super.onResume();
    }
}
