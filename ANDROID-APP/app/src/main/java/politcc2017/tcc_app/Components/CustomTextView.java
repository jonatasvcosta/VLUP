package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.FontHelper;

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
                DialogHelper.CustomDialog(getContext(), "Titulo", getText().subSequence(getSelectionStart(), getSelectionEnd()).toString(), "OK", "Cancelar").show();
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
