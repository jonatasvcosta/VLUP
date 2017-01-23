package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 23/01/2017.
 */

public class BookshelfActivity extends BaseActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bookshelf);
        setActivityTitle("Bookshelf");
    }
}
