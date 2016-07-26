package ir.abrstudio.negarkhaneh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.abrstudio.negarkhaneh.list.Factory;
import ir.abrstudio.negarkhaneh.list.FactoryExtendedEndlessAdapter;

public class ImageListAdapter extends FactoryExtendedEndlessAdapter<Image> {

	public ImageListAdapter(Context context, List<Object> initList,
							int LoadingViewLayoutId, int FailedViewLayoutId,
							Factory<Image> dataFactory) {
		super(context, initList, LoadingViewLayoutId, FailedViewLayoutId, dataFactory);
	}

	public ImageListAdapter(Context context, List<Object> initList,
							int LoadingViewLayoutId, int FailedViewLayoutId,
							Factory<Image> dataFactory, int pageSize) {
		super(context, initList, LoadingViewLayoutId, FailedViewLayoutId, dataFactory,
				pageSize);
	}

	@Override
	public View getSimpleView(int position, View convertView, ViewGroup parent,
							  boolean isInit) {
		View toReturn = convertView;
		Image image = (Image) this.getItem(position);
		if (toReturn == null || toReturn.getTag() == null || !(toReturn.getTag() instanceof ViewHolder)) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			toReturn = inflater.inflate(R.layout.grid_image_item, parent,
					false);

		}
		ViewHolder holder = null;
		holder = (ViewHolder) toReturn.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.image = (ImageView) toReturn.findViewById(R.id.tile_image);
			toReturn.setTag(holder);
		}
		if(holder.image != null)
			Glide.with(getContext()).load(image.getImageThumbnail()).asBitmap()
				.fitCenter().into(holder.image);
		return toReturn;
	}
	private class ViewHolder {
		public ImageView image;
	}

}
