/**
 * Created By hamid at May 13, 2014 11:27:07 PM
 * Project: UmanoFarsi
 */
package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Observable;
import java.util.Observer;

/**
 * @author hamid
 */
public abstract class ListViewItem<DATA, EXTRA, ADAPTER extends BaseAdapter> {
	private DATA data;
	private EXTRA extra;
	private int position;
	private View layout;
	private ADAPTER adapter;
	private Observer observer;

	public class UpdataUiObserver implements Observer {

		@Override
		public void update(Observable arg0, Object arg1) {
			ListViewItem.this.setViewContent();
		}

	}

	public void saveObserver(Observer observer) {
		this.observer = observer;
	}

	public Observer getObserver() {
		return this.observer;
	}

	public void setData(DATA data) {
		DATA temp = this.data;
		this.data = data;
		if (temp != null && temp != data) {
			this.removeObservers(data);
		}
		if (this.data != null) {
			this.setObservers();
		}
	}

	public DATA getData() {
		return this.data;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return this.position;
	}

	public void setExtra(EXTRA extra) {
		this.extra = extra;
	}

	public EXTRA getExtra() {
		return this.extra;
	}

	public ADAPTER getAdapter() {
		return this.adapter;
	}

	public View getView() {
		return layout;
	}

	public View init(LayoutInflater inflater, ViewGroup parent, DATA data,
					 EXTRA extra, ADAPTER adapter, int position) {
		this.changeData(data, extra, adapter, position);
		this.layout = this.inflateView(inflater, parent);
		this.findViewItems();
		return this.layout;
	}

	public void changeData(DATA data, EXTRA extra, ADAPTER adapter, int position) {
		this.adapter = adapter;
		this.setPosition(position);
		this.setData(data);
		this.setExtra(extra);
	}

	public Context getContext() {
		if (this.layout == null) {
			throw new IllegalStateException(
					"cannot get context from null view!");
		}
		return this.layout.getContext();
	}

	protected abstract View inflateView(LayoutInflater inflater,
										ViewGroup parent);

	public abstract void findViewItems();

	public abstract void setViewContent();

	public abstract void setViewListeners();

	/**
	 * implement this function to add observers
	 */
	public void setObservers() {
	}

	/**
	 * implement this function to remove observers
	 */
	public void removeObservers(DATA data) {
	}

	public void onItemClick(View view) {
		// nothing! :)
	}

	@SuppressWarnings("unchecked")
	public static <DATA, EXTRA, ADAPTER extends BaseAdapter> View getLayout(
			Class<? extends ListViewItem<DATA, EXTRA, ADAPTER>> cls,
			LayoutInflater inflater, ViewGroup parent, View convertView,
			int position, DATA data, EXTRA extra, ADAPTER adapter) {
		if (!ListViewItem.class.isAssignableFrom(cls)) {
			throw new IllegalStateException(
					"class must be child of ListViewItem");
		}
		View layout = convertView;
		ListViewItem<DATA, EXTRA, ADAPTER> item = null;
		if (layout != null) {
			Object temp = layout.getTag();
			if (temp != null && temp.getClass() == cls) {
				item = (ListViewItem<DATA, EXTRA, ADAPTER>) temp;
				item.changeData(data, extra, adapter, position);
			}
		}
		if (item == null) {
			try {
				item = (ListViewItem<DATA, EXTRA, ADAPTER>) cls.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new IllegalStateException("cannot instantiate object");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new IllegalStateException("cannot instantiate object");
			}
			layout = item
					.init(inflater, parent, data, extra, adapter, position);
			layout.setTag(item);
		}
		item.setViewListeners();
		item.setViewContent();
		return layout;
	}

}
