package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoOptions;

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
                startOrResumeActivity(TestComponentsActivity.class, true);
            }
        });
        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adview);
        adView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());
        AdRequest request = new AdRequest.Builder()
          .addTestDevice("379438B680AF5ECCD27F54638EE00DB8").build();
        adView.loadAd(request);

    }
}
