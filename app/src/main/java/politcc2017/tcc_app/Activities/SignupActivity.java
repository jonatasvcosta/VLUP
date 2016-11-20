package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;

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

public class SignupActivity extends AppCompatActivity {
    CustomEditText nameEditText, ageEditText, emailEditText, motherLanguageEditText, countryEditText, cityEditText, neighborhoodEditText, passwordEditText, passwordConfirmationEditText;
    CustomPicker genderPicker, otherLanguagesPicker;
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
                if(validateFields()) processData();
            }
        });
    }

    private void processData(){
        final MaterialDialog loadingDialog = DialogHelper.ProgressDialog(SignupActivity.this, getResources().getString(R.string.dialog_loading_title), getResources().getString(R.string.dialog_loading_title));
        loadingDialog.show();
        user.name = nameEditText.getText();
        user.age = ageEditText.getText();
        user.city = cityEditText.getText();
        user.country = countryEditText.getText();
        user.email = emailEditText.getText();
        user.gender = genderPicker.getText();
        user.motherLanguage = motherLanguageEditText.getText();
        user.neighborhood = neighborhoodEditText.getText();
        user.password = passwordEditText.getText();
        SaveUserOnSharedPreferences();
        ServerRequestHelper.postString(getApplicationContext(), ServerConstants.SIGNUP_POST_URL, JSONHelper.objectToJSON(user), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingDialog.dismiss();
                startLoginActivity();
            }
        });
    }

    private void SaveUserOnSharedPreferences(){
        SharedPreferencesHelper.addString(SharedPreferencesHelper.NAME_KEY ,user.name);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.AGE_KEY ,user.age);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.CITY_KEY ,user.city);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.COUNTRY_KEY ,user.country);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.EMAIL_KEY ,user.email);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.GENDER_KEY ,user.gender);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.MOTHERLANGUAGE_KEY ,user.motherLanguage);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.NEIGHBORHOOD_KEY ,user.neighborhood);
        SharedPreferencesHelper.addString(SharedPreferencesHelper.PASSWORD_KEY ,user.password);
        SharedPreferencesHelper.addStringArray(SharedPreferencesHelper.LANGUAGES_KEY ,user.languages);
    }

    private boolean validateFields(){
        if(!passwordsMatch()){
            passwordConfirmationEditText.setErrorMessage(passwordDismatchError);
            passwordConfirmationEditText.hasForcedError = true;
            passwordConfirmationEditText.validate();
            return false;
        }
        passwordConfirmationEditText.hasForcedError = false;
        passwordConfirmationEditText.setErrorMessage(passwordError);
        nameEditText.validate();
        ageEditText.validate();
        emailEditText.validate();
        motherLanguageEditText.validate();
        countryEditText.validate();
        cityEditText.validate();
        neighborhoodEditText.validate();
        passwordEditText.validate();
        passwordConfirmationEditText.validate();
        genderPicker.validate();
        otherLanguagesPicker.validate();
        return (!nameEditText.hasError()&& !ageEditText.hasError()&& !emailEditText.hasError()&& !motherLanguageEditText.hasError()&& !countryEditText.hasError()&& !cityEditText.hasError()
                && !neighborhoodEditText.hasError()&& !passwordEditText.hasError()&& !passwordConfirmationEditText.hasError()&& !genderPicker.hasError()&& !otherLanguagesPicker.hasError());
    }

    private boolean passwordsMatch(){
        return passwordEditText.getText().equals(passwordConfirmationEditText.getText());
    }

    private void loadViews(){
        user = new User();
        passwordError = getResources().getString(R.string.signup_activity_password_field_error);
        passwordDismatchError = getResources().getString(R.string.signup_activity_password_fields_dismatch_error);
        nameEditText = (CustomEditText) findViewById(R.id.signup_activity_name);
        ageEditText = (CustomEditText) findViewById(R.id.signup_activity_age);
        emailEditText = (CustomEditText) findViewById(R.id.signup_activity_email);
        motherLanguageEditText = (CustomEditText) findViewById(R.id.signup_activity_mother_language);
        countryEditText = (CustomEditText) findViewById(R.id.signup_activity_country);
        cityEditText = (CustomEditText) findViewById(R.id.signup_activity_city);
        neighborhoodEditText = (CustomEditText) findViewById(R.id.signup_activity_neighborhood);
        passwordEditText = (CustomEditText) findViewById(R.id.signup_activity_password);
        passwordConfirmationEditText = (CustomEditText) findViewById(R.id.signup_activity_password_confirm);
        genderPicker = (CustomPicker) findViewById(R.id.signup_activity_gender);
        otherLanguagesPicker = (CustomPicker) findViewById(R.id.signup_activity_other_languages);
        createAccountButton = (CustomButton) findViewById(R.id.signup_activity_create_account_button);

        genderPicker.registerDialog(DialogHelper.ListSingleChoiceDialog(SignupActivity.this, getResources().getString(R.string.signup_activity_gender_field) , ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.gender_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                genderPicker.setText((String) text);
                return true;
            }
        }));

        otherLanguagesPicker.registerDialog(DialogHelper.ListMultiChoiceDialog(SignupActivity.this, getResources().getString(R.string.signup_activity_languages_field) , ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.languages_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackMultiChoice() {

            @Override
            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                String textString = "";
                if(text.length > 0) user.languages = new String[text.length];
                for(int i = 0; i < text.length; i++) user.languages[i] = (String) text[i];
                if(text.length == 1) textString = (String) text[0];
                else if(text.length > 1){
                    for(int i = 0; i < text.length - 1; i++){
                        textString+=text[i];
                        if(i < text.length - 2) textString += ", ";
                    }
                    textString += " e " + (String) text[text.length-1];
                }
                otherLanguagesPicker.setText(textString);
                return false;
            }
        }));
    }

    protected void startLoginActivity(){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.putExtra(SharedPreferencesHelper.EMAIL_KEY, emailEditText.getText());
        intent.putExtra(SharedPreferencesHelper.PASSWORD_KEY, passwordEditText.getText());
        finishAffinity();
        startActivity(intent);
    }
}
