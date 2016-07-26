package ir.abrstudio.negarkhaneh.data;

/**
 * Created by hamid on 7/26/16.
 *
 */
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResponse implements Serializable{

    @SerializedName("_id")
    private String id;
    @SerializedName("uniqueId")
    private String uniqueId;
    @SerializedName("creationDate")
    private String creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
