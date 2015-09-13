package collage.entity;

/**
 * Created by byte on 9/13/15.
 */
public class Image {
    private final String imgUrl;
    private int width;
    private int height;
    private int x;
    private int y;

    public Image(String imgUrl, int postCount) {
        this.imgUrl = imgUrl;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
