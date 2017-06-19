package politcc2017.tcc_app.Components;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Locale;

import politcc2017.tcc_app.Activities.Bookshelf.BookshelfActivity;
import politcc2017.tcc_app.Activities.VocabularyActivity;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

import static politcc2017.tcc_app.Components.Helpers.DialogHelper.CustomDialogBuilder;

/**
 * Created by Jônatas on 18/06/2017.
 */

public class TextToSpeechComponent {
    private static TextToSpeech tts = null;
    private static Locale ttsLocale = null;

    private static void loadTextToSpeech(Context context){
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(ttsLocale != null) tts.setLanguage(ttsLocale);
            }
        });
    }

    public  static void textToSpeech(Context context, String text){
        if(tts == null) loadTextToSpeech(context);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public static void setTextToSpeechLocale(Context context, Locale l){
        ttsLocale = l;
        loadTextToSpeech(context);
    }

}
