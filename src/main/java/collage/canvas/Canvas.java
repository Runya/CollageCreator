package collage.canvas;

import collage.entity.Image;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by byte on 9/13/15.
 */
public class Canvas {

    private int width;
    private int haigh;
    private List<Image> imageList;
    private Random random;
    private List<Point4D> points;


    public Canvas(int width, int haigh, List<Image> imageList) {
        this.width = width;
        this.haigh = haigh;
        this.imageList = imageList;
        points = new LinkedList<>();
    }


    public void build() {
        imageList.forEach(this::put);
    }

    private final int SEARCH_COUNT = 100;

    private void put(Image image) {
        Point4D point4D = getFreePoint(image);
        image.setX(point4D.x1);
        image.setY(point4D.y1);
        image.setWidth(point4D.x2 - point4D.x1);
        image.setHeight(point4D.y2 - point4D.y1);
    }

    public Point4D getFreePoint(Image image) {
        Point5D best = null;
        for (int i = 0; i < SEARCH_COUNT; i++) {
            Point5D point5D = getRandomPoint(image);
            if (best == null || best.anInt > point5D.anInt) best = point5D;
        }
        Point4D res = new Point4D(best.x1, best.y1, best.x2 - best.anInt, best.y2 - best.anInt);
        return best;
    }

    private Point5D getRandomPoint(Image image) {

        Point5D p = new Point5D();
        do {
            p.x1 = random.nextInt(width);
            p.y1 = random.nextInt(haigh);
            p.x2 = p.x1 + image.getWidth();
            p.y2 = p.y1 + image.getHeight();
        } while (check(p));
        return p;
    }

    private boolean check(Point5D p) {
        int fasa = 0;
        for (Point4D p4 : points) {
            if (check(p.x1, p4.x1, p4.x2) && check(p.y1, p4.y1, p4.y2)) return false;
            int buf = Math.max(step1(p.x1, p.y1, p.x2, p.y2, p4.x1, p4.y1, p4.x2, p4.y2),
                    step2(p.x1, p.y1, p.x2, p.y2, p4.x1, p4.y1, p4.x2, p4.y2));
            buf = Math.max(buf, step3(p.x1, p.y1, p.x2, p.y2, p4.x1, p4.y1, p4.x2, p4.y2));
            fasa = Math.max(fasa, buf);
        }
        p.anInt = fasa;
        return true;
    }

    private int step1(int x1, int y1, int x2, int y2, int z1, int v1, int z2, int v2) {
        if (check(z1, x1, x2) && check(v2, y1, y2))
            return Math.min(x2 - z1, v2 - y1);
        return -1;
    }

    private int step2(int x1, int y1, int x2, int y2, int z1, int v1, int z2, int v2) {
        if (check(z1, x1, x2) && check(v1, y1, y2)){
            return x2 - z1;
        }
        return -1;
    }

    private int step3(int x1, int y1, int x2, int y2, int z1, int v1, int z2, int v2){
        if (check(z1, x1, x2) && check(v2, y1, y2)){
            return y2 - v1;
        }
        return -1;
    }

    private boolean check(int x, int x1, int x2) {
        return x1 < x && x2 > x;
    }

    private void clear() {
        points.clear();
    }

    private class Point4D {
        int x1;
        int y1;
        int x2;
        int y2;

        public Point4D() {
        }

        public Point4D(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private class Point2D {
        int x;
        int y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Point5D extends Point4D{
        int anInt;



        public Point5D(int x1, int y1, int x2, int y2) {
            super(x1, y1, x2, y2);
        }

        public Point5D() {

        }
    }
}
