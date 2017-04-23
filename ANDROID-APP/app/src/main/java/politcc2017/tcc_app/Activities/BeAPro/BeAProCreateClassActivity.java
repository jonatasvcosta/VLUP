package politcc2017.tcc_app.Activities.BeAPro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import io.github.mthli.knife.KnifeText;
import mabbas007.tagsedittext.TagsEditText;
import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomEditText;
import politcc2017.tcc_app.Components.CustomPicker;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 01/04/2017.
 */

public class BeAProCreateClassActivity extends BaseActivity {
    private CustomButton continueOrSaveClassButton;
    private LinearLayout contentContainer;
    private KnifeText content;
    private TagsEditText tags;
    private CustomEditText classTitle;
    private CustomPicker classLanguage;
    private CustomPicker translationLanguage;
    private int btnType = CONTINUE_BTN;
    private static int GET_CLASS_CONTENT = 5, CONTINUE_BTN = 0, SAVE_BTN = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.be_a_pro_create_class);
        setActivityTitle(getResString(R.string.be_a_pro_activity_title));
        continueOrSaveClassButton = (CustomButton) findViewById(R.id.class_continue_or_save_button);
        contentContainer = (LinearLayout) findViewById(R.id.class_content_container);
        content = (KnifeText) findViewById(R.id.class_text_content);
        tags = (TagsEditText) findViewById(R.id.be_a_pro_class_tags);
        classTitle = (CustomEditText) findViewById(R.id.be_a_pro_class_title_text);
        classLanguage = (CustomPicker) findViewById(R.id.be_a_pro_class_language_picker);
        translationLanguage = (CustomPicker) findViewById(R.id.be_a_pro_translation_language_picker);
        content.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getApplicationContext()));
        tags.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getApplicationContext()));
        continueOrSaveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
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
    }

    private void validateFields(){
        if(btnType == CONTINUE_BTN) startGetContentActivity();
        else {
            classTitle.validate();
            classLanguage.validate();
        }
    }

    private void startGetContentActivity(){
        Intent i = new Intent(this, BeAProCreateClassTextActivity.class);
        if(content.toHtml() != null && content.toHtml().length() > 0) i.putExtra("content", content.toHtml());
        startActivityForResult(i, GET_CLASS_CONTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_CLASS_CONTENT) {
            btnType = SAVE_BTN;
            continueOrSaveClassButton.setText(getResString(R.string.save_class_button));
            if(resultCode == Activity.RESULT_OK){
                String classContent = data.getStringExtra("content");
                content.fromHtml(classContent);
                contentContainer.setVisibility(View.VISIBLE);
            }
        }
    }
}
