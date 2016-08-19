package vn.app.android.ordermanager.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Custom SSLSocketFactory that provides a context
 * @author thaonp
 */
public class MySSLSocketFactory extends SSLSocketFactory {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * SSL (Secure Socket Layer) Context with instance TLS (Transport Layer Security).
	 */
	protected SSLContext mSslContext = SSLContext.getInstance("TLS");
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param truststore
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
		super(truststore);
		//Create TrustManager instance
		TrustManager trustManager = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		mSslContext.init(null, new TrustManager[] { trustManager }, null);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param context
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public MySSLSocketFactory(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
		super(null);
		mSslContext = context;
	}
	//-------------------------------------------------------------------------------------------------------------------
	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		return mSslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}
	//-------------------------------------------------------------------------------------------------------------------
	@Override
	public Socket createSocket() throws IOException {
		return mSslContext.getSocketFactory().createSocket();
	}
	//-------------------------------------------------------------------------------------------------------------------
}
