package ir.abrstudio.negarkhaneh.factory;

import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ir.abrstudio.negarkhaneh.Image;
import ir.abrstudio.negarkhaneh.NetworkHelper;
import ir.abrstudio.negarkhaneh.UserInfo;
import ir.abrstudio.negarkhaneh.data.ImageResponse;
import ir.abrstudio.negarkhaneh.list.Factory;
import ir.abrstudio.negarkhaneh.list.GetDataCallback;


public class MyImagesFactory extends Factory<Image> implements Serializable {


    public MyImagesFactory(Object extra) {
        super(extra);
    }

    @Override
    public void getData(final int start, final int count,
                        final GetDataCallback<List<Image>> callback, Context context) {
        UserInfo.getInstance().getUserId(context, new UserInfo.UserCallback() {

            @Override
            public void done(String userid) {
                Log.d("userId", "id is " + userid);
                Request request = new Request.Builder().url(NetworkHelper.getInstance()
                        .getAddressHelper().getUserLikedImages(start, count)).addHeader("UserId", userid).build();
                NetworkHelper.getInstance().getClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        callback.failed(0, e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            callback.failed(response.code(), "");
                            return;
                        }
                        Gson gson = new Gson();
                        List<ImageResponse> imageResponses = gson.fromJson(response.body().charStream(),
                                new TypeToken<List<ImageResponse>>() {
                                }.getType());
                        List<Image> toReturn = new ArrayList<Image>();
                        if (imageResponses != null) {
                            for (ImageResponse image : imageResponses) {
                                Image toAdd = new Image();
                                toAdd.setImageEntity(image);
                                toReturn.add(toAdd);
                            }
                        }
                        callback.success(toReturn);
                    }
                });
            }
        });
    }

}
