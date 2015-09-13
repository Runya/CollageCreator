package collage.canvas;

import collage.entity.Image;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by byte on 9/13/15.
 */
public class Canvas {

    private int width;
    private int haigh;
    private List<Image> imageList;
    private List<Point4D> points;

    public Canvas(int width, int haigh, List<Image> imageList) {
        this.width = width;
        this.haigh = haigh;
        this.imageList = imageList;
        points = new LinkedList<>();
    }


    public void build(){
        imageList.forEach(this::put);
    }

    private final int SEARCH_COUNT = 10;

    private void put(Image image){
        Point5D point5D = getFreePoint(image);
        image.
    }

    public Point5D getFreePoint(Image image){
        Point5D best;
        for (int i = 0; i <SEARCH_COUNT; i++) {
            Point5D point5D = getFreePoint(image);

        }
    }

    private void clear(){
        points.clear();
    }

    private class Point4D{
        int x1;
        int y1;
        int x2;
        int y2;

        public Point4D(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private class Point2D{
        int x;
        int y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Point5D{
        int x;
        int y;
        int xL;
        int yU;
        int anInt;

        public Point5D(int x, int y, int xL, int yU, int anInt) {
            this.x = x;
            this.y = y;
            this.xL = xL;
            this.yU = yU;
            this.anInt = anInt;
        }
    }
}
