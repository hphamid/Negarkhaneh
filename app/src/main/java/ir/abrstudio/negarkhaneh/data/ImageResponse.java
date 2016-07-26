package ir.abrstudio.negarkhaneh.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hamid on 7/26/16.
 *
 */
public class ImageResponse implements Serializable{
    @SerializedName("image")
    private ImageItem Image;
    @SerializedName("like")
    private ImageLikeItem like;

    public ImageItem getImage() {
        return Image;
    }

    public void setImage(ImageItem image) {
        Image = image;
    }

    public ImageLikeItem getLike() {
        return like;
    }

    public void setLike(ImageLikeItem like) {
        this.like = like;
    }
    public boolean hasBeenLiked(){
        return getLike() != null;
    }
}
