package politcc2017.tcc_app.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomCard;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 09/03/2016.
 */

public class SettingsActivity extends BaseActivity {
    private CustomCard learningStatsCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_settings);
        setActivityTitle(getResString(R.string.settings_activity_title));
        Button nativeLanguageBtn = (Button) findViewById(R.id.settings_native_language_btn);
        Button learningLanguageBtn = (Button) findViewById(R.id.settings_learning_language_btn);
        loadLearningStats();

        final String[] locales = getResources().getStringArray(R.array.locale_array);
        final String[] languages = getResources().getStringArray(R.array.languages_array);
        final ArrayList<String> languagesList = new ArrayList<>();
        for(int i = 0; i < languages.length; i++) languagesList.add(i, languages[i]);

        SharedPreferencesHelper.Initialize(getApplicationContext());
        nativeLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.ListSingleChoiceDialog(SettingsActivity.this, getResString(R.string.signup_activity_native_language_field), languagesList, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        SharedPreferencesHelper.addString(getApplicationContext(), SharedPreferencesHelper.LOCALE_KEY, locales[which]);
                        changeAppLanguage(locales[which]);
                        return true;
                    }
                }).show();
            }
        });

        learningLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLearningLanguageChoice(SettingsActivity.this);
            }
        });
    }
    private void loadLearningStats() {
        learningStatsCard = (CustomCard) findViewById(R.id.language_learning_stats);
        final String[] languages = getResources().getStringArray(R.array.languages_array);
        learningStatsCard.setTitle(languages[learningLanguage]);
        learningStatsCard.setRadius(getResources().getDimension(R.dimen.margin_extra_large));
        learningStatsCard.setCardColor(getResources().getColor(R.color.card_beige));
        learningStatsCard.hideBottomContainer();
        learningStatsCard.setVotes("#1054 points");
        ArrayList<Integer> flagIcons = ResourcesHelper.getIntArrayAsArrayList(getBaseContext(), R.array.languages_icons);
        learningStatsCard.setTopRightIcon(flagIcons.get(learningLanguage));
        learningStatsCard.setContent("<blockquote>Level 3 - Hookie Pre-Intermediate</blockquote><ul><li>104 words added to bookshelf</li></ul><ul><li>800 words translated</li></ul><ul><li>50 texts read</li>");
    }

    protected void handleLearningLanguageChange(){
        loadLearningStats();
    }
}
