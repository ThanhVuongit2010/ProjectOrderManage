package vn.app.android.ordermanager.mode;

public class TableOrder {
	/**
	 * Code function
	 */
	private String mCode;
	/**
	 * Name function
	 */
	private String mName;
	/**
	 * 0: no using
	 * 1: Using
	 */
	private int isUsing;
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public int getIsUsing() {
		return isUsing;
	}
	public void setIsUsing(int isUsing) {
		this.isUsing = isUsing;
	}

	public TableOrder(String code, String name, int isUsing){
		this.mCode=code;
		this.mName=name;
		this.isUsing=isUsing;
	}

}
