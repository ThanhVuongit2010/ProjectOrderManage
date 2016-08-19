package vn.app.android.ordermanager.worker;

import android.os.AsyncTask;

/**
 * Base callback for AsyncTask
 * @author thaonp
 *
 * @param <Params> Parameters
 * @param <Progress> Progress values
 * @param <Result> Result post to UI Thread
 */
public abstract class AsyncTaskCallback<Params, Progress, Result> {
	//-------------------------------------------------------------------------------------------------------------------
	protected AsyncTask<Params, Progress, Result> mAsyncTask;
	//-------------------------------------------------------------------------------------------------------------------
	public void doPreExcute() {
	}
	//-------------------------------------------------------------------------------------------------------------------
	public void doPostExecute(Result result) {
	}
	//-------------------------------------------------------------------------------------------------------------------
	public Result doInBackground(Params... params) {
		return null;
	}
	//-------------------------------------------------------------------------------------------------------------------
	public void doProgressUpdate(Progress... values) {
	}
	//-------------------------------------------------------------------------------------------------------------------
	public void doCancelled() {
	}
	//-------------------------------------------------------------------------------------------------------------------
	public void doCancelled(Result result) {
	}
	//-------------------------------------------------------------------------------------------------------------------
	public void attachHostAsyncTask(AsyncTask<Params, Progress, Result> asyncTask) {
		mAsyncTask = asyncTask;
	}
	//-------------------------------------------------------------------------------------------------------------------
}