package ir.abrstudio.negarkhaneh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by hamid on 7/29/15.
 */
public class ImagesAdapter extends BaseAdapter {
    private final Context context;
    List<Image> images;

    private class ViewHolder{
        public ImageView image;
    }

    public ImagesAdapter(Context context, List<Image> images){
        this.context = context;
        this.images = images;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return this.images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View toReturn = view;
        Image image = (Image) this.getItem(i);
        if(toReturn == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            toReturn = inflater.inflate(R.layout.grid_image_item, viewGroup, false);

        }
        ViewHolder holder = null;
        if(toReturn.getTag() != null && toReturn.getTag() instanceof ViewHolder){
            holder = (ViewHolder)toReturn.getTag();
        }
        if(holder == null){
            holder = new ViewHolder();
            holder.image = (ImageView) toReturn.findViewById(R.id.tile_image);
            toReturn.setTag(holder);
        }
        Log.d("test", "" + image.getImageAddress());
        Glide.with(getContext()).load(image.getImageAddress()).asBitmap().fitCenter().into(holder.image);
        return toReturn;
    }
}
