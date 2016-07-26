/**
 * Created By hamid 
 * Project: Jomlak
 */
package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class EndlessAdapter extends BaseAdapter {
	public final static int MINIMUM_AVAILABLE_VIEW_TYPE_NUMBER = 3;
	public final static int LOADING_VIEW_TYPE = 0;
	public final static int EMPTY_LOADING_VIEW_TYPE = 1;
	public final static int LOADING_FAILED_TYPE = 2;

	protected transient Context context;
	private transient LayoutInflater inflater;
	
	private int processing = 0;
	private int isEndless = 1;
	private int isFailed = 0;
	private int size = 0;

	public EndlessAdapter(Context context) {
		this.setContext(context);
	}

	public Context getContext() {
		if(context == null){
			throw new IllegalStateException("context is null set context before using it!");
		}
		return context;
	}
	
	public void setContext(Context context){
		this.context = context;
		this.inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public LayoutInflater getInflater() {
		if(inflater == null){
			throw new IllegalStateException("inflater is null call setContext before calling getInflater!");
		}
		return inflater;
	}

	private int calculateSize() {
		if (this.isEndless()) {
			return this.getCountEndless() + 1;
		}
		return this.getCountEndless();
	}

	@Override
	public int getCount() {
		if (this.isProcessing()) {
			return this.size;
		}
		return this.calculateSize();
	}

	@Override
	public Object getItem(int position) {
		if (position < this.getCountEndless()) {
			return this.getItemEndless(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getInternalCustomViewType(int position) {
		if (this.needToShowCustomView(position)) {
			if (this.hasFailed()) {
				if (this.failedViewIsAvailable()) {
					return LOADING_FAILED_TYPE;
				}
				return IGNORE_ITEM_VIEW_TYPE;
			} else {
				if (this.isEmpty()) {
					return EMPTY_LOADING_VIEW_TYPE;
				} else {
					return LOADING_VIEW_TYPE;
				}
			}
		}
		throw new IllegalStateException("No Custom View available");
		// return MINIMUM_AVAILABLE_VIEW_TYPE_NUMBER;
	}

	@Override
	public int getItemViewType(int position) {
		if (this.needToShowCustomView(position)) {
			return this.getInternalCustomViewType(position);
		}
		return getExtraViewType(this.getItemViewTypeEndless(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.checkForloading(position);
		int type = this.getItemViewType(position);
		if (type >= MINIMUM_AVAILABLE_VIEW_TYPE_NUMBER) {
			if (position < this.getCountEndless()) {
				return this.getViewEndless(position, convertView, parent);
			}
		} else {
			return this.getCustomView(type, position, convertView, parent);
		}
		return getLoadingViewEndless(position, convertView, parent);
	}

	public void checkForloading(int position) {
		if (this.needToGetMore(position)) {
			this.getMore();
		}
	}

	public boolean needToGetMore(int position) {
		return position >= this.getCountEndless() - this.getMarginEndless()
				&& this.isEndless() && !this.hasFailed();
	}

	private View getCustomView(int type, int position, View convertView,
							   ViewGroup parent) {
		switch (type) {
		case EMPTY_LOADING_VIEW_TYPE:
			return this.getEmptyLoadingViewEndless(position, convertView,
					parent);
		case LOADING_VIEW_TYPE:
			return this.getLoadingViewEndless(position, convertView, parent);
		case LOADING_FAILED_TYPE:
			return this.getLoadingFailedViewEndless(position, convertView,
					parent);
		default:
			throw new IllegalStateException("no View found for type: " + type);
		}
	}

	public boolean needToShowCustomView(int position) {
		return (position == this.getCountEndless() && this.isEndless());
	}

	public synchronized void getMore() {
		if (!this.isProcessing()) {
			this.setProcessing();
			this.getMoreEndless();
		}
	}

	public boolean isProcessing() {
		return this.processing == 1;
	}

	public void setProcessing() {
		this.size = this.calculateSize();
		this.processing = 1;
		this.notifyDataSetChanged();
	}

	public void dataDone() {
		this.isEndless = 0;
	}

	public void makeEndless() {
		this.isEndless = 1;
        this.isFailed = 0;
	}

	public boolean isEndless() {
		return this.isEndless == 1;
	}

	public boolean isEmpty() {
		return (this.getCountEndless() == 0);
	}

	public boolean hasFailed() {
		return this.isFailed == 1;
	}

	public void failed() {
		if (this.failedViewIsAvailable()) {
			this.isFailed = 1;
		} else {
			this.dataDone();
		}

	}

	public void unsetFailed() {
		if (this.failedViewIsAvailable()) {
			this.isFailed = 0;
		} else {
			this.makeEndless();
		}
	}

	/*
	 * call this function from ui thread please! :P
	 */
	public void retry() {
		this.checkMainThread();
		this.unsetFailed();
		this.notifyDataSetChanged();
		this.getMore();
	}

	/*
	 * call this function from ui thread please! :P
	 */
	public synchronized void dataProcessDone() {
		this.checkMainThread();
		this.processing = 0;
		this.size = this.calculateSize();
		this.notifyDataSetChanged();
	}

	private void checkMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalStateException(
					"dataProcessDone Function must be called from UI thread!");
		}
	}

	/*
	 * call from ui thread please! :)
	 */
	public void resetData() {
		this.makeEndless();
		this.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getViewTypeCount() {
		return this.getViewTypeCountEndless()
				+ MINIMUM_AVAILABLE_VIEW_TYPE_NUMBER; // for loading and failed!
														// :)
	}

	public int getViewTypeCountEndless() {
		return 1;
	}

	
	public int getItemViewTypeEndless(int position) { 
		return 0;
	}

	public boolean failedViewIsAvailable() {
		return false;
	}

	public View getEmptyLoadingViewEndless(int position, View convertView,
										   ViewGroup parent) {
		return this.getLoadingViewEndless(position, convertView, parent);
	}

	public View getLoadingFailedViewEndless(int position, View convertView,
											ViewGroup parent) {
		throw new IllegalStateException("Unimplemented function!");
	}

	private static int getExtraViewType(int type) {
		if (type < 0) {
			throw new IllegalAccessError("type must be positive");
		} else {
			return type + MINIMUM_AVAILABLE_VIEW_TYPE_NUMBER;
		}
	}
	
	/*
	 * override this method if you want! :P
	 */
	public void itemClicked(View item, int position){
		// noThing! :)
	}
	
	public abstract int getCountEndless();

	public abstract Object getItemEndless(int position);

	public abstract void getMoreEndless();

	public int getMarginEndless(){
		return 0;
	};

	public abstract View getViewEndless(int position, View convertView,
										ViewGroup parent);

	public abstract View getLoadingViewEndless(int position, View convertView,
											   ViewGroup parent);

	public abstract void clear();
}
