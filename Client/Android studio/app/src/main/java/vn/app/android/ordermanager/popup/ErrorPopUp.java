package vn.app.android.ordermanager.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

import com.order.ordermanager.R;

import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.StringUtil;

public class ErrorPopUp extends Dialog {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat tag.
	 */
	public static final String TAG = ErrorPopUp.class.getName();
	//--------------------------i------------------------------------------------------------------------------------------
	/**
	 * Pop Up title
	 */
	protected String mTitle = "";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop Up message
	 */
	protected String mMessage = "";
	//--------------------------------------------------------------------------------------------------------------------
	protected SpannableString mSpanMessage;
	//--------------------------------------------------------------------------------------------------------------------
	protected boolean mUseSpanMessage = false;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Title Color.
	 */
	protected int mTitleColor = -1;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Message Color.
	 */
	protected int mMessageColor = -1;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Host view of this PopUp
	 */
	protected View mHostView;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop Up Layout
	 */
	protected View mPopupLayout;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Context
	 */
	protected Context mContext;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Is Close Activity with dialog.
	 */
	protected boolean mIsCloseActivityWithDialog = false;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * OK button OnClick callback
	 */
	protected View.OnClickListener mButtonOnClickCallBack;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * <code>true</code> use Spannable String for the message view. <br/>
	 * <code>false</code> use normal String
	 * @param isUseSpannableStringMessage
	 * @return
	 */
	public ErrorPopUp useSpanMessage(boolean isUseSpannableStringMessage) {
		mUseSpanMessage = isUseSpannableStringMessage;
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
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
	public ErrorPopUp setTitle(String title) {
		mTitle = title;
		if(mPopupLayout != null) {
			TextView tvTitle = (TextView) findViewById(R.id.tvPopUpErrorTitle);
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
	public ErrorPopUp setTitleColor(int color) {
		mTitleColor = color;
		if(mPopupLayout != null) {
			TextView tvTitle = (TextView) findViewById(R.id.tvPopUpErrorTitle);
			if(mTitleColor != -1) {
				tvTitle.setTextColor(mTitleColor);
			}
		}
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set close activity with dialog
	 * @param isCloseAppWithDialog
	 * @return
	 */
	public ErrorPopUp setCloseActivityWithDialog(boolean isCloseAppWithDialog) {
		mIsCloseActivityWithDialog = isCloseAppWithDialog;
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
		/**
		 * Get Button OnClickListener.
		 * @param buttonOnClickCallBack
		 */
		public ErrorPopUp setButtonOnClickCallBack(View.OnClickListener buttonOnClickCallBack) {
			mButtonOnClickCallBack = buttonOnClickCallBack;
			if(mPopupLayout != null) {
				TextView tvOkButton = (TextView) findViewById(R.id.tvPopUpErrorOkButton);
				if(tvOkButton != null) {
					tvOkButton.setOnClickListener(mButtonOnClickCallBack);
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
	public ErrorPopUp setMessage(String message) {
		mMessage = message;
		if(mPopupLayout != null) {
			TextView tvMessage = (TextView) findViewById(R.id.tvPopUpErrorMessage);
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
	public ErrorPopUp setMessage(SpannableString message) {
		mSpanMessage = message;
		if(mPopupLayout != null) {
			TextView tvMessage = (TextView) findViewById(R.id.tvPopUpErrorMessage);
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
	public ErrorPopUp setMessageColor(int color) {
		mMessageColor = color;
		if(mPopupLayout != null) {
			TextView tvMessage = (TextView) findViewById(R.id.tvPopUpErrorMessage);
			if(mMessageColor != -1) {
				tvMessage.setTextColor(mMessageColor);
			}
		}
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param context
	 */
	public ErrorPopUp(Context context, View hostView) {
		super(context);
		mContext = context;
		mHostView = hostView;
		createDefaultButtonOnClickCallback();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor.
	 * @param context
	 * @param hostView
	 * @param message
	 */
	public ErrorPopUp(Context context, View hostView, String message) {
		super(context);
		mContext = context;
		mHostView = hostView;
		mMessage = message;
		mTitle = context.getResources().getString(R.string.pop_up_error_title);
		createDefaultButtonOnClickCallback();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param context
	 */
	public ErrorPopUp(Context context, View hostView, String title, String message) {
		super(context);
		mContext = context;
		mHostView = hostView;
		mTitle = title;
		mMessage = message;
		createDefaultButtonOnClickCallback();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param context
	 * @param theme
	 */
	public ErrorPopUp(Context context, int theme, View hostView) {
		super(context, theme);
		mContext = context;
		mHostView = hostView;
		createDefaultButtonOnClickCallback();
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor.
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	protected ErrorPopUp(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
		createDefaultButtonOnClickCallback();
	}
	//--------------------------------------------------------------------------------------------------------------------
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mPopupLayout = LayoutInflater.from(getContext()).inflate(R.layout.pop_up_error, null);
		setContentView(mPopupLayout);
		Resources resource = mContext.getResources(); 
		float width = resource.getDimension(R.dimen.h300);
		getWindow().setLayout((int) width, LayoutParams.WRAP_CONTENT);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setCanceledOnTouchOutside(false);
		setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				AppState.sIsErrorPopUpOpened = false;
				dismiss();
			}
		});
		
		//Set pop up title
		setTitle(mTitle).setTitleColor(mTitleColor);
		
		//Set pop up message
		if(mUseSpanMessage) {
			setMessageColor(mMessageColor).setMessage(mSpanMessage);
		}
		else {
			setMessage(mMessage).setMessageColor(mMessageColor);
		}
		
		//Set button callback
		setButtonOnClickCallBack(mButtonOnClickCallBack);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onBackPressed() {
		//Do nothing when press back button
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Create Default Button OnClick Callback.
	 */
	private void createDefaultButtonOnClickCallback() {
		mButtonOnClickCallBack = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ErrorPopUp.this.dismiss();
				AppState.sIsErrorPopUpOpened = false;
				if(mIsCloseActivityWithDialog && mContext instanceof Activity) {
					((Activity) mContext).finish();
				}
			}
		};
	}
	//--------------------------------------------------------------------------------------------------------------------
}
