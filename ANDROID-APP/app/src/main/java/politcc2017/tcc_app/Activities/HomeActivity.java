package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 06/08/2017.
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test);
        setActivityTitle(getResString(R.string.app_name));
    }
}
