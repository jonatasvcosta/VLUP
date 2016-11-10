package politcc2017.tcc_app.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 02/11/2016.
 */

public class CustomEditText extends LinearLayout {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    protected final int EMAIL_INPUT = 1, PASSWORD_INPUT = 0, MANDATORY_INPUT = 2, OPTIONAL_INPUT = 3, PASSWORD_MIN_LENGTH = 6;
    protected EditText mEditText;
    protected CustomTextView errorText;
    protected LinearLayout errorLayout;
    protected ImageView rightIcon, leftIcon;
    protected Context mContext;
    protected String errorMessage, hintText;
    protected int fieldType = OPTIONAL_INPUT;
    protected OnFocusChangeListener onFocusChangeListener;
    protected OnClickListener onClickListener;
    protected TextWatcher textWatcher;
    protected Pattern pattern;

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
        errorText = (CustomTextView) findViewById(R.id.custom_edit_text_error_text);
        errorLayout = (LinearLayout) findViewById(R.id.custom_edit_text_error_layout);
        pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        if(attrs == null) return;
        getXMLValues(attrs);
        mEditText.setTypeface(FontHelper.get(FontHelper.TTF_FONT, c));
    }

    private void getXMLValues(AttributeSet attrs){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomEditText, R.attr.actionBarStyle, 0);
        if(a == null) return;
        errorMessage = a.getString(R.styleable.CustomEditText_error_message);
        hintText = a.getString(R.styleable.CustomEditText_hint_text);
        if(errorMessage != null && errorMessage.length() > 0) errorText.setText(errorMessage);
        if(hintText != null && hintText.length() > 0) mEditText.setHint(hintText);
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

        if(a.getInt(R.styleable.CustomEditText_display_type, OPTIONAL_INPUT) == 0) {
            mEditText.setSingleLine();
        }
        if(a.getInt(R.styleable.CustomEditText_display_type, OPTIONAL_INPUT) == 1) {
            mEditText.setMaxHeight((int)getResources().getDimension(R.dimen.multiline_edit_text_fixed_height));
        }
        if(fieldType != OPTIONAL_INPUT){
            mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(onFocusChangeListener != null) onFocusChangeListener.onFocusChange(view, b);
                    if(!mEditText.hasFocus()) validate();
                }
            });
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(textWatcher != null) textWatcher.beforeTextChanged(charSequence, i, i1, i2);
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(textWatcher != null) textWatcher.onTextChanged(charSequence, i, i1, i2);
                    validate();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(textWatcher != null) textWatcher.afterTextChanged(editable);
                }
            });
        }
        a.recycle();
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public void registerClickListener(OnClickListener clickListener){
        this.onClickListener = clickListener;
    }

    public void registerFocusChangeListener(OnFocusChangeListener listener){
        this.onFocusChangeListener = listener;
    }

    public void registerTextWatcher(TextWatcher watcher){
        this.textWatcher = watcher;
    }

    protected boolean validateEmail() {
        return pattern.matcher(mEditText.getText().toString()).matches();
    }

    protected boolean hasError(){
        if(fieldType != OPTIONAL_INPUT && mEditText.getText().toString().length() == 0) return true;
        else if(fieldType == PASSWORD_INPUT && mEditText.getText().toString().length() < PASSWORD_MIN_LENGTH) return true;
        else if(fieldType == EMAIL_INPUT && !validateEmail()) return true;
        return false;
    }

    public void validate(){
        if(fieldType != OPTIONAL_INPUT && !hasError() && errorLayout.getVisibility() == VISIBLE){
            errorLayout.setVisibility(GONE);
            rightIcon.setImageResource(R.drawable.ic_right);
            rightIcon.setVisibility(VISIBLE);
        }
        if(hasError() && errorLayout.getVisibility() != VISIBLE){
            errorLayout.setVisibility(VISIBLE);
            rightIcon.setImageResource(R.drawable.ic_wrong);
            rightIcon.setVisibility(VISIBLE);
        }
    }

}
