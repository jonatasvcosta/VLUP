package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 09/03/2016.
 */

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_settings);
        setActivityTitle(getResString(R.string.settings_activity_title));
        Button mButton = (Button) findViewById(R.id.settings_native_language_btn);
        final String[] locales = getResources().getStringArray(R.array.locale_array);
        String[] languages = getResources().getStringArray(R.array.languages_array);
        final ArrayList<String> languagesList = new ArrayList<>();
        for(int i = 0; i < languages.length; i++) languagesList.add(i, languages[i]);
        SharedPreferencesHelper.Initialize(getApplicationContext());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.ListSingleChoiceDialog(SettingsActivity.this, "Escolha um idioma", languagesList, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        SharedPreferencesHelper.addString(SharedPreferencesHelper.LOCALE_KEY, locales[which]);
                        changeAppLanguage(locales[which]);
                        return true;
                    }
                }).show();
            }
        });
    }
}
