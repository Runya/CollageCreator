package collage.controller;

import collage.controller.impl.Twitter4jParser;
import collage.entity.Image;

import java.util.*;
import java.util.stream.Collectors;

public class ImageFactory {

    static final double MAX_PERCENT = 0.3;

    private Twitter4jParser parser;
    private ImageProperty imageProperty;

    public ImageFactory(Twitter4jParser parser, ImageProperty imageProperty) {
        this.parser = parser;
        this.imageProperty = imageProperty;
    }

    public ImageFactory(Twitter4jParser parser) {
        this(parser, new ImagePropertyDefault());
    }


    public List<Image> getUserImages(String login, int width, int height) {
        List<Long> userId = parser.getFollowers(login);
        HashMap<String, Integer> hashMap = parser.getImagesMap(userId);
        List<Image> images = hashMap.keySet().stream().
                map(key -> new Image(key, hashMap.get(key)))
                .sorted((i1, i2) -> i1.getPostCount() - i2.getPostCount())
                .collect(Collectors.toList());
        setImagesProperty(images, width, height);
        return images;
    }

    protected void setImagesProperty(List<Image> images, int width, int height) {
        imageProperty.setImagesProperty(images, width, height);
    }

}

interface ImageProperty {
    void setImagesProperty(List<Image> images, int width, int height);
}

class ImagePropertyDefault implements ImageProperty {


    @Override
    public void setImagesProperty(List<Image> images, int width, int height) {
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



class ImagePropertyNotDefoult implements ImageProperty {

    @Override
    public void setImagesProperty(List<Image> images, int width, int height) {
        double twittCount = 0;
        for (Image image : images) {
            twittCount += image.getPostCount();
        }
        for (Image image : images) {
            if (image.getPostCount() / twittCount > ImageFactory.MAX_PERCENT) {
                int count = (int) (ImageFactory.MAX_PERCENT * image.getPostCount());
                twittCount -= image.getPostCount() + count;
                image.setPostCount(count);
            }
        }

        for (Image image : images) {
            double percent = image.getPostCount() / twittCount;
            image.setWidth((int) (width * percent));
            image.setHeight((int) (width * percent));
        }
    }
}