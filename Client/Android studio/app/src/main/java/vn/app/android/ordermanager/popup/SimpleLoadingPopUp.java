package vn.app.android.ordermanager.popup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

import com.order.ordermanager.R;

/**
 * Simple loading pop up using Dialog
 * @author thaonp
 */
public class SimpleLoadingPopUp extends Dialog {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Log cat TAG
	 */
	public static final String TAG = SimpleLoadingPopUp.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop Up message
	 */
	protected String mMessage = "";
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Pop Up Background
	 */
	protected int mBackgroundColor = Color.TRANSPARENT;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Host view of this PopUp
	 */
	protected View mHostView;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Loading pop up layout view
	 */
	protected View mLayoutView;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * MainActivity
	 */
	protected Context mContext;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Set background color to the loading pop up window. <br/>
	 * Can set transparent by this value : Color.TRANSPARENT.
	 * @param backgroundColor
	 * @return
	 */
	public SimpleLoadingPopUp setBackgroundColor(int backgroundColor) {
		mBackgroundColor = backgroundColor;
		Window window = getWindow();
		if(window != null) {
			window.setBackgroundDrawable(new ColorDrawable(mBackgroundColor));
		}
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get background color. Default is TRANSPARENT.
	 * @return
	 */
	public int getBackgroundColor() {
		return mBackgroundColor;
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
	public SimpleLoadingPopUp setMessage(String message) {
		mMessage = message;
		if(mLayoutView != null) {
			TextView tvText = (TextView) mLayoutView.findViewById(R.id.tvPopUpWaitingMessage);
			if(tvText != null) {
				tvText.setText(message);
			}
		}
		return this;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param context
	 */
	public SimpleLoadingPopUp(Context context, View hostView) {
		super(context);
		mContext = context;
		mHostView = hostView;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param context
	 * @param theme
	 */
	public SimpleLoadingPopUp(Context context, int theme, View hostView) {
		super(context, theme);
		mContext = context;
		mHostView = hostView;
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor.
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	protected SimpleLoadingPopUp(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLayoutView = LayoutInflater.from(getContext()).inflate(R.layout.pop_up_loading_simple, null);
		setContentView(mLayoutView);
		getWindow().setBackgroundDrawable(new ColorDrawable(mBackgroundColor));
		getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		setCanceledOnTouchOutside(false);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void onBackPressed() {
		//Do nothing when press back button
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Show waiting message
	 */
	public void showWaitingMessage() {
		mLayoutView.findViewById(R.id.tvPopUpWaitingMessage).setVisibility(View.VISIBLE);
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Hide waiting message
	 */
	public void hideWaitingMessage() {
		mLayoutView.findViewById(R.id.tvPopUpWaitingMessage).setVisibility(View.GONE);
	}
	//--------------------------------------------------------------------------------------------------------------------
}
