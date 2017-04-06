package vn.app.android.ordermanager.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Dynamic Image View
 * @author MrVuong
 */
public class DynamicImageView extends ImageView {
	//--------------------------------------------------------------------------------------------------------------------
	public DynamicImageView(Context context) {
		super(context);
	}
	//--------------------------------------------------------------------------------------------------------------------
	public DynamicImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@SuppressLint("NewApi")
	public DynamicImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	//--------------------------------------------------------------------------------------------------------------------
	public DynamicImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	//--------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
	{
		final Drawable drawable = this.getDrawable();
		if(drawable != null) {
			//Ceil not round - avoid thin vertical gaps along the left/right edges
			final int width = MeasureSpec.getSize(widthMeasureSpec);
			final int height = (int) Math.ceil(width * (float) drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth());
			this.setMeasuredDimension(width, height);
		} 
		else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
}
