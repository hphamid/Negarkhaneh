package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
 * CAUTION: this class implements ExtendedEndlessAdapter but it won't do anything meaningful! 
 * this purpose of this class is just to stop complier from nagging about unimplemented methods! :)s
 */
public class SimpleExtendedEndlessAdapater extends ExtendedEndlessAdapter {

	public SimpleExtendedEndlessAdapater(Context context, List<Object> initList) {
		super(context, initList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getCustomViewExtendedEndless(int position, View convertView,
											 ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getNormalViewExtendedEndless(int position, View convertView,
											 ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initListExtended(List<Object> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void ClearExtendedEndless() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCountEndless() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItemEndless(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getMoreEndless() {
		// TODO Auto-generated method stub

	}

	@Override
	public View getLoadingViewEndless(int position, View convertView,
									  ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
