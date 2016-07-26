package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
 * CAUTION: this class implements ArrayExtendedEndlessAdapter but it won't do anything meaningful! 
 * this purpose of this class is just to stop complier from nagging about unimplemented methods! :)s
 */
public abstract class SimpleEndlessAdapter extends ArrayExtendedEndlessAdapter {

	public SimpleEndlessAdapter(Context context, List<Object> initList,
								int LoadingViewLayoutId, int FailedViewLayoutId) {
		super(context, initList, LoadingViewLayoutId, FailedViewLayoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getSimpleView(int position, View convertView, ViewGroup parent,
							  boolean isInit) {
		Class<? extends ListViewItem<Object, Object, SimpleEndlessAdapter>> cls = this.getListViewItem(position, isInit);
		return ListViewItem.getLayout(cls, getInflater(), parent, convertView,
				position, this.getItem(position),
				this.getExtra(position, isInit), this);
	}

	public Object getExtra(int position, boolean isInit) {
		return null;
	}

	public abstract Class<? extends ListViewItem<Object, Object, SimpleEndlessAdapter>> getListViewItem(int position, boolean isInit);
	
}
