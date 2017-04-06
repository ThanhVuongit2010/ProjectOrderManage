package vn.app.android.ordermanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.app.android.ordermanager.rest.URLConnectionResponse;

/**
 * Created by Mr Vuong on 3/22/2017.
 */
public class NetworkULRConnectionUtil {
    public enum RequestMethod { GET, POST, PUT, DELETE }

    public static final String TAG = NetworkULRConnectionUtil.class.getName();

    /**
     * Get bitmap from url using URLConnection.
     * @param url
     * @return
     */
    public static Bitmap getBimapByUrl(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.connect();
            return BitmapFactory.decodeStream(connection.getInputStream() );
        }
        catch(Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return null;
        }
    }
    /**
     * Execute request URL (API) with specific request method, headers and parameters.
     * @param api
     * @param method
     * @param headers
     * @param params
     * @param encode
     * @return
     */
    public static URLConnectionResponse executeRequest(String api, RequestMethod method, ArrayList<NameValuePair> headers, ArrayList<NameValuePair> params, String encode) throws Exception {
        HttpURLConnection connection = null;
        URLConnectionResponse response = new URLConnectionResponse();
        String combinedParams =  combineParameters(params, encode);
        if(method == RequestMethod.GET) {
            URL url = new URL(api+combinedParams);
            LogUtil.d(TAG, "GET: " + api + combinedParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(headers != null){
              connection = addHeaders(connection, headers);
            }
        }
        else if(method == RequestMethod.POST) {
            URL url = new URL(api+combinedParams);
            LogUtil.d(TAG, "POST: " + api + combinedParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if(headers != null){
                connection = addHeaders(connection, headers);
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(combinedParams.substring(1));
            dStream.flush();
            dStream.close();
        }
        else if(method == RequestMethod.PUT) {
            URL url = new URL(api+combinedParams);
            LogUtil.d(TAG, "PUT: " + api + combinedParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            if(headers != null){
                connection = addHeaders(connection, headers);
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(combinedParams.substring(1));
            dStream.flush();
            dStream.close();
        }else if(method == RequestMethod.DELETE) {
            URL url = new URL(api+combinedParams);
            LogUtil.d(TAG, "DELETE: " + api + combinedParams);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            if(headers != null){
                connection = addHeaders(connection, headers);
            }
        }
        response.setStatusCode(connection.getResponseCode());
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        StringBuilder responseOutput = new StringBuilder();
        while((line = br.readLine()) != null ) {
            responseOutput.append(line);
        }
        br.close();
        response.setEntity(responseOutput.toString());
        return response;
    }
    /**
     * Combine parameter for GET or DELETE method.
     * @param parameters
     * @param encode encode string. eg: "UTF-8"
     * @return combined parameters
     * @throws Exception
     */
    public static String combineParameters(ArrayList<NameValuePair> parameters, String encode) throws Exception {
        String combinedParams = "";
        if(parameters != null) {
            if(parameters.size() == 0) {
                return combinedParams;
            }
            combinedParams += "?";
            for(NameValuePair p : parameters) {
                String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), encode);
                if(combinedParams.length() > 1) {
                    combinedParams += "&" + paramString;
                }
                else {
                    combinedParams += paramString;
                }
            }
        }
        return combinedParams;
    }
    /**
     * Add header fields to request header
     * @param connection
     * @param headers
     * @return
     */
    public static HttpURLConnection addHeaders(HttpURLConnection connection, ArrayList<NameValuePair> headers) {
        if(headers == null) {
            headers = new ArrayList<NameValuePair>();
        }
        for (NameValuePair h : headers) {
            connection.setRequestProperty(h.getName(), h.getValue());
        }
        return connection;
    }


    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Check network is connected or not. Should use
     * @param context
     * @return
     */
    public static boolean isNetworkOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
            else {
                netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if(netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        catch(Exception e){
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return false;
    }
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Check network is connected or not. Should use this method.
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return netInfo.isConnected();
        }
        catch(Exception e){
            LogUtil.e(TAG, e.getMessage(), e);
            return false;
        }
    }
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Get query map (parameter key & value) from URL query.
     * @param urlQuery
     * @return
     */
    public static Map<String, String> getQueryMap(String urlQuery)
    {
        Map<String, String> map = new HashMap<String, String>();
        if(urlQuery != null) {
            String[] parameters = urlQuery.split("&");
            for (String parameter : parameters)
            {
                String []pair = parameter.split("=");
                if(pair.length > 0) {
                    String name = pair[0];
                    String value = "";
                    if(pair.length > 1) {
                        value = pair[1];
                    }
                    map.put(name, value);
                }
            }
        }
        return map;
    }
    //-------------------------------------------------------------------------------------------------------------------
    /**
     * Get Query Map from URL.
     * @param url
     * @return
     */
    public static Map<String, String> getQueryMapFromUrl(String url) {
        Map<String, String> queryMap = new HashMap<String, String>();
        try {
            URL aURL = new URL(url);
            String urlQuery = aURL.getQuery();
            String urlRef = aURL.getRef();
            String query = urlQuery != null ? urlQuery : urlRef;
            queryMap = getQueryMap(query);
        }
        catch(MalformedURLException e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return queryMap;
    }

}
