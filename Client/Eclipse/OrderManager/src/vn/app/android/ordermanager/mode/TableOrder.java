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
	public static class Builder{
		private String code;
		private String name;
		private String isUsing;
		
		public Builder(String code, String name, String isUsing){
			this.code = code;
			this.name = name;
			this.isUsing = isUsing;
		}
		
	}

}
