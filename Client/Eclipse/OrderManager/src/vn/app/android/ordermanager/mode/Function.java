package vn.app.android.ordermanager.mode;

public class Function {

	/**
	 * Code function
	 */
	private String mCode;
	/**
	 * Name function
	 */
	private String mName;
	/**
	 * Branch using function
	 */
	private String mBranch;
	/**
	 * Url loading Image funtion
	 */
	private String mUrlImage;
	/**
	 * Date current Day/Month/Year
	 */
	private String mCurrenDateYMD;
	/**
	 * get code function
	 */
	public String getmCode() {
		return mCode;
	}
	/**
	 * set code function
	 */
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	/**
	 * get name function
	 */
	public String getmName() {
		return mName;
	}
	/**
	 * set name function
	 */
	public void setmName(String mName) {
		this.mName = mName;
	}
	/**
	 * get Branch function
	 */
	public String getmBranch() {
		return mBranch;
	}
	/**
	 * set Branch function
	 */
	public void setmBranch(String mBranch) {
		this.mBranch = mBranch;
	}
	/**
	 * get UrlImage function
	 */
	public String getmUrlImage() {
		return mUrlImage;
	}
	/**
	 * set UrlImage function
	 */
	public void setmUrlImage(String mUrlImage) {
		this.mUrlImage = mUrlImage;
	}

	public String getmCurrenDateYMD() {
		return mCurrenDateYMD;
	}

	public void setmCurrenDateYMD(String mCurrenDateYMD) {
		this.mCurrenDateYMD = mCurrenDateYMD;
	}

	public static class Builder {
		private String mCode;
		private String mName;
		private String mBranch = null;
		private String mUrlImage = null;
		private String mCurrenDateYMD = null;

		public Builder(String code, String name) {
			this.mCode = code;
			this.mName = name;
		}

		public Builder Branch(String branch) {
			this.mBranch = branch;
			return this;
		}

		public Builder UrlImage(String urlImage) {
			this.mUrlImage = urlImage;
			return this;
		}

		public Builder CurrenDateYMD(String currentDate) {
			this.mCurrenDateYMD = currentDate;
			return this;
		}

		public Function build() {
			return new Function(this);
		}
	}

	public Function(Builder builder){
		this.mCode = builder.mCode;
		this.mName = builder.mName;
		this.mBranch = builder.mBranch;
		this.mUrlImage = builder.mUrlImage;
		this.mCurrenDateYMD = builder.mCurrenDateYMD;
	}

}
