package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        ActionBar bar = getSupportActionBar();
        if(bar != null) bar.hide();
        CustomButton loginButton = (CustomButton) findViewById(R.id.login_activity_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateLogin()) startHomeActivity();
            }
        });
        CustomButton signupButton = (CustomButton) findViewById(R.id.login_activity_signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignupActivity();
            }
        });
    }

    protected boolean validateLogin(){
        return true;
    }

    protected void startHomeActivity(){
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        finishAffinity();
        startActivity(intent);
    }

    protected void startSignupActivity(){
        Intent intent = new Intent(getBaseContext(), SignupActivity.class);
        startActivity(intent);
    }
}
