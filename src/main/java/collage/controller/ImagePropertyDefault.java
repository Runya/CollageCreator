package collage.controller;

import collage.entity.Image;

import java.util.Collections;
import java.util.List;
public class ImagePropertyDefault implements ImageProperty {

    public ImagePropertyDefault(){}

    @Override
    public void setImagesProperty(List<Image> images, int width, int height) {
        Collections.shuffle(images);
        int x = (int) Math.ceil(Math.sqrt(1f * images.size() * width / height));
        int y = (int) Math.ceil(1f * images.size() / x);
        int size = Math.min(width / x, height / y);
        int i = 0;
        int j = 0;
        for (Image image : images) {
            image.setX(size * i++);
            image.setY(size * j);
            image.setHeight(size);
            image.setWidth(size);
            if (i == x) {
                j++;
                i = 0;
            }
        }
    }

}
