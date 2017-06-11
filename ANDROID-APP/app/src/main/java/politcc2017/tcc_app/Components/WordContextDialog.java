package politcc2017.tcc_app.Components;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.R;

import static politcc2017.tcc_app.Components.Helpers.DialogHelper.CustomDialogBuilder;

/**
 * Created by Jônatas on 11/06/2017.
 */

public class WordContextDialog {
    public static final String CONTEXT_ADD_WORD = "add", CONTEXT_TRANSLATE = "translate", CONTEXT_PRONOUNCE = "pronounce", CONTEXT_SYNONYM = "synonym", CONTEXT_ANTONYM = "antonym", CONTEXT_SIMILAR_WORDS = "similar_words";

    public static MaterialDialog WordContextDialog(Context context, String title, String translationString){
        return WordContextDialog(context, title, translationString, "", null);
    }

    public static MaterialDialog WordContextDialog(Context context, String title, String translationString, String phraseContext, final ContextMenuClickListener listener){
        MaterialDialog.Builder builder = CustomDialogBuilder(context, title, -1, null, "", "", null, null, false, null, null, true);
        MaterialDialog dialog =  builder.build();
        View view = dialog.getCustomView();
        CustomTextView translation = (CustomTextView) view.findViewById(R.id.word_context_menu_translation_text);
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
            }
        });
        translationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_TRANSLATE);
            }
        });
        pronounceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_PRONOUNCE);
            }
        });
        synonymContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_SYNONYM);
            }
        });
        antonymContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_ANTONYM);
            }
        });
        similarWordsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view, CONTEXT_SIMILAR_WORDS);
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

    public static void launchDialog(Context c, String word){
        String translationWord = "Translation of "+word;
        WordContextDialog(c, word, translationWord).show();
    }

    public static void launchDialog(Context c, String word, String translation, String phraseContext, final ContextMenuClickListener listener){
        WordContextDialog(c, word, translation, phraseContext, listener).show();
    }

    public static MaterialDialog createDialog(Context c, String word, String translation, String phraseContext, final ContextMenuClickListener listener){
        return WordContextDialog(c, word, translation, phraseContext, listener);
    }
}
