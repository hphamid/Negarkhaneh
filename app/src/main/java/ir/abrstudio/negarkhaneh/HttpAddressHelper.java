package ir.abrstudio.negarkhaneh;

/**
 * Created by hamid on 7/26/16.
 *
 */

public class HttpAddressHelper {
    public final static String mainAddress = "http://negarkhaneh.abrstudio.ir";
    public final static String mainApiAddress =  mainAddress + "/api";
    public String getUserAddress(String uniqueId){
        return mainAddress + "/getUser/" + uniqueId;
    }
    public String getImagePath(String imagePath){
        return mainAddress + "/image/" + imagePath;
    }
    public String getLikeAddress(String imageId){
        return mainApiAddress + "/like/" + imageId;
    }
    public String getUserLikedImages(int start, int count){
        return mainApiAddress + "/favs/" + start + "/" + count;
    }
    public String getNewImages(int start, int count){
        return mainApiAddress + "/new/" + start + "/" + count;
    }
    public String getPopularImages(int start, int count){
        return mainApiAddress + "/popular/" + start + "/" + count;
    }
}
