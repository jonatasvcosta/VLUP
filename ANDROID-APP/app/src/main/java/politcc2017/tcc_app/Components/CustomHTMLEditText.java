package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import io.github.mthli.knife.KnifeText;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 02/11/2016.
 */

public class CustomHTMLEditText extends KnifeText {
    public CustomHTMLEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getContext()));
    }

    private static final int MAX_CONTEXT_PHRASE_TEXT_LENGTH = 200;

    public void allowWordContextMenu(){
        setTextIsSelectable(true);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                String selectedWord = getText().subSequence(getSelectionStart(), getSelectionEnd()).toString();
                if(!selectedWord.matches(".*[a-zA-Z]+.*")) return false;
                WordContextMenu wordData = ServerRequestHelper.getWordInformation(getContext(), SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY), selectedWord);
                WordContextDialog.launchDialog(getContext(), selectedWord, wordData.translation, getContextPhrase(), new ContextMenuClickListener() {
                    @Override
                    public void onClick(View v, String action) {
                        Toast.makeText(getContext(), action, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onClick(View view) {

                    }
                });
                return true;
            }

            private String getContextPhrase(){
                String text = getText().toString();
                int begin = 0, index = 0, end = getSelectionEnd();
                while (index != -1 && index <= getSelectionStart()){
                    begin = index;
                    if(begin + 1 < text.length()){
                        index = text.indexOf('.', begin+1);
                        if(index == -1) index = text.indexOf(',', begin+1);
                        if(index == -1) index = text.indexOf('\n', begin+1);
                    }
                }
                if(text.charAt(begin) == '.' || text.charAt(begin) == ',') begin++;
                end = text.indexOf('.', getSelectionEnd());
                if(end == -1){
                    end = begin + MAX_CONTEXT_PHRASE_TEXT_LENGTH;
                    if(end > text.length()) end = text.length();
                }
                if((begin - end) < MAX_CONTEXT_PHRASE_TEXT_LENGTH) return text.substring(begin, end);
                else if((begin - getSelectionStart()) < MAX_CONTEXT_PHRASE_TEXT_LENGTH/2) return (text.substring(begin, calculateFinalIndex(begin, text.length()))+"...");
                else if((end - getSelectionEnd()) < MAX_CONTEXT_PHRASE_TEXT_LENGTH/2) return ("..."+text.substring(calculateInitialIndex(end), end));
                else return ("..."+text.substring(getSelectionStart() - calculateMiddleIndex(), getSelectionEnd() + calculateMiddleIndex())+"...");
            }

            private int calculateFinalIndex(int begin, int maxLength){
                int ret = MAX_CONTEXT_PHRASE_TEXT_LENGTH - (begin + getSelectionEnd());
                if(ret < maxLength) return ret;
                else return maxLength;
            }

            private int calculateInitialIndex(int end){
                int ret = MAX_CONTEXT_PHRASE_TEXT_LENGTH - (getSelectionStart() + end);
                if(ret > 0) return ret;
                return 0;
            }

            private int calculateMiddleIndex(){
                int ret = (MAX_CONTEXT_PHRASE_TEXT_LENGTH - (getSelectionEnd() + getSelectionStart()))/2;
                if(ret > 0) return ret;
                return 0;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }
}
