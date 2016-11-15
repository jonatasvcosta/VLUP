package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_signup);
        ActionBar bar = getSupportActionBar();
        //if(bar != null) bar.hide();
    }

    protected void startHomeActivity(){
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}
