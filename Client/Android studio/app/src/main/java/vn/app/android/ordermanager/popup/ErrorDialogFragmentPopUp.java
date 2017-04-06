package vn.app.android.ordermanager.popup;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.order.ordermanager.R;

import vn.app.android.ordermanager.util.StringUtil;

/**
 * Created by Mr Vuong on 4/1/2017.
 */
@SuppressLint("ValidFragment")
public class ErrorDialogFragmentPopUp extends DialogFragment {
    public static final String TAG = ErrorDialogFragmentPopUp.class.getName();
    protected String mTitle = "";
    private String mMessage;
    protected View mPopupLayout;
    protected Context mContext;
    protected int mTitleColor = -1;
    protected int mMessageColor = -1;
    protected SpannableString mSpanMessage;

    public int getmMessageColor() {
        return mMessageColor;
    }

    public void setmMessageColor(int mMessageColor) {
        this.mMessageColor = mMessageColor;
    }

    public int getmTitleColor() {
        return mTitleColor;
    }

    public void setmTitleColor(int mTitleColor) {
        this.mTitleColor = mTitleColor;
    }
    //----------------------------------
//    protected View.OnClickListener mButtonOnClickCallBack;
    @SuppressLint("ValidFragment")
    public ErrorDialogFragmentPopUp(Context context,String title, String message){
        this.mContext = context;
        this.mTitle = title;
        this.mMessage = message;

    }
    /**
     * Get title.
     * @return
     */
    public String getTitle() {
        return mTitle;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Set title.
     * @param title
     * @return
     */
    public ErrorDialogFragmentPopUp setTitle(String title) {
        mTitle = title;
        if(mPopupLayout != null) {
            TextView tvTitle = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorTitle);
            tvTitle.setText(mTitle);
        }
        return this;
    }
    //--------------------------------------------------------------------------------------------------------------------

    /**
     * Set title color.
     * @param color

     * @return
     */
    public ErrorDialogFragmentPopUp setTitleColor(int color) {
        mTitleColor = color;
        if(mPopupLayout != null) {
            TextView tvTitle = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorTitle);
            if(mTitleColor != -1) {
                tvTitle.setTextColor(mTitleColor);
            }
        }
        return this;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Get Button OnClickListener.
     */
    public ErrorDialogFragmentPopUp setButtonOnClickCallBack() {
        if(mPopupLayout != null) {
            TextView tvOkButton = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorOkButton);
            if(tvOkButton != null) {
                tvOkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
            }
        }
        return this;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Get message
     * @return
     */
    public String getMessage() {
        return mMessage;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Set message
     * @param message
     * @return
     */
    public ErrorDialogFragmentPopUp setMessage(String message) {
        mMessage = message;
        if(mPopupLayout != null) {
            TextView tvMessage = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorMessage);
            if(StringUtil.isEmpty(message)) {
                tvMessage.setVisibility(View.GONE);
            }
            else {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(mMessage);
            }
        }
        return this;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Set message by Spannable String
     * @param message
     * @return
     */
    public ErrorDialogFragmentPopUp setMessage(SpannableString message) {
        mSpanMessage = message;
        if(mPopupLayout != null) {
            TextView tvMessage = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorMessage);
            if(StringUtil.isEmpty(mSpanMessage.toString())) {
                tvMessage.setVisibility(View.GONE);
            }
            else {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(mSpanMessage);
            }
        }
        return this;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Set message Color.
     * @param color
     * @return
     */
    public ErrorDialogFragmentPopUp setMessageColor(int color) {
        mMessageColor = color;
        if(mPopupLayout != null) {
            TextView tvMessage = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorMessage);
            if(mMessageColor != -1) {
                tvMessage.setTextColor(mMessageColor);
            }
        }
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPopupLayout = inflater.inflate(R.layout.pop_up_error, null);
        Resources resource = mContext.getResources();
        float width = resource.getDimension(R.dimen.h300);
        //set Layout dialog
        getDialog().getWindow().setLayout((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getDialog().setCanceledOnTouchOutside(false);
//        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                AppState.sIsErrorPopUpOpened = false;
//                dismiss();
//            }
//        });

        //Set pop up title
//        setTitle(mTitle).setTitleColor(mTitleColor);

//        setMessage(mMessage).setMessageColor(mMessageColor);

        //Set button callback
//        setButtonOnClickCallBack();
        getMadatoryView();
        return mPopupLayout;
    }
    private void getMadatoryView(){
        TextView tvTitle = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorTitle);
        TextView tvMessage = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorMessage);
        TextView tvOkButton = (TextView) mPopupLayout.findViewById(R.id.tvPopUpErrorOkButton);
        if(tvOkButton != null) {
            tvOkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        if(tvTitle != null){
            tvTitle.setText(mTitle);
        }
        if(tvMessage != null){
            tvMessage.setText(mMessage);
        }
    }
    /**
     * Create Default Button OnClick Callback.
     */
//    private void createDefaultButtonOnClickCallback() {
//        mButtonOnClickCallBack = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ErrorDialogFragmentPopUp.this.dismiss();
//            }
//        };
//    }
}
