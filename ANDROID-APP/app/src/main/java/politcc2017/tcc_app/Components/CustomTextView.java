package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 02/11/2016.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(FontHelper.get(FontHelper.TTF_FONT, getContext()));
    }

    public void allowWordContextMenu(){
        setTextIsSelectable(true);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                String selectedWord = getText().subSequence(getSelectionStart(), getSelectionEnd()).toString();
                WordContextMenu wordData = ServerRequestHelper.getWordInformation(getContext(), SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY), selectedWord);
                DialogHelper.WordContextDialog(getContext(), selectedWord, wordData.translation, "Context phrase of " + selectedWord).show();
                return true;
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
