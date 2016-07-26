package ir.abrstudio.negarkhaneh.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hamid on 7/26/16.
 *
 */

public class ImageLikeItem implements Serializable{

    @SerializedName("_id")
    private String id;
    @SerializedName("imageId")
    private String imageId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("creationDate")
    private String creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
