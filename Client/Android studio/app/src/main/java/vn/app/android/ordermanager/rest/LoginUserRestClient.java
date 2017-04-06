package vn.app.android.ordermanager.rest;

import android.content.Context;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import vn.app.android.ordermanager.mode.User;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.NetworkULRConnectionUtil;
import vn.app.android.ordermanager.util.ResultUtil;
import vn.app.android.ordermanager.util.StringUtil;
import vn.app.android.ordermanager.worker.RestAsyncTaskCallback;

import static vn.app.android.ordermanager.util.TaskUtil.getBadRequestParamMessages;

/**
 * Created by Mr Vuong on 3/18/2017.
 */
public class LoginUserRestClient extends  AsyncRestClient{


    public enum RequestMethod { GET, POST, PUT, DELETE }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Log cat tag.
     */
    public static final String TAG = LoginUserRestClient.class.getName();
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Extra information to call API.
     */
    protected HashMap<String, Object> mExtraInfo = new HashMap<String, Object>();
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Callback
     */
    protected RestAsyncTaskCallback mCallback;

    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Company code <br/>
     * @param value
     * @return
     */
    public LoginUserRestClient CompanyCode(String value) {
        mExtraInfo.put("compcode", value);
        return this;
    }
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * password. <br/> <br/>
     * @param value
     * @return
     */
    public LoginUserRestClient password(String value) {
        mExtraInfo.put("password", value);
        return this;
    }
    /**
     * password. <br/> <br/>
     * @param value
     * @return
     */
    public LoginUserRestClient deviceToken(String value) {
        mExtraInfo.put("deviceToken", value);
        return this;
    }
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * User name <br/> <br/>
     * @param value
     * @return
     */
    public LoginUserRestClient userName(String value) {
        mExtraInfo.put("username", value);
        return this;
    }
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Set callback.
     * @param callback
     * @return
     */
    public LoginUserRestClient setCallback(RestAsyncTaskCallback callback) {
        mCallback = callback;
        if(mCallback != null) {
            mCallback.attachHostAsyncTask(this);
        }
        return this;
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor.
     * @param context
     */
    public LoginUserRestClient(Context context, RestAsyncTaskCallback callback) {
        super(context);
        setCallback(callback);
    }
    //-------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onPreExecute() {
        if(mCallback != null) {
            mCallback.doPreExcute();
        }
    }
    //--------------------------------------------------------------------------------------------------------------------
    @Override
    protected HashMap<String, Object> doInBackground(Object... params) {
        setEncode(HTTP.UTF_8);
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<NameValuePair> headers = new ArrayList<NameValuePair>();
        ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
        try {
            addHeaders(headers);
            addParameters(parameters);
            URLConnectionResponse response = NetworkULRConnectionUtil.executeRequest(ServerApi.API_LOGIN_USER,NetworkULRConnectionUtil.RequestMethod.POST,headers,parameters,getEncode());
            if(response != null) {
                String responseData = response.getEntity();
               int statusCode = response.getStatusCode();
                ResultUtil.putStatusCode(results, statusCode);
                if(StringUtil.isEmpty(responseData)) {
                    ResultUtil.putError(results, "Response data (Code=" + statusCode + "): null or empty");
                }
                else {
                    LogUtil.d(TAG, "Register User Response Data-> " + responseData);
                    if(statusCode == HttpStatus.SC_OK) {	//200 code
                        processResponseData(results, responseData);
                    }
                    else {	//Not CREATED status
                        processInvalidCodeStatus(results, responseData, headers, parameters);
                    }
                }
            }
            else {	//NULL Response
                ResultUtil.putError(results, "Response Object: NULL");
            }
        }
        catch(Exception e) {
            if(e instanceof SocketTimeoutException) {
            }
            else if(e instanceof ConnectTimeoutException) {
            }
            ResultUtil.putError(results, "Error Occur");
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return results;
    }
    //-------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCancelled() {
        if(mCallback != null) {
            mCallback.doCancelled();
        }
        super.onCancelled();
    }
    //-------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCancelled(HashMap<String, Object> results) {
        if(mCallback != null) {
            mCallback.doCancelled(results);
        }
        super.onCancelled(results);
    }
    //-------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onPostExecute(HashMap<String, Object> result) {
        if(mCallback != null) {
            mCallback.doPostExecute(result);
        }
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Add headers.
     * @param headers
     */
    private void addHeaders(ArrayList<NameValuePair> headers) {
//		headers.add(new BasicNameValuePair("LOGIN-HASH", (String) mExtraInfo.get("LOGIN-HASH")));
        headers.add(new BasicNameValuePair("USER-AGENT", "Mozilla/5.0"));
        headers.add(new BasicNameValuePair("ACCEPT-LANGUAGE", "en-US,en;0.5"));
        LogUtil.logNameValuePairParameter(TAG, headers, "");
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Add parameters.
     * @param parameters
     */
    private void addParameters(ArrayList<NameValuePair> parameters) {
        parameters.add(new BasicNameValuePair("compcode",mExtraInfo.get("compcode").toString()));
        parameters.add(new BasicNameValuePair("username",mExtraInfo.get("username").toString()));
        parameters.add(new BasicNameValuePair("password",mExtraInfo.get("password").toString()));
        parameters.add(new BasicNameValuePair("deviceToken",mExtraInfo.get("deviceToken").toString()));
        LogUtil.logNameValuePairParameter(TAG, parameters, "");
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Process response data.
     * @param results
     * @param responseData
     * @throws JSONException
     */
    private void processResponseData(HashMap<String, Object> results, String responseData) throws JSONException {
//        JSONArray userarr = new JSONArray(responseData);
        JSONObject userObj = new JSONObject(responseData);
        User user = new User().fromJsontoObject(userObj);
        results.put("user", user);
    }
    //--------------------------------------------------------------------------------------------------------------------
    /**
     * Process Invalid Code Status
     * @param results
     * @param responseData
     * @throws JSONException
     */
    private void processInvalidCodeStatus(final HashMap<String, Object> results, String responseData, ArrayList<NameValuePair> headers, ArrayList<NameValuePair> parameters) throws JSONException {
        String messageKey = "msg";
        JSONObject obj = new JSONObject(responseData);
        String message = obj.optString(messageKey);
        int statusCode = ResultUtil.getStatusCode(results);
        switch(statusCode) {
            case HttpStatus.SC_UNAUTHORIZED:	//401
                ResultUtil.putError(results, obj.optString(messageKey));
                break;
            case HttpStatus.SC_BAD_REQUEST:	//400
                String badMessage = getBadRequestParamMessages(obj, headers, parameters, messageKey);
                if(!StringUtil.isEmpty(message)) {
                    message += "\n";
                }
                message += badMessage;
                ResultUtil.putError(results, message);
                break;
            default:
                ResultUtil.putError(results, message);
                break;
        }
    }
}
