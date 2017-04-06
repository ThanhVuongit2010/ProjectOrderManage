package vn.app.android.ordermanager.error;


/**
 * Rest Errors
 * @author thaonp
 */
public class RestError {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Exception when process in background.
	 */
	public static final int EXCEPTION_DO_IN_BACKGROUND = 0;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Response data is empty.
	 */
	public static final int EMPTY_DATA = 10;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Response Object NULLl.
	 */
	public static final int NULL_RESPONSE = 11;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Can not get response data from Response object.
	 */
	public static final int NULL_RESPONSE_DATA = 12;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Response status code is not a valid code defined by Server side developers.
	 */
	public static final int STATUS_CODE_NOT_VALID = 100;
	//--------------------------------------------------------------------------------------------------------------------
}
