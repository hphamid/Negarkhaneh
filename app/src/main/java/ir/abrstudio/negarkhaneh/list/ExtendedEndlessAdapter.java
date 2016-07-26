/**
 * Created By hamid 
 * Project: Jomlak
 */
package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedEndlessAdapter extends EndlessAdapter {
	private List<Object> initList;

	public ExtendedEndlessAdapter(Context context, List<Object> initList) {
		super(context);
		this.initList = initList;
		if (this.initList == null) {
			this.initList = new ArrayList<Object>();
		}
		this.initListExtended(this.initList);
	}

	protected void CopyList(List<Object> des, List<?> source) {
		if (source != null && des != null) {
			for (Object item : source) {
				des.add(item);
			}
		}
	}

	@Override
	public int getViewTypeCountEndless() {
		return this.getViewTypeCountExtended()
				+ this.getViewTypeCountInitExtended();
	}

	@Override
	public int getItemViewTypeEndless(int position) {
		if (position < this.initList.size()) {
			return 1;
		}
		return 0;
	}

	public boolean isInit(int position) {
		if (position < this.initList.size()) {
			return true;
		}
		return false;
	}

	@Override
	public View getViewEndless(int position, View convertView, ViewGroup parent) {
		if (position < this.initList.size()) {
			return getCustomViewExtendedEndless(position, convertView, parent);
		}
		return getNormalViewExtendedEndless(position, convertView, parent);
	}

	public abstract View getCustomViewExtendedEndless(int position,
													  View convertView, ViewGroup parent);

	public abstract View getNormalViewExtendedEndless(int position,
													  View convertView, ViewGroup parent);

	public List<Object> getInitList() {
		return this.initList;
	}

	@Override
	public void clear() {
		this.ClearExtendedEndless();
		this.initListExtended(this.initList);
		this.notifyDataSetChanged();
	}

	protected int getViewTypeCountExtended() {
		return 1;
	}

	protected int getViewTypeCountInitExtended() {
		return 1;
	}

	public abstract void initListExtended(List<Object> list);

	protected abstract void ClearExtendedEndless();

}
