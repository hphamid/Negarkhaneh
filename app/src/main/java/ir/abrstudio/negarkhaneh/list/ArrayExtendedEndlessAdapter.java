package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayExtendedEndlessAdapter extends
		ExtendedEndlessAdapter {
	private List<Object> data = new ArrayList<Object>();
	private final int LoadingViewLayoutId;
	private final int FailedViewLayoutId;
	/*
	 * must pass loading and failed layout id
	 * if you want to ignore failed view you can easily pass 0 as failed layout id;
	 */
	public ArrayExtendedEndlessAdapter(Context context, List<Object> initList,
									   int LoadingViewLayoutId, int FailedViewLayoutId) {
		super(context, initList);
		this.LoadingViewLayoutId = LoadingViewLayoutId;
		this.FailedViewLayoutId = FailedViewLayoutId;
	}

	@Override
	public View getCustomViewExtendedEndless(int position, View convertView,
											 ViewGroup parent) {
		return this.getSimpleView(position, convertView, parent, true);
	}

	@Override
	public View getNormalViewExtendedEndless(int position, View convertView,
											 ViewGroup parent) {
		return this.getSimpleView(position, convertView, parent, false);
	}

	@Override
	public void initListExtended(List<Object> list) {
		this.CopyList(this.data, list);
	}

	@Override
	protected void ClearExtendedEndless() {
		this.data.clear();
	}

	@Override
	public int getCountEndless() {
		return this.data.size();
	}
	
	public int getArraySize(){
		return this.data.size();
	}

	@Override
	public Object getItemEndless(int position) {
		return this.getArrayItem(position);
	}
	
	public Object getArrayItem(int position){
		return this.data.get(position);
	}
	
	public void addItem(Object item){
		this.data.add(item);
	}
	
	public void addAllItems(Collection<? extends Object> items){
		this.data.addAll(items);
	}

	@Override
	public View getLoadingViewEndless(int position, View convertView,
									  ViewGroup parent) {
		return this.checkViewAndInflateIfNeeded(convertView,
				LoadingViewLayoutId);
	}

	@Override
	public boolean failedViewIsAvailable() {
		return this.FailedViewLayoutId > 0;
	}

	@Override
	public View getLoadingFailedViewEndless(int position, View convertView,
											ViewGroup parent) {
		return this
				.checkViewAndInflateIfNeeded(convertView, FailedViewLayoutId);
	}

	private View checkViewAndInflateIfNeeded(View convertView, int layoutId) {
		View toReturn = convertView;
		if (toReturn == null) {
			toReturn = this.getInflater().inflate(layoutId, null);
		}
		return toReturn;
	}

	@Override
	public int getItemViewTypeEndless(int position) {
		return this.getItemViewTypeSimple(position, this.isInit(position));
	}

	/*
	 * actually isInit is extra information and can be obtained using isInit
	 * function! :)
	 * CAUTION: don't forget to override this function if you need to return more than one view type!!
	 */
	public int getItemViewTypeSimple(int position, boolean isInit) {
		return 0;
	}

	public abstract View getSimpleView(int position, View convertView,
									   ViewGroup parent, boolean isInit);
}
