package vn.app.android.ordermanager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import vn.app.android.ordermanager.network.MySSLSocketFactory;

//import android.net.http.AndroidHttpClient;

/**
 * Network Ultilities
 * @author thaonp
 */
public class NetworkUtil {
	//-------------------------------------------------------------------------------------------------------------------
	public enum RequestMethod { GET, POST, PUT, DELETE }
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Logcat tag
	 */
	public static final String TAG = NetworkUtil.class.getName();
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Achieve the SSL Client from HttpClient
	 * @param client HttpClient instance
	 * @return SSL HttpClient
	 */
	public static HttpClient achieveSslClient(HttpClient client) {
		try {
			//Create TrustManager instance
			X509TrustManager trustManager = new X509TrustManager() { 
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			//Create SSL Context with TLS
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{trustManager}, null);
			//Create SSLSocketFactory instance and allow all host name verifier
			SSLSocketFactory sslSocketFactory = new MySSLSocketFactory(sslContext);
			sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			//Create ClientConnectionManager instance
			ClientConnectionManager clientConnectionManager = client.getConnectionManager();
			//Get SchemaRegistry and regist https schema with port 443 (TLS/SSL)
			SchemeRegistry schemaRegistry = clientConnectionManager.getSchemeRegistry();
			schemaRegistry.register(new Scheme("https", sslSocketFactory, 443));
			return new DefaultHttpClient(clientConnectionManager, client.getParams());
		} 
		catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
			return null;
		}
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get bitmap from url using URLConnection.
	 * @param url
	 * @return
	 */
	public static Bitmap getBimapByUrlConnection(String url) {
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
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get bitmap from Https URL.
	 * @param httpsUrl
	 * @return
	 */
	public static Bitmap getBitmapFromHttps(String httpsUrl) {
		try {
			HttpClient client = NetworkUtil.achieveSslClient(new DefaultHttpClient());
			HttpResponse response = client.execute(new HttpGet(httpsUrl));
			if(response != null) {
				HttpEntity entity = response.getEntity();
				if(entity != null) {
					return BitmapFactory.decodeStream(entity.getContent());
				}
			}
		}
		catch(Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get bitmap from URL using AndroidHttpClient
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapByAndroidHttpClient(String url) {
//		final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		DefaultHttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_OK) { 
				Log.w(TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
				return null;
			}
			final HttpEntity entity = response.getEntity();
			if(entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent(); 
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} 
				finally {
					if (inputStream != null) {
						inputStream.close();  
					}
					entity.consumeContent();
				}
			}
		} 
		catch(Exception e) {
			getRequest.abort();
			LogUtil.e(TAG, "Error while retrieving bitmap from " + url + ". " + e.getMessage(), e);
		} 
		finally {
			if(client != null) {
//			client.close();
			}
		}
		return null;
	}
	//-------------------------------------------------------------------------------------------------------------------
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
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get response data from Response object.
	 * @param response
	 * @return
	 */
	public static String getResponseData(HttpResponse response) throws Exception {
		String responseData = null;
		if(response != null) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = null;
				instream = entity.getContent();
				responseData = convertStreamToString(instream);
				instream.close();
			}
		}
		return responseData;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Convert input stream to String
	 * @param is InputStream
	 * @return
	 */
	public static String convertStreamToString(InputStream is) throws Exception {
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		is.close();
		return sb.toString();
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get ASCII content from HttpEntity
	 * @param entity
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String getAsciiContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
		int n = 1;
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		while(n > 0) {
			byte[] b = new byte[4096];
			n =  in.read(b);
			if(n > 0) {
				out.append(new String(b, 0, n));
			}
		}
		return out.toString();
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Execute Request with specific request URL
	 * @param request
	 * @param url
	 * @return
	 */
	public static HttpResponse executeRequest(HttpUriRequest request, String url) throws Exception {
		HttpClient client = NetworkUtil.achieveSslClient(new DefaultHttpClient());
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 20000);
		HttpConnectionParams.setSoTimeout(client.getParams(), 30000);
		return client.execute(request);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Execute request URL (API) with specific request method, headers and parameters.
	 * @param api
	 * @param method
	 * @param headers
	 * @param params
	 * @param encode
	 * @return
	 */
	public static HttpResponse executeRequest(String api, RequestMethod method, ArrayList<NameValuePair> headers, ArrayList<NameValuePair> params, String encode) throws Exception {
		HttpRequestBase request = null;
		if(method == RequestMethod.GET) {
			//Add parameters
			String combinedParams =  NetworkUtil.combineParameters(params, encode);
			request = new HttpGet(api + combinedParams);
			LogUtil.d(TAG, "GET: " + api + combinedParams);
			//Add headers
			if (headers != null) {
				request = (HttpGet) NetworkUtil.addHeaders(request, headers);
			}
		}
		else if(method == RequestMethod.POST) {
			request = new HttpPost(api);
			//Add headers
			if (headers != null) {
				request = (HttpPost) NetworkUtil.addHeaders(request, headers);
			}
			//Add parameters
			if (params != null) {
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(params, encode));
			}
		}
		else if(method == RequestMethod.DELETE) {
			request = new HttpDelete(api);
			//Add headers
			if (headers != null) {
				request = (HttpDelete) NetworkUtil.addHeaders(request, headers);
			}
			//Set parameters
			if(params != null && params.size() > 0) {
				BasicHttpParams basicParams = new BasicHttpParams();
				for (NameValuePair p : params) {
					basicParams.setParameter(p.getName(), p.getValue());
				}
				((HttpDelete) request).setParams(basicParams);
			}
		}
		return NetworkUtil.executeRequest(request, api);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Add header fields to request header
	 * @param request
	 * @param headers
	 * @return
	 */
	public static HttpRequestBase addHeaders(HttpRequestBase request, ArrayList<NameValuePair> headers) {
		if(headers == null) {
			headers = new ArrayList<NameValuePair>();
		}
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}
		return request;
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
	//-------------------------------------------------------------------------------------------------------------------
}
