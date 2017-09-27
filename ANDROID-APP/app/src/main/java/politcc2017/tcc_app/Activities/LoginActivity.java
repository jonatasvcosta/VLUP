package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomEditText;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerConstants;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class LoginActivity extends AppCompatActivity {
    private CustomEditText emailEditText, passwordEditText;
    private MaterialDialog errorDialog;
    private CheckBox mCheckBox;
    private LinearLayout checkboxContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        ActionBar bar = getSupportActionBar();
        if(bar != null) bar.hide();
        CustomButton loginButton = (CustomButton) findViewById(R.id.login_activity_login_button);
        emailEditText = (CustomEditText) findViewById(R.id.login_activity_email);
        passwordEditText = (CustomEditText) findViewById(R.id.login_activity_password);
        mCheckBox = (CheckBox) findViewById(R.id.login_activity_remain_conected_checkbox);
        checkboxContainer = (LinearLayout) findViewById(R.id.login_activity_checkbox_container);

        if(SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.AUTOMATIC_AUTHENTICATION_KEY, getApplicationContext())) startHomeActivity(); //the user was previously authenticated

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra(SharedPreferencesHelper.EMAIL_KEY) != null) emailEditText.setText(intent.getStringExtra(SharedPreferencesHelper.EMAIL_KEY));
            if(intent.getStringExtra(SharedPreferencesHelper.PASSWORD_KEY) != null) passwordEditText.setText(intent.getStringExtra(SharedPreferencesHelper.PASSWORD_KEY));
        }
        errorDialog = DialogHelper.ErrorDialog(LoginActivity.this, R.drawable.ic_wrong, getResources().getString(R.string.login_activity_invalid_user_error), getResources().getString(R.string.dialog_confirm));
        SharedPreferencesHelper.Initialize(getApplicationContext());
        if(SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.AUTOMATIC_AUTHENTICATION_KEY, getApplicationContext())) startHomeActivity();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });
        checkboxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckBox.setChecked(!mCheckBox.isChecked());
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

    protected void validateLogin(){
        MaterialDialog dialog = DialogHelper.ProgressDialog(LoginActivity.this, getResources().getString(R.string.dialog_loading_title));
        dialog.show();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ServerConstants.USERNAME_KEY, emailEditText.getText());
        params.put(ServerConstants.PASSWORD_KEY, passwordEditText.getText());
        ServerRequestHelper.postJSONRequest(getApplicationContext(), ServerConstants.AUTHENTICATION_ENDPOINT, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(mCheckBox.isChecked()) SharedPreferencesHelper.addBoolean(SharedPreferencesHelper.AUTOMATIC_AUTHENTICATION_KEY, true);
                else SharedPreferencesHelper.addBoolean(SharedPreferencesHelper.AUTOMATIC_AUTHENTICATION_KEY, false);
                startHomeActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorDialog.show();
            }
        });
        dialog.dismiss();
    }

    protected void startHomeActivity(){
        Intent intent = new Intent(getBaseContext(), MainActivitiesActivity.class);
        finishAffinity();
        startActivity(intent);
    }

    protected void startSignupActivity(){
        Intent intent = new Intent(getBaseContext(), SignupActivity.class);
        startActivity(intent);
    }
}
