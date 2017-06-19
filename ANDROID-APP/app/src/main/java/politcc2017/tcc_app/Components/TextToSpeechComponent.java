package politcc2017.tcc_app.Components;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

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
