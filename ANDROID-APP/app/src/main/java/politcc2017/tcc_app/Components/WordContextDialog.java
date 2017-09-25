package politcc2017.tcc_app.Components;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;

import java.util.ArrayList;

import politcc2017.tcc_app.Activities.Bookshelf.BookshelfActivity;
import politcc2017.tcc_app.Activities.MainActivitiesActivity;
import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

import static politcc2017.tcc_app.Components.Helpers.DialogHelper.CustomDialogBuilder;

/**
 * Created by Jônatas on 11/06/2017.
 */

public class WordContextDialog{
    private static String serverToken;
    public static final String CONTEXT_ADD_WORD = "add",CONTEXT_ADD_TEXT = "add_text", CONTEXT_TRANSLATE = "translate", CONTEXT_PRONOUNCE = "pronounce", CONTEXT_SYNONYM = "synonym", CONTEXT_ANTONYM = "antonym", CONTEXT_SIMILAR_WORDS = "similar_words";

    public static MaterialDialog WordContextDialog(Context context, String title, String translationString){
        return WordContextDialog(context, title, translationString, "", null);
    }

    public static void SetToken(String token){
        serverToken = token;
        ServerRequestHelper.SetToken(token);
    }

    public static MaterialDialog WordContextDialog(final Context context, final String title, String translationString, String phraseContext, final ContextMenuClickListener listener){
        MaterialDialog.Builder builder = CustomDialogBuilder(context, title, -1, null, "", "", null, null, false, null, null, true);
        MaterialDialog dialog =  builder.build();
        View view = dialog.getCustomView();
        final CustomTextView translation = (CustomTextView) view.findViewById(R.id.word_context_menu_translation_text);
        LinearLayout addContainer = (LinearLayout) view.findViewById(R.id.add_to_bookshelf_context_container);
        LinearLayout translationContainer = (LinearLayout) view.findViewById(R.id.translate_context_container);
        LinearLayout pronounceContainer = (LinearLayout) view.findViewById(R.id.pronounce_context_container);
        LinearLayout synonymContainer = (LinearLayout) view.findViewById(R.id.synonym_context_container);
        LinearLayout antonymContainer = (LinearLayout) view.findViewById(R.id.antonym_context_container);
        LinearLayout similarWordsContainer = (LinearLayout) view.findViewById(R.id.similar_words_context_container);

        addContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_ADD_WORD);
                Intent intent = new Intent(context, BookshelfActivity.class);
                intent.putExtra(CONTEXT_ADD_WORD, title);
                context.startActivity(intent);
            }
        });
        translationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_TRANSLATE);
                final String[] languages = context.getResources().getStringArray(R.array.languages_array);
                final ArrayList<String> languagesList = new ArrayList<>();
                for(int i = 0; i < languages.length; i++) languagesList.add(i, languages[i]);
                final ArrayList<String> locales = ResourcesHelper.getStringArrayAsArrayList(context, R.array.locale_array);
                DialogHelper.ListSingleChoiceDialog(context, context.getResources().getString(R.string.signup_activity_languages_field_error), languagesList, context.getResources().getString(R.string.dialog_confirm), context.getResources().getString(R.string.dialog_cancel), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        ServerRequestHelper.getWordTranslation(context, locales.get(which),
                                SharedPreferencesHelper.getString(SharedPreferencesHelper.LEARNING_LANGUAGE_LOCALE), title, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        translation.setText(response);
                                    }
                        });
                        return true;
                    }
                }).show();
            }
        });
        pronounceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_PRONOUNCE);
                TextToSpeechComponent.textToSpeech(context, title);
            }
        });
        synonymContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_SYNONYM);
                WordContextDialog.launchDialog(context, "Synonym of "+title);
            }
        });
        antonymContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_ANTONYM);
                WordContextDialog.launchDialog(context, "Antonym of "+title);
            }
        });

        similarWordsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_SIMILAR_WORDS);
                Intent intent = new Intent(context, MainActivitiesActivity.class);
                intent.putExtra(CONTEXT_SIMILAR_WORDS, title);
                context.startActivity(intent);
            }
        });

        translation.allowWordContextMenu(); //Check if this label should be recursively translatable
        translation.setText(translationString);
        if(phraseContext != null && phraseContext.length() > 0) {
            CustomTextView contextPhrase = (CustomTextView) view.findViewById(R.id.word_context_menu_phrase_context_text);
            contextPhrase.allowWordContextMenu(); //Check if this label should be recursively translatable
            contextPhrase.setText(phraseContext);
        }
        return dialog;
    }

    public static void launchDialog(final Context c, final String word){
        ServerRequestHelper.getWordTranslation(c, SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY),
                SharedPreferencesHelper.getString(SharedPreferencesHelper.LEARNING_LANGUAGE_LOCALE) ,word, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                WordContextDialog(c, word, response).show();
            }
        });
    }

    public static void launchDialog(final Context c, final String word, final String phraseContext, final ContextMenuClickListener listener){
        ServerRequestHelper.getWordTranslation(c, SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY),
                SharedPreferencesHelper.getString(SharedPreferencesHelper.LEARNING_LANGUAGE_LOCALE), word, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                WordContextDialog(c, word, response, phraseContext, listener).show();
            }
        });
    }
}
