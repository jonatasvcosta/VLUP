package politcc2017.tcc_app.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 02/11/2016.
 */

public class CustomEditText extends LinearLayout {
    protected final int EMAIL_INPUT = 1, PASSWORD_INPUT = 0, MANDATORY_INPUT = 2, OPTIONAL_INPUT = 3;
    protected EditText mEditText;
    protected ImageView rightIcon, leftIcon;
    protected Context mContext;
    protected String errorMessage;
    protected int fieldType = OPTIONAL_INPUT;

    public CustomEditText(Context context) {
        super(context);
        ComponentSetup(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        ComponentSetup(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ComponentSetup(context, attrs);
    }

    private void ComponentSetup(Context c, AttributeSet attrs){
        mContext = c;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_edit_text, this, true);
        mEditText = (EditText) findViewById(R.id.custom_edit_text_edit_text);
        leftIcon = (ImageView) findViewById(R.id.custom_edit_text_left_icon);
        rightIcon = (ImageView) findViewById(R.id.custom_edit_text_right_icon);
        mEditText.setTypeface(FontHelper.get(FontHelper.TTF_FONT, c));
        if(attrs == null) return;
        getXMLValues(attrs);
    }

    private void getXMLValues(AttributeSet attrs){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomEditText, R.attr.actionBarStyle, 0);
        if(a == null) return;
        errorMessage = a.getString(R.styleable.CustomEditText_error_message);
        if(a.getInt(R.styleable.CustomEditText_validation_type, OPTIONAL_INPUT) == 0) {
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditText.setSelection(mEditText.getText().length());
            fieldType = PASSWORD_INPUT;
        }
        else if(a.getInt(R.styleable.CustomEditText_validation_type, OPTIONAL_INPUT) == 1) {
            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            fieldType = EMAIL_INPUT;
        }
        else if(a.getInt(R.styleable.CustomEditText_validation_type, OPTIONAL_INPUT) == 2) {
            fieldType = MANDATORY_INPUT;
        }

        int leftID = a.getResourceId(R.styleable.CustomEditText_left_icon, -1);
        int rightID = a.getResourceId(R.styleable.CustomEditText_right_icon, -1);

        if(leftID != -1) leftIcon.setImageResource(leftID);
        if(rightID != -1) rightIcon.setImageResource(rightID);

        if(a.getInt(R.styleable.CustomEditText_display_type, OPTIONAL_INPUT) == 0) {
            mEditText.setSingleLine();
        }
        if(a.getInt(R.styleable.CustomEditText_display_type, OPTIONAL_INPUT) == 1) {
            mEditText.setMaxHeight((int)getResources().getDimension(R.dimen.multiline_edit_text_fixed_height));
        }

        a.recycle();
    }

}
