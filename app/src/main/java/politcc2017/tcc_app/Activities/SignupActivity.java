package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomEditText;
import politcc2017.tcc_app.Components.CustomPicker;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class SignupActivity extends AppCompatActivity {
    CustomEditText nameEditText, ageEditText, emailEditText, motherLanguageEditText, countryEditText, cityEditText, neighborhoodEditText, passwordEditText, passwordConfirmationEditText;
    CustomPicker genderPicker, otherLanguagesPicker;
    CustomButton createAccountButton;
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
                validateFields();
            }
        });
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
        return (nameEditText.hasError()&& ageEditText.hasError()&& emailEditText.hasError()&& motherLanguageEditText.hasError()&& countryEditText.hasError()&& cityEditText.hasError()
                && neighborhoodEditText.hasError()&& passwordEditText.hasError()&& passwordConfirmationEditText.hasError()&& genderPicker.hasError()&& otherLanguagesPicker.hasError());
    }

    private boolean passwordsMatch(){
        return passwordEditText.getText().equals(passwordConfirmationEditText.getText());
    }

    private void loadViews(){
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
    }

    protected void startHomeActivity(){
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}
