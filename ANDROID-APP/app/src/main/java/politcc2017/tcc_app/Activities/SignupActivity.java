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

import net.alexandroid.gps.GpsStatusDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomEditText;
import politcc2017.tcc_app.Components.CustomPicker;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.Entities.User;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.JSONHelper;
import politcc2017.tcc_app.Volley.ServerConstants;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class SignupActivity extends AppCompatActivity implements LocationListener , GpsStatusDetector.GpsStatusDetectorCallBack{
    CustomEditText nameEditText, emailEditText, passwordEditText, passwordConfirmationEditText;
    CustomPicker nativeLanguagePicker, learningLanguagePicker;
    CustomButton createAccountButton;
    User user;
    String passwordError, passwordDismatchError;
    private GpsStatusDetector mGpsStatusDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_signup);
        ActionBar bar = getSupportActionBar();
        loadViews();
        getGPSLocation();
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) processData();
            }
        });
        mGpsStatusDetector = new GpsStatusDetector(this);
        mGpsStatusDetector.checkGpsStatus();
    }

    private void processData() {
        final MaterialDialog loadingDialog = DialogHelper.ProgressDialog(SignupActivity.this, getResources().getString(R.string.dialog_loading_title), getResources().getString(R.string.dialog_loading_title));
        loadingDialog.show();
        user.email = emailEditText.getText();
        user.password = passwordEditText.getText();
        user.name = nameEditText.getText();
        getToken(loadingDialog);
    }

    protected void getToken(final MaterialDialog dialog){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ServerConstants.USERNAME_KEY, "root");
        params.put(ServerConstants.PASSWORD_KEY, "password");
        ServerRequestHelper.postJSONRequest(getApplicationContext(), ServerConstants.AUTHENTICATION_ENDPOINT, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.get(ServerConstants.TOKEN_KEY).toString();
                    WordContextDialog.SetToken(token);
                    Toast.makeText(getApplicationContext(), token, Toast.LENGTH_LONG).show();
                    postNewUser(dialog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postNewUser(final MaterialDialog loadingDialog){
        ArrayList<String> locales = ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.locale_array);
        HashMap<String, Object> params = new HashMap<>();
        params.put(ServerConstants.EMAIL_KEY, user.email);
        params.put(ServerConstants.PASSWORD_KEY, user.password);
        params.put(ServerConstants.FIRST_NAME_KEY, user.name);
        params.put(ServerConstants.LAST_NAME_KEY, user.name);
        params.put(ServerConstants.NAME_KEY, user.name);
        params.put(ServerConstants.NATIVE_LANGUAGE_KEY, locales.get(user.nativeLanguage));
        params.put(ServerConstants.LEARNING_LANGUAGE_KEY, locales.get(user.learningLanguage));
        params.put(ServerConstants.LATITUDE_KEY, user.latitude);
        params.put(ServerConstants.LONGITUDE_KEY, user.longitude);

        ServerRequestHelper.postAuthorizedJSONRequest(getApplicationContext(), ServerConstants.SIGNUP_ENDPOINT, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingDialog.dismiss();
                startLoginActivity();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
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
        nameEditText.validate();
        passwordEditText.validate();
        passwordConfirmationEditText.validate();
        return (!nameEditText.hasError() && !passwordEditText.hasError() && !passwordConfirmationEditText.hasError() && !nativeLanguagePicker.hasError() && !learningLanguagePicker.hasError());
    }

    private boolean passwordsMatch() {
        return passwordEditText.getText().equals(passwordConfirmationEditText.getText());
    }

    private void loadViews() {
        user = new User();
        passwordError = getResources().getString(R.string.signup_activity_password_field_error);
        passwordDismatchError = getResources().getString(R.string.signup_activity_password_fields_dismatch_error);
        nameEditText = (CustomEditText) findViewById(R.id.signup_activity_name);
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
                user.learningLanguage = which;
                return true;
            }
        }));

        nativeLanguagePicker.registerDialog(DialogHelper.ListSingleChoiceDialog(SignupActivity.this, getResources().getString(R.string.signup_activity_native_language_field), ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.languages_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {

            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                nativeLanguagePicker.setText(text.toString());
                user.nativeLanguage = which;
                if (which >= 0 && which < locales.length) changeAppLanguage(locales[which]);
                return true;
            }
        }));
    }

    private void getGPSLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignupActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
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
        SharedPreferencesHelper.addInt(getApplicationContext(), SharedPreferencesHelper.LEARNING_LANGUAGE_KEY, user.learningLanguage);
        SharedPreferencesHelper.addInt(getApplicationContext() , SharedPreferencesHelper.NATIVE_LANGUAGE_KEY, user.nativeLanguage);
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
        user.latitude = latitude;
        user.longitude = longitude;
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

    @Override
    public void onGpsSettingStatus(boolean enabled) {

    }

    @Override
    public void onGpsAlertCanceledByUser() {

    }
}
