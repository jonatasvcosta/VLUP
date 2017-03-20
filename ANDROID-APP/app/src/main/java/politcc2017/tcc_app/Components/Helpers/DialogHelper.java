package politcc2017.tcc_app.Components.Helpers;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 02/11/2016.
 */
public class DialogHelper {
    public static MaterialDialog CustomDialog(Context context, String title, String body, String positiveText, String negativeText){
        return CustomDialog(context, title, -1, body, positiveText, negativeText, null, null);
    }

    public static MaterialDialog CustomDialog(Context context, String title, int icon, String body, String positiveText, String negativeText){
        return CustomDialog(context, title, icon, body, positiveText, negativeText, null, null);
    }

    public static MaterialDialog CustomDialog(Context context, String title, int icon, String body, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveButtonListener, MaterialDialog.SingleButtonCallback negativeButtonListener){
        return CustomDialogBuilder(context, title, icon, body, positiveText, negativeText, positiveButtonListener, negativeButtonListener, false, null).build();
    }

    public static MaterialDialog ProgressDialog(Context context, String title){
        return ProgressDialog(context, title, -1, null, null, null, null, null);
    }

    public static MaterialDialog ProgressDialog(Context context, String title, String body){
        return ProgressDialog(context, title, -1, body, null, null, null, null);
    }

    public static MaterialDialog ListSingleChoiceDialog(Context context, String title, ArrayList<String> array, String positiveText, String negativeText, MaterialDialog.ListCallbackSingleChoice singleChoice){
        return ListChoiceDialog(context, title, -1, array, positiveText, negativeText, null, singleChoice);
    }

    public static MaterialDialog ListSingleChoiceDialog(Context context, String title, int icon, ArrayList<String> array, String positiveText, String negativeText, MaterialDialog.ListCallbackSingleChoice singleChoice){
        return ListChoiceDialog(context, title, icon, array, positiveText, negativeText, null, singleChoice);
    }

    public static MaterialDialog ListMultiChoiceDialog(Context context, String title, ArrayList<String> array, String positiveText, String negativeText, MaterialDialog.ListCallbackMultiChoice multiChoice){
        return ListChoiceDialog(context, title, -1, array, positiveText, negativeText, multiChoice, null);
    }

    public static MaterialDialog ListSingleChoiceDialog(Context context, String title, int icon, ArrayList<String> array, String positiveText, String negativeText, MaterialDialog.ListCallbackMultiChoice multiChoice){
        return ListChoiceDialog(context, title, icon, array, positiveText, negativeText, multiChoice, null);
    }

    public static MaterialDialog ListChoiceDialog(Context context, String title, int icon, ArrayList<String> array, String positiveText, String negativeText, MaterialDialog.ListCallbackMultiChoice multiChoiceListener, MaterialDialog.ListCallbackSingleChoice singleChoiceListener){
        MaterialDialog.Builder builder =  CustomDialogBuilder(context, title, icon, null, positiveText, negativeText, null, null, false, null);
        if(array != null){
            builder.items(array);
        }
        if(multiChoiceListener != null) builder.itemsCallbackMultiChoice(null, multiChoiceListener);
        else if(singleChoiceListener != null) builder.itemsCallbackSingleChoice(-1, singleChoiceListener);
        return builder.build();
    }

    public static MaterialDialog ProgressDialog(Context context, String title, int icon, String body, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveButtonListener, MaterialDialog.SingleButtonCallback negativeButtonListener){
        MaterialDialog.Builder builder =  CustomDialogBuilder(context, title, icon, body, positiveText, negativeText, positiveButtonListener, negativeButtonListener, false, null);
        builder.progress(true, 0);
        return builder.build();
    }

    public static MaterialDialog ErrorDialog(Context context, int icon, String message, String positiveText){
        MaterialDialog.Builder builder = CustomDialogBuilder(context, "", icon, message, positiveText, null, null, null, false, null);
        return builder.build();
    }

    public static MaterialDialog InputDialog(Context context, String title, MaterialDialog.InputCallback callback, String positiveText, String negativeText){
        MaterialDialog.Builder builder = CustomDialogBuilder(context, title, -1, "" , positiveText, negativeText, null, null, true, callback);
        return builder.build();
    }

    public static MaterialDialog InputDialog(Context context, String title, MaterialDialog.InputCallback callback, String positiveText, String negativeText, String filledText){
        MaterialDialog.Builder builder = CustomDialogBuilder(context, title, -1, "" , positiveText, negativeText, null, null, true, callback, filledText, false);
        return builder.build();
    }

    public static MaterialDialog.Builder CustomDialogBuilder(Context context, String title, int icon, String body, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveButtonListener, MaterialDialog.SingleButtonCallback negativeButtonListener, boolean inputDialog, MaterialDialog.InputCallback inputCallback) {
        return CustomDialogBuilder(context, title, icon, body, positiveText, negativeText, positiveButtonListener, negativeButtonListener, inputDialog, inputCallback, null, false);
    }

    public static MaterialDialog WordContextDialog(Context context, String title, String translationString){
        return WordContextDialog(context, title, translationString, "");
    }

    public static MaterialDialog WordContextDialog(Context context, String title, String translationString, String phraseContext){
        MaterialDialog.Builder builder = CustomDialogBuilder(context, title, -1, null, "", "", null, null, false, null, null, true);
        MaterialDialog dialog =  builder.build();
        View view = dialog.getCustomView();
        CustomTextView translation = (CustomTextView) view.findViewById(R.id.word_context_menu_translation_text);
        translation.allowWordContextMenu(); //Check if this label should be recursively translatable
        translation.setText(translationString);
        if(phraseContext != null && phraseContext.length() > 0) {
            CustomTextView contextPhrase = (CustomTextView) view.findViewById(R.id.word_context_menu_phrase_context_text);
            contextPhrase.allowWordContextMenu(); //Check if this label should be recursively translatable
            contextPhrase.setText(phraseContext);
        }
        return dialog;
    }

    public static MaterialDialog.Builder CustomDialogBuilder(Context context, String title, int icon, String body, String positiveText, String negativeText, MaterialDialog.SingleButtonCallback positiveButtonListener, MaterialDialog.SingleButtonCallback negativeButtonListener, boolean inputDialog, MaterialDialog.InputCallback inputCallback, String filledText, boolean wordContextMenu) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .titleColor(context.getResources().getColor(R.color.black))
                .typeface(FontHelper.FONT_NAME, FontHelper.FONT_NAME)
                .backgroundColor(context.getResources().getColor(R.color.cyan_darkest));
        if(wordContextMenu){
            builder.titleGravity(GravityEnum.CENTER)
                    .customView(R.layout.word_context_menu, true);
        }
        if(body != null){
            builder.content(body)
                    .contentColor(context.getResources().getColor(R.color.beige));
        }
        if(positiveText != null){
            builder.positiveText(positiveText)
                    .positiveColor(context.getResources().getColor(R.color.black));
        }
        if(negativeText != null){
            builder.negativeText(negativeText)
                    .negativeColor(context.getResources().getColor(R.color.black));
        }
        if(inputDialog){
            builder.inputType(InputType.TYPE_CLASS_TEXT)
            .input(null, filledText, inputCallback);

        }
        if(icon != -1) builder.iconRes(icon);
        if(positiveButtonListener != null) builder.onPositive(positiveButtonListener);
        if(negativeButtonListener != null) builder.onNegative(negativeButtonListener);
        return builder;
    }
}