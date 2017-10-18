package politcc2017.tcc_app.Activities.BeAPro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;

import mabbas007.tagsedittext.TagsEditText;
import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomEditText;
import politcc2017.tcc_app.Components.CustomHTMLEditText;
import politcc2017.tcc_app.Components.CustomPicker;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.CreatedMiniClasses;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 01/04/2017.
 */

public class BeAProCreateClassActivity extends BaseActivity {
    private CustomButton continueOrSaveClassButton;
    private LinearLayout contentContainer, movieContainer;
    private CustomHTMLEditText content;
    private TagsEditText tags;
    private CustomEditText classTitle;
    private CustomPicker classLanguage;
    private CustomPicker translationLanguage;
    private FloatingActionButton addMovieFAB;
    private int btnType = CONTINUE_BTN;
    private String HTMLContentText;
    private String movieUrl;
    private YouTubePlayerView moviePlayer;

    private static int GET_CLASS_CONTENT = 5, CONTINUE_BTN = 0, SAVE_BTN = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.be_a_pro_create_class);
        setActivityTitle(getResString(R.string.be_a_pro_activity_title));
        HTMLContentText = "";
        continueOrSaveClassButton = (CustomButton) findViewById(R.id.class_continue_or_save_button);
        contentContainer = (LinearLayout) findViewById(R.id.class_content_container);
        movieContainer = (LinearLayout) findViewById(R.id.create_class_movie_container);
        content = (CustomHTMLEditText) findViewById(R.id.class_text_content);
        tags = (TagsEditText) findViewById(R.id.be_a_pro_class_tags);
        classTitle = (CustomEditText) findViewById(R.id.be_a_pro_class_title_text);
        classLanguage = (CustomPicker) findViewById(R.id.be_a_pro_class_language_picker);
        translationLanguage = (CustomPicker) findViewById(R.id.be_a_pro_translation_language_picker);
        addMovieFAB = (FloatingActionButton) findViewById(R.id.add_movie_fab);
        moviePlayer = (YouTubePlayerView) findViewById(R.id.create_class_movie_view);

        content.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getApplicationContext()));
        tags.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getApplicationContext()));

        final MaterialDialog inputVideoURlDialog = DialogHelper.InputDialog(BeAProCreateClassActivity.this, "Enter a youtube movie URL:", new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, final CharSequence input) {
                movieUrl = input.toString();
                movieContainer.setVisibility(View.VISIBLE);
                moviePlayer.initialize(new AbstractYouTubeListener() {
                    @Override
                    public void onReady() {
                        String url = getValidatedInput(input.toString());
                        moviePlayer.loadVideo(url, 0);
                        moviePlayer.pauseVideo();
                    }
                }, true);
            }
        }, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel));

        continueOrSaveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        contentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGetContentActivity();
            }
        });
        classLanguage.registerDialog(DialogHelper.ListSingleChoiceDialog(BeAProCreateClassActivity.this, getResources().getString(R.string.be_a_pro_language_picker_text), ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.languages_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {

            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                classLanguage.setText(text.toString());
                return true;
            }
        }));
        translationLanguage.registerDialog(DialogHelper.ListSingleChoiceDialog(BeAProCreateClassActivity.this, getResources().getString(R.string.be_a_pro_translation_language_picker_text), ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), R.array.languages_array), getResources().getString(R.string.dialog_confirm), getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {

            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                translationLanguage.setText(text.toString());
                return true;
            }
        }));
        addMovieFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputVideoURlDialog.show();
            }
        });
    }

    private String getValidatedInput(final String input){
        int end = input.length();
        int ini = input.indexOf("?");
        if(ini == -1){
            ini = end-1;
            while(ini > 0 && input.charAt(ini) != '/') ini--;
            if(ini == '/') ini++;
            return input.substring(ini, end);
        }
        while(ini < input.length() && input.charAt(ini) != '=') ini++;
        ini++;
        end = input.indexOf("&", ini);
        if(end == -1) end = input.length();
        return input.substring(ini, end);
    }

    private void validateFields(){
        if(btnType == CONTINUE_BTN) startGetContentActivity();
        else {
            classTitle.validate();
            classLanguage.validate();
            scorePoints("+"+getScoringPoints(SqlHelper.RULE_CREATE_CLASS));
            String language = this.classLanguage.getText();
            String translationLanguage = this.translationLanguage.getText();
            String title = this.classTitle.getText();
            String[] tags = this.tags.getText().toString().split(" ");
            String content = this.content.toHtml();
            if(movieUrl != null && movieUrl.length() > 0){
                Inquiry.get(this).insert(CreatedMiniClasses.class).values(new CreatedMiniClasses[]{new CreatedMiniClasses(language, translationLanguage, title, tags, movieUrl, true)}).run();
            }
            else Inquiry.get(this).insert(CreatedMiniClasses.class).values(new CreatedMiniClasses[]{new CreatedMiniClasses(language, translationLanguage, title, tags, content)}).run();

        }
    }

    private void startGetContentActivity(){
        Intent i = new Intent(this, BeAProCreateClassTextActivity.class);
        if(HTMLContentText != null && HTMLContentText.length() > 0) i.putExtra("content", HTMLContentText);
        startActivityForResult(i, GET_CLASS_CONTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_CLASS_CONTENT) {
            btnType = SAVE_BTN;
            continueOrSaveClassButton.setText(getResString(R.string.save_class_button));
            if(resultCode == Activity.RESULT_OK){
                String classContent = data.getStringExtra("content");
                classContent = "<p>"+classContent+"</p>";
                HTMLContentText = classContent;
                content.fromHtml(classContent);
                contentContainer.setVisibility(View.VISIBLE);
            }
        }
    }
}
