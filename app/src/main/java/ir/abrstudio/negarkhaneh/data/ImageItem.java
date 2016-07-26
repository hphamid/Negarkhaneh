package ir.abrstudio.negarkhaneh.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hamid on 7/26/16.
 *
 */

public class ImageItem implements Serializable{

    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("path")
    private String path;
    @SerializedName("likeCount")
    private int likeCount;
    @SerializedName("released")
    private boolean released;
    @SerializedName("creationDate")
    private String creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
