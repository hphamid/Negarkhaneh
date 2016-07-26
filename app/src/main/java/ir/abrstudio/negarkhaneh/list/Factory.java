package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/*
 * data is return type and extra is for extra information which is necessary to get data
 */
public abstract class Factory<DATA> extends ExtendedWeakObservable {
	public final static int DEFAULT_PAGE_SIZE = 5;

	public final static int ALREADY_LOADING = -100;



	private Object extra;
	public static int a;
	private String timeToken = null; // usually used for safe pagination
	private int lastErrorCode = 0;
	private boolean isDone = false;
	private List<DATA> data = new ArrayList<DATA>();
	private boolean isLoading = false;

	/*
	 * for calling callback on UI thread;
	 */
	protected class ExtendedGetDataCallbackUI extends
			ExtendedGetDataCallback<List<DATA>> {
		private transient Context context;

		public ExtendedGetDataCallbackUI(GetDataCallback<List<DATA>> callback,
				Context context) {
			super(callback);
			this.setContext(context);
		}

		public void setContext(Context context) {
			this.context = context;
		}

		@Override
		public void success(final List<DATA> data) {
			doSuccess(data);
			runOnUiThread(context, new Runnable() {
				@Override
				public void run() {
					ExtendedGetDataCallbackUI.super.success(data);

				}
			});
		}

		@Override
		public void failed(final int code, final String message) {
			doFailed(code, message);
			runOnUiThread(context, new Runnable() {
				@Override
				public void run() {
					ExtendedGetDataCallbackUI.super.failed(code, message);

				}
			});
		}
	}

	public Factory(Object extra) {
		this.setExtra(extra);
	}

	public Object getExtra() {
		return extra;
	}

	/*
	 * changing extra results resetting position
	 */
	public void setExtra(Object extra) {
		this.extra = extra;
		this.reset();
	}

	public void reset() {
		this.removeAll();
		this.setTimeToken(null);
	}

	public int getStart() {
		return this.data.size();
	}

	public String getTimeToken() {
		return timeToken;
	}

	public void setTimeToken(String timeToken) {
		this.timeToken = timeToken;
	}

	public int getLastErrorCode() {
		return this.lastErrorCode;
	}

	/*
	 * isDone returns true if no more data is available to load!
	 */
	public boolean isDone() {
		return this.isDone;
	}

	public void done(boolean isDone) {
		this.isDone = isDone;
		this.changeAndNotifyObservers();
	}

	protected void runOnUiThread(Context context, Runnable runnable) {
		Handler handler = new Handler(context.getMainLooper());
		handler.post(runnable);
	}

	/*
	 * callback won't run on UI thread
	 */
	public void getMore(int count, GetDataCallback<List<DATA>> callback,
			Context context) {
		this.getMoreUnsafe(count, new ExtendedGetDataCallback<List<DATA>>(
				callback) {
			@Override
			public void success(List<DATA> data) {
				isLoading = false;
				doSuccess(data);
				super.success(data);
			}

			@Override
			public void failed(int code, String message) {
				doFailed(code, message);
				isLoading = false;
				super.failed(code, message);
			}
		}, context);
	}

	/*
	 * callback won't run on UI thread
	 */
	public void getMoreDefault(GetDataCallback<List<DATA>> callback,
			Context context) {
		this.getMore(this.getDefaultCount(), callback, context);

	}

	protected void getMoreUnsafe(int count,
								 GetDataCallback<List<DATA>> callback, Context context) {
		if(isLoading == false){
			isLoading = true;
			if (!this.isDone()) {
				this.getData(getStart(), count, callback, context);
			}else{
				callback.success(null);
			}
		}else{
			callback.failed(ALREADY_LOADING, "already loading");
		}
	}

	protected void doSuccess(List<DATA> data) {
		isLoading = false;
		if (data != null && data.size() > 0) {
			this.putData(data);
		} else {
			done(true);
		}
	}

	/*
	 * data can't be null!
	 */
	protected void putData(List<DATA> data) {
		int start = this.getLoadedDataList().size();
		this.getLoadedDataList().addAll(data);
		this.changeAndNotifyObservers(new Range(start, this.getLoadedDataList().size()));
	}

	protected void removeAll() {
        Log.d("factory", "removed");
		int size = this.getLoadedDataList().size();
		this.getLoadedDataList().clear();
		this.done(false);
		this.changeAndNotifyObservers(new Range(0, size));
	}

	/*
	 * won't return null!
	 */
	public List<DATA> getLoadedDataList() {
		return this.data;
	}

	public void getMoreOnUiThread(int count,
								  GetDataCallback<List<DATA>> callback, final Context context) {
		this.getMoreUnsafe(count, new ExtendedGetDataCallbackUI(callback,
				context), context);
	}

	public void getMoreOnUiThreadDefault(GetDataCallback<List<DATA>> callback,
			Context context) {
		this.getMoreOnUiThread(this.getDefaultCount(), callback, context);
	}

	public int getDefaultCount() {
		return DEFAULT_PAGE_SIZE;
	}

	public boolean canGetItem(int position) {
		return position >= 0
				&& (this.itemIsLoaded(position) || (position < this.getStart()
						+ this.getDefaultCount() && !this.isDone()));
	}

	public boolean itemIsLoaded(int position) {
		return position < this.getStart() && position >= 0;
	}

	protected void doFailed(int code, String message) {
		isLoading = false;
		this.lastErrorCode = code;
	}

	public String getTitle(){
		return null;
	}
	
	/*f
	 * calling this function directly won't change any internal state in this
	 * class
	 */
	public abstract void getData(int start, int count,
								 final GetDataCallback<List<DATA>> callback, Context context);
}
