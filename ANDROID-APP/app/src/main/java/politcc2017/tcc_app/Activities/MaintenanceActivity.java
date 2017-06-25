package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class MaintenanceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_maintenance);
        setActivityTitle("Maintenance Activity");
        Button mButton = (Button) findViewById(R.id.maintenance_back_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startOrResumeActivity(HomeActivity.class, true);
                promptSpeechInput();
            }
        });
    }
}
