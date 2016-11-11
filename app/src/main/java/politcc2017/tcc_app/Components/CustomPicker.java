package politcc2017.tcc_app.Components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 10/11/2016.
 */

public class CustomPicker extends CustomEditText {
    protected MaterialDialog dialog;

    public CustomPicker(Context context) {
        super(context);
    }

    public CustomPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void ComponentSetup(Context c, AttributeSet attrs){
        super.ComponentSetup(c, attrs);
        rightIcon.setImageResource(android.R.drawable.arrow_down_float);
        mEditText.setFocusable(false);
        mEditText.setCursorVisible(false);
        fieldType = MANDATORY_INPUT;
    }

    @Override
    public void validate(){
        if(fieldType != OPTIONAL_INPUT && !hasError() && errorLayout.getVisibility() == VISIBLE){
            errorLayout.setVisibility(GONE);
        }
        if(hasError() && errorLayout.getVisibility() != VISIBLE){
            errorLayout.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void setupIconMargins(){
        int margin = (int)getResources().getDimension(R.dimen.margin_small);
        setupIconMarginProgramatically(rightIcon,0, margin, 0, 0);
    }

    @Override
    protected void setupListeners(){
        mEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener != null) onClickListener.onClick(view);
                if(dialog != null) dialog.show();
            }
        });
    }

    public void registerDialog(MaterialDialog dialog){
        this.dialog = dialog;
    }
}
