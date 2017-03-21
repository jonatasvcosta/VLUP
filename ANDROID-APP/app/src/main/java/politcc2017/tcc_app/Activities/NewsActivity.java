package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 15/03/2017.
 */

public class NewsActivity extends BaseActivity {
    CustomTextView mText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news);
        mText = (CustomTextView) findViewById(R.id.activity_news_text);
        mText.allowWordContextMenu();
    }
}
