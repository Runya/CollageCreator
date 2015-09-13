package collage.entity;

/**
 * Created by byte on 9/13/15.
 */
public class Image {
    private final String imgUrl;
    private final int postCount;
    private int width;
    private int height;
    private double percent;

    public Image(String imgUrl, int postCount) {
        this.imgUrl = imgUrl;
        this.postCount = postCount;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public int getPostCount() {
        return postCount;
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

    public double getPersent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
