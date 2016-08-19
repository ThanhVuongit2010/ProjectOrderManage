package vn.app.android.ordermanager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Bitmap Utilities
 * @author thaonp
 *
 */
public class BitmapUtil {
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Logcat tag.
	 */
	public final static String TAG = BitmapUtil.class.getName();
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Compute the inSampleSize for BitmapFactory.Option to scale down bitmap size into required size.
	 * @param options
	 * @param requireWidth
	 * @param requireHeight
	 * @return
	 */
	public static int computeInSampleSize(BitmapFactory.Options options, int requireWidth, int requireHeight) {
		int inSampleSize = 1;
		final int height = options.outHeight;
		final int width = options.outWidth;
		if (height > requireHeight || width > requireWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while(((halfHeight/inSampleSize) > requireHeight) && ((halfWidth/inSampleSize) > requireWidth)) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
	public static int computeInSampleSize(InputStream inputStream, int requireWidth, int requireHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(inputStream, null, options);
		return computeInSampleSize(options, requireWidth, requireHeight);
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Decode bitmap file with required with and heigh in sample.
	 * @param bitmapFile
	 * @param requiredWidth
	 * @param requiredHeight
	 * @return
	 */
	public static Bitmap decodeFile(File bitmapFile, int requiredWidth, int requiredHeight) {
		try {
			//First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			FileInputStream fileInputStream = new FileInputStream(bitmapFile);
			BitmapFactory.decodeStream(fileInputStream, null, options);
			fileInputStream.close();

			//Comppute inSampleSize
			if(requiredWidth != -1 && requiredHeight != -1) {
				options.inSampleSize = computeInSampleSize(options, requiredWidth, requiredHeight);
				LogUtil.d(TAG, "inSampleSize: " + options.inSampleSize);
			}

			//Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			fileInputStream = new FileInputStream(bitmapFile);
			Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
			fileInputStream.close();

			return bitmap;
		} 
		catch (IOException e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Decode bitmap input stream with required with and height in sample.
	 * @param bitmapInputStream
	 * @param requiredWidth
	 * @param requiredHeight
	 * @param context
	 * @param uniqueName
	 * @return
	 */
//	public static Bitmap decodeStream(InputStream bitmapInputStream, int requiredWidth, int requiredHeight) {
//		try {
//			byte[] bytes = IOUtils.toByteArray(bitmapInputStream);
//			
//			//First decode with inJustDecodeBounds=true to check dimensions
//			final BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inJustDecodeBounds = true;
//			BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
//			
//			//Comppute inSampleSize
//			if(requiredWidth != -1 && requiredHeight != -1) {
//				options.inSampleSize = computeInSampleSize(options, requiredWidth, requiredHeight);
//				LogUtil.d(TAG, "inSampleSize: " + options.inSampleSize);
//			}
//
//			//Decode bitmap with inSampleSize set
//			options.inJustDecodeBounds = false;
//			return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
//		}
//		catch(Exception e) {
//			LogUtil.e(TAG, e.getMessage(), e);
//			return null;
//		}
//	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Copy InputStream to OutputStream for decoding file.
	 * @param inputStream
	 * @param outputStream
	 */
	public static void copyInputStreamToOutputStream(InputStream inputStream, OutputStream outputStream)
	{
		final int bufferSize = 1024;
		try
		{
			byte[] bytes = new byte[bufferSize];
			while(true)
			{
				int count = inputStream.read(bytes, 0, bufferSize);
				if(count == -1) { //Reach EOF
					break;
				}
				outputStream.write(bytes, 0, count);
			}
		}
		catch(Exception e){
			LogUtil.e(TAG, e.getMessage(), e);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------
	/**
	 * Get a circular (rounded) bitmap shape with the diameter is the smaller between target width and target height.
	 * @param bitmap
	 * @param width target width
	 * @param height target height
	 * @return Rounded (circular) bitmap width diameter is the smaller between target width and target height.
	 */
	public static Bitmap getRoundedBitmap(Bitmap bitmap, int width, int height) {
		int diameter = width < height ? width : height;
		Bitmap targetBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(width/2f, height/2f, diameter/2f, Path.Direction.CCW);
		canvas.clipPath(path);
		
		Bitmap sourceBitmap = bitmap;
		Rect destinationRect = new Rect(0, 0, width, height);
		Rect sourceRect = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());
		canvas.drawBitmap(sourceBitmap, sourceRect, destinationRect, null);
		return targetBitmap;
	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get byte array of bitmap
	 * @param bitmap
	 * @return
	 */
//	public static byte[] getBitmapByteArray(final Bitmap bitmap) {
//		int byteCount = BitmapUtil.getBitmapByteCount(bitmap);
//		ByteBuffer byteBuffer = ByteBuffer.allocate(byteCount);
//		bitmap.copyPixelsToBuffer(byteBuffer);
//		return byteBuffer.array();
//	}
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * Get bitmap byte count.
	 * @param bitmap
	 * @return
	 */
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
//	public static int getBitmapByteCount(final Bitmap bitmap) {
//	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {	//Level 12 and later
//	        return bitmap.getByteCount();
//	    }
//	    //Pre HoneyComb-MR1 (Level 12)
//	    return bitmap.getRowBytes() * bitmap.getHeight();
//	}
	//-------------------------------------------------------------------------------------------------------------------
}
