package vn.app.android.ordermanager.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomEllipsizeTextView extends TextView {
	//--------------------------------------------------------------------------------------------------------------------
	public static final String TAG = CustomEllipsizeTextView.class.getName();
	//--------------------------------------------------------------------------------------------------------------------
	protected int mMaxLines = 1;
	//--------------------------------------------------------------------------------------------------------------------
	protected String mEllipsize = "...";
	//--------------------------------------------------------------------------------------------------------------------
	protected SpannableString spanString;
	//--------------------------------------------------------------------------------------------------------------------
	protected ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GRAY);
	//--------------------------------------------------------------------------------------------------------------------
	public CustomEllipsizeTextView(Context context) {
		super(context);
	}
	//--------------------------------------------------------------------------------------------------------------------
	public CustomEllipsizeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//--------------------------------------------------------------------------------------------------------------------
	public CustomEllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@SuppressLint("NewApi")
	public CustomEllipsizeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public void setMaxLines(int maxlines) {
		super.setMaxLines(maxlines);
		mMaxLines = maxlines;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	public int getMaxLines() {
		return mMaxLines;
	}
	//--------------------------------------------------------------------------------------------------------------------
	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		onPreDraw();
		int maxLines = getMaxLines();
		setLines(maxLines);
		if(getLineCount() > maxLines){
			int ellipsizeLength = mEllipsize.length();
			int lineEndIndex = getLayout().getLineEnd(maxLines - 1);
			int trimLength = ellipsizeLength;
			//In case last line length less than ellipsize symbol length
			if(maxLines > 1) {
				int paddingCount = 3;
				int lastLineLength = lineEndIndex - getLayout().getLineEnd(maxLines-2);
				if(lastLineLength < ellipsizeLength - paddingCount) {
					trimLength = ellipsizeLength-lastLineLength-paddingCount;
				}
			}
			//Reset the text with padding ellipsize
			int preLength = lineEndIndex-trimLength;
			String newText = getText().subSequence(0, preLength) + mEllipsize;
			spanString = new SpannableString(newText);
			spanString.setSpan(foregroundColorSpan, preLength, newText.length(), 0);
			setText(spanString);
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			onPreDraw();
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
}