package vn.app.android.ordermanager.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

/**
 * File Utilites
 * @author thaonp
 */
public class FileUtil {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Logcat TAG
	 */
	public static final String TAG = FileUtil.class.getName();
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * IO buffer size about 8MB
	 */
	public static final int IO_BUFFER_SIZE = 8 * 1024;
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Private constructor to prevent instantiating this class.
	 */
	private FileUtil() {

	};
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Is external storage removable
	 * @return
	 */
	public static boolean isExternalStorageRemovable() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get external cache directory
	 * @param context
	 * @return
	 */
	public static File getExternalCacheDir(Context context) {
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir();
		}
		//Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Has external cache directory
	 * @return
	 */
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath = context.getCacheDir().getPath();
		String externalStorageState = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(externalStorageState) || !FileUtil.isExternalStorageRemovable()){
			cachePath = FileUtil.getExternalCacheDir(context).getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Convert input stream to byte array.
	 * @param inputStream
	 * @return
	 */
	public static byte[] convertInputStreamToByteArray(InputStream inputStream) {
		byte[] bytes = null;
		try {
			int count;
			byte data[] = new byte[1024];
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			while((count = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, count);
			}
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
			inputStream.close();
			bytes = byteArrayOutputStream.toByteArray();
		}
		catch (IOException e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return bytes;
	}
	//-------------------------------------------------------------------------------------------------------------------
}
