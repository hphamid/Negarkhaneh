package ir.abrstudio.negarkhaneh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import ir.abrstudio.negarkhaneh.list.Factory;


public class GridListFragment extends Fragment {

    private BaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_list, container, false);
        GridView imageList = (GridView) rootView.findViewById(R.id.images);
		adapter = new ImageListAdapter(getActivity(), null,
				R.layout.include_loading_view, 0,
				(Factory<Image>) getArguments().get("factory"));
//        adapter = new ImagesAdapter(this.getActivity(), getImageList());
        imageList.setAdapter(adapter);
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Image toSend = (Image) adapter.getItem(i);
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra("image", toSend);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }

//    public List<Image> getImageList(){
//        List<Image> toReturn = new ArrayList<Image>();
//        for(int i = 1; i <= 42  ; i++) {
//            Image toAdd = new Image();
//            toAdd.setImageTitle("background" + i);
//            toAdd.setImageAddress(this.getResources().getIdentifier(toAdd.getImageTitle(),
//                    "drawable", this.getActivity().getPackageName()));
//
//            toReturn.add(toAdd);
//        }
//        return toReturn;
//    }
}
