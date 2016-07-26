package ir.abrstudio.negarkhaneh.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/*
 * CAUTION: this class implements listViewItem but it won't do anything meaningful! 
 * this purpose of this class is just to stop complier from nagging about unimplemented methods! :)s
 */
public abstract class SimpleListViewItem<DATA, EXTRA, ADAPTER extends BaseAdapter> extends ListViewItem<DATA , EXTRA, ADAPTER> {

	@Override
	protected View inflateView(LayoutInflater inflater, ViewGroup parent) {
		return inflater.inflate(this.getLayoutId(), parent, false);
	}

	
	@Override
	public void findViewItems() {
		
	}

	@Override
	public void setViewContent() {
		
	}

	@Override
	public void setViewListeners() {
		
	}

	protected abstract int getLayoutId();
}
