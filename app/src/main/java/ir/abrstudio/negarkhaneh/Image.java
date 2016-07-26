package ir.abrstudio.negarkhaneh;

import java.io.Serializable;

public class Image implements Serializable {
    private int imageAddress;
    private String imageTitle;


    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public int getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(int imageAddress) {
        this.imageAddress = imageAddress;
    }
}
