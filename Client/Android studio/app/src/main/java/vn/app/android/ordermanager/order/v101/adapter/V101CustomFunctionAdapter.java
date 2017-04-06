package vn.app.android.ordermanager.order.v101.adapter;

import vn.app.android.ordermanager.collection.SortedArrayList;
import vn.app.android.ordermanager.mode.Function;
import vn.app.android.ordermanager.order.v101.fragment.V101MainFragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.order.ordermanager.R;
/**
 * Custom Adapter List Function to add menu left
 * @author Mr Vuong
 *
 */
public class V101CustomFunctionAdapter extends BaseAdapter{
	
	public static class ViewHolder{
		ImageView iconFunction;
		TextView txtFunctionName;
		View layout;
		int position;
	}
	public static String TAG = V101CustomFunctionAdapter.class.getName();
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected SortedArrayList<Function> mDataSource = new SortedArrayList<Function>();
	private int mSlected;
	
	public int getmSlected() {
		return mSlected;
	}
	public void setmSlected(int mSlected) {
		this.mSlected = mSlected;
	}
	public V101CustomFunctionAdapter(Context context, SortedArrayList<Function> dataSource){
		this.mContext = context;
		setDataSource(dataSource);
		mInflater = LayoutInflater.from(context);
	}
	/**
	 * set data source 
	 * @param dataSource
	 */
	public void setDataSource(SortedArrayList<Function> dataSource){
		if(dataSource != null){
			mDataSource = dataSource;
		}
	}
	public void setDataSourceAndNotifyDataSetChange(SortedArrayList<Function>dataSource){
		if(dataSource != null){
			mDataSource = dataSource;
		}
		if(this != null){
			notifyDataSetChanged();
		}
	}
	@Override
	public int getCount() {
		return mDataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_menu_function, null);
			viewHolder = new ViewHolder();
			viewHolder.position = position;
			viewHolder.iconFunction = (ImageView) convertView.findViewById(R.id.item_menu_img_function_icon);
			viewHolder.txtFunctionName = (TextView) convertView.findViewById(R.id.item_menu_txt_function_name);
			viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.item_menu_ln_function_layout);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Function function = mDataSource.get(position);
		if(function != null){
			viewHolder.position = position;
			viewHolder.txtFunctionName.setText(function.getmName());
			viewHolder.iconFunction.setImageResource((position / 2 ==0) ? R.drawable.menu_icon_order : R.drawable.menu_icon_map);
		}
		if(getmSlected() == position){
			viewHolder.layout.setBackgroundColor(Color.BLUE);
		}else{
			viewHolder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.menu_list_writing_left_background));
		}
		return convertView;
	}

}
