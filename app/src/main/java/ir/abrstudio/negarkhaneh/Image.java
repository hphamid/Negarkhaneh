package ir.abrstudio.negarkhaneh;

import android.content.Context;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;

import ir.abrstudio.negarkhaneh.data.ImageLikeItem;
import ir.abrstudio.negarkhaneh.data.ImageResponse;

public class Image implements Serializable {
    private ImageResponse imageEntity;

    public void setImageEntity(ImageResponse entity) {
        this.imageEntity = entity;
    }

    public String getImageTitle() {
        return imageEntity.getImage().getName();
    }

    public String getImageAddress() {
        return NetworkHelper.getInstance().getAddressHelper().getImagePath(imageEntity.getImage().getPath());
    }

    public String getImageThumbnail() {
        return NetworkHelper.getInstance().getAddressHelper().getImagePath(imageEntity.getImage().getPath());
    }

    public void Like(Context context) {
        if (!this.hasLiked()) {
            imageEntity.setLike(new ImageLikeItem()); //just to make better ui response
            UserInfo.getInstance().getUserId(context, new UserInfo.UserCallback() {
                @Override
                public void done(String userid) {
                    RequestBody reqbody = RequestBody.create(null, new byte[0]); //empty post body
                    Request request = new Request.Builder().url(NetworkHelper.getInstance()
                            .getAddressHelper().getLikeAddress(imageEntity.getImage().getId()))
                            .post(reqbody).addHeader("UserId", userid).build();
                    NetworkHelper.getInstance().getClient().newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            imageEntity.setLike(null);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                imageEntity.setLike(null);
                            }
                        }
                    });
                }
            });
        }
    }

    public boolean hasLiked() {
        return imageEntity.hasBeenLiked();
    }

}
