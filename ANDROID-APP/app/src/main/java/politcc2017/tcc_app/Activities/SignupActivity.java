package politcc2017.tcc_app.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;

import java.util.Locale;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomEditText;
import politcc2017.tcc_app.Components.CustomPicker;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Entities.User;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.JSONHelper;
import politcc2017.tcc_app.Volley.ServerConstants;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class SignupActivity extends AppCompatActivity implements LocationListener {
    CustomEditText emailEditText, passwordEditText, passwordConfirmationEditText;
    CustomPicker nativeLanguagePicker, learningLanguagePicker;
    CustomButton createAccountButton;
    User user;
    String passwordError, passwordDismatchError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_signup);
        ActionBar bar = getSupportActionBar();
        loadViews();
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) processData();
            }
        });
    }

    private void processData() {
        final MaterialDialog loadingDialog = DialogHelper.ProgressDialog(SignupActivity.this, getResources().getString(R.string.dialog_loading_title), getResources().getString(R.string.dialog_loading_title));
        loadingDialog.show();
        user.email = emailEditText.getText();
        user.password = passwordEditText.getText();
        ServerRequestHelper.postString(getApplicationContext(), ServerConstants.SIGNUP_POST_URL, JSONHelper.objectToJSON(user), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingDialog.dismiss();
                startLoginActivity();
            }
        });
    }

    private boolean validateFields() {
        learningLanguagePicker.validate();
        nativeLanguagePicker.validate();
        if (!passwordsMatch()) {
            passwordConfirmationEditText.setErrorMessage(passwordDismatchError);
            passwordConfirmationEditText.hasForcedError = true;
            passwordConfirmationEditText.validate();
            return false;
        }
        passwordConfirmationEditText.hasForcedError = false;
        passwordConfirmationEditText.setErrorMessage(passwordError);
        emailEditText.validate();
        passwordEditText.validate();
        passwordConfirmationEditText.validate();
        return (!passwordEditText.hasError() && !passwordConfirmationEditText.hasError() && !nativeLanguagePicker.hasError() && !learningLanguagePicker.hasError());
    }

    private boolean passwordsMatch() {
        return passwordEditText.getText().equals(passwordConfirmationEditText.getText());
    }

    private void loadViews() {
        user = new User();
        passwordError = getResources().getString(R.string.signup_activity_password_field_error);
        passwordDismatchError = getResources().getString(R.string.signup_activity_password_fields_dismatch_error);
        emailEditText = (CustomEditText) findViewById(R.id.signup_activity_email);
        passwordEditText = (CustomEditText) findViewById(R.id.signup_activity_password);
        passwordConfirmationEditText = (CustomEditText) findViewById(R.id.signup_activity_password_confirm);
        learningLanguagePicker = (CustomPicker) findViewById(R.id.signup_activity_other_languages);
        nativeLanguagePicker = (CustomPicker) findViewById(R.id.signup_activity_native_language);
        createAccountButton = (CustomButton) findViewById(R.id.signup_activity_create_account_button);
        final String[] locales = getResources().getStringArray(R.array.locale_array);
        learningLanguagePicker.registerDialog(DialogHelper.ListSingleChoiceDialog(SignupActivity.this, getResources().getString(R.string.signup_activity_languages_field), ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.languages_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {

            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                learningLanguagePicker.setText(text.toString());
                user.learningLanguage = text.toString();
                return true;
            }
        }));

        nativeLanguagePicker.registerDialog(DialogHelper.ListSingleChoiceDialog(SignupActivity.this, getResources().getString(R.string.signup_activity_native_language_field), ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.languages_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {

            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                nativeLanguagePicker.setText(text.toString());
                user.nativeLanguage = text.toString();
                if (which >= 0 && which < locales.length) changeAppLanguage(locales[which]);
                return true;
            }
        }));

        getGPSLocation();
    }

    private void getGPSLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignupActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    protected void startLoginActivity(){
        SaveLanguagesChoice();
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.putExtra(SharedPreferencesHelper.EMAIL_KEY, emailEditText.getText());
        intent.putExtra(SharedPreferencesHelper.PASSWORD_KEY, passwordEditText.getText());
        finishAffinity();
        startActivity(intent);
    }

    public void SaveLanguagesChoice(){
        SharedPreferencesHelper.addString(SharedPreferencesHelper.LEARNING_LANGUAGE_KEY, user.learningLanguage);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.NATIVE_LANGUAGE_KEY, user.nativeLanguage);
    }

    public void changeAppLanguage(String loc){
        Locale locale = new Locale(loc);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = (double) (location.getLatitude());
        double longitude = (double) (location.getLongitude());

        Toast.makeText(getApplicationContext(), Double.toString(latitude) + " - "+Double.toString(longitude), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
