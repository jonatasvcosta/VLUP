package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 02/11/2016.
 */

public class CustomTextView extends TextView {
    private String punctuation = ".:!-?";
    public CustomTextView(Context context, AttributeSet attrs) {
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
                WordContextDialog.launchDialog(getContext(), selectedWord, getContextPhrase(), null);
                return true;
            }

            private String getContextPhrase(){
                String text = getText().toString();
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                int begin = selectionStart;
                int end = selectionEnd;
                boolean addInitialEllipsize = false, addFinalEllipsize = false;
                while(begin > 0){
                    if(punctuation.indexOf(text.charAt(begin)) != -1){
                        begin++;
                        break;
                    }
                    if(selectionStart - begin >= MAX_CONTEXT_PHRASE_TEXT_LENGTH/2){
                        addInitialEllipsize = true;
                        break;
                    }
                    begin --;
                }
                while(end < length()){
                    if(punctuation.indexOf(text.charAt(end)) != -1) break;
                    if(end - selectionEnd >= MAX_CONTEXT_PHRASE_TEXT_LENGTH/2){
                        addFinalEllipsize = true;
                        break;
                    }
                    end++;
                }
                String contextPhrase = text.substring(begin, end);
                if(addInitialEllipsize) contextPhrase = "..."+contextPhrase;
                if(addFinalEllipsize) contextPhrase = contextPhrase+"...";
                return contextPhrase;
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
