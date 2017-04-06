package vn.app.android.ordermanager.state;


/**
 * Common Flags
 * @author VuongNT
 */
public class Flag {
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Check that is use developer server or product server.
	 */
	public static boolean USED_SERVER_DEV = false;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Check that go to Test Fragment to test something or not.
	 */
	public static boolean OPEN_TEST_FRAGMENT = false;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Test Fragment Tag Name.
	 */
//	public static String TEST_FRAGMENT_TAG = TestFunctionFragment.TAG;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get Test GCM Registration ID with <code>Flag.GCM_TEST_SENDER_ID</code> sender ID.
	 */
	public static boolean GCM_USE_GETREGSTRATION_ID = false;
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * GCM Test Sender ID.
	 */
	public static String GCM_TEST_SENDER_ID = "173475553378";
	//--------------------------------------------------------------------------------------------------------------------
}
