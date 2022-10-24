package com.han.total.Util;

import android.app.Dialog;
import android.content.Context;

import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.han.total.Interface.OneButtonDialogCallback;
import com.han.total.Interface.TwoButtonDialogCallback;
import com.han.total.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class CustomDialog extends Dialog {
    @Getter
    @Setter
    private TwoButtonDialogCallback TwoButtonDialogCallback;
    @Getter
    @Setter
    private OneButtonDialogCallback OneButtonDialogCallback;

    @BindView(R.id.two_button_dialog_title) TextView two_button_dialog_title;
    @BindView(R.id.two_button_dialog_content) TextView two_button_dialog_content;
    @BindView(R.id.two_button_dialog_confirm) Button two_button_dialog_confirm;
    @BindView(R.id.two_button_dialog_cancel) Button two_button_dialog_cancel;

    public CustomDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView(R.layout.custom_dialog2);
        ButterKnife.bind(this);
        //BusProvider.getInstance().register(this);
    }

    @OnClick(R.id.two_button_dialog_confirm)
    public void onClickTwoButtonDialogConfirm(View view) {
        if (TwoButtonDialogCallback != null) {
            TwoButtonDialogCallback.onClickConfirm();
        }else if(OneButtonDialogCallback !=null){
            OneButtonDialogCallback.onClickConfirm();
        }
    }

    @OnClick(R.id.two_button_dialog_cancel)
    public void onClickTwoButtonDialogCancel(View view) {
        if (TwoButtonDialogCallback != null) {
            TwoButtonDialogCallback.onClickCancel();
        }
    }

    public void setConfirmText(String text){ two_button_dialog_confirm.setText(text);}
    public void setConfirmText(int text){ two_button_dialog_confirm.setText(text);}
    public void setConfirmTextSize(float textSize){ two_button_dialog_confirm.setTextSize(textSize);}
    public void setCancelText(String text){ two_button_dialog_cancel.setText(text);}
    public void setCancelText(int text){ two_button_dialog_cancel.setText(text);}
    public void setCancelTextSize(float textSize){ two_button_dialog_cancel.setTextSize(textSize);}
    public void setTitleVisibility(int visibility) { two_button_dialog_title.setVisibility(visibility);}
    public void setTitle(String title) {
        two_button_dialog_title.setText(title);
        //TextColorChange.chagneTextColor(two_button_dialog_title);
    }
    public void setTitle(int titleRes) {
        two_button_dialog_title.setText(titleRes);
        //TextColorChange.chagneTextColor(two_button_dialog_title);
    }
    public void setContent(String content) {
        two_button_dialog_content.setText(content);
        //TextColorChange.chagneTextColor(two_button_dialog_content);
    }
    public void setContent(int contentRes) {
        two_button_dialog_content.setText(contentRes);
        //TextColorChange.chagneTextColor(two_button_dialog_content);
    }

    public void setContent(Spanned content){
        two_button_dialog_content.setText(content,TextView.BufferType.SPANNABLE);
    }

    public void setContentVisibility(int visibility){
        two_button_dialog_content.setVisibility(visibility);
    }
    @Override
    public void dismiss() {
        super.dismiss();
        //BusProvider.getInstance().unregister(this);
    }

}
