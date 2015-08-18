package androidinterview.com.customlistviewimagetext;

import android.graphics.Bitmap;

/**
 * Created by jong-min on 2015-08-11.
 */
public class fromServerData {
    private String mapCaptureUrl;
    private Bitmap image;
    private String text1;
    private String text2;

    public Bitmap getImage(){
        return image;
    }
    public String getText1(){
        return text1;
    }
    public String getText2(){return text2;}
    public String getMapCaptureUrl() {return mapCaptureUrl;}

    public void setImage(Bitmap img){
        this.image = img;
    }
    public void setText1(String txt1){
        this.text1 = txt1;
    }
    public void setText2(String txt2){
        this.text2 = txt2;
    }
    public void setMapCaptureUrl(String url) {this.mapCaptureUrl = url;}
}
