package collage.controller;

import collage.controller.impl.Twitter4jParser;
import collage.entity.Image;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ImageFactory {

    private static final double MAX_PERCENT = 0.3;

    Twitter4jParser parser;

    public ImageFactory(Twitter4jParser parser) {
        this.parser = parser;
    }

    public List<Image> getUserImages(String login, int width, int height){
        List<Long> userId = parser.getFollowers(login);
        HashMap<String, Integer> hashMap = parser.getImagesMap(userId);
        List<Image> images = hashMap.keySet().stream().
                map(key -> new Image(key, hashMap.get(key)))
                .sorted((i1, i2) -> i1.getPostCount() - i2.getPostCount())
                .collect(Collectors.toList());
        setImagesProperty(images, width, height);
        return images;
    }

    private void setImagesProperty(List<Image> images, int width, int height) {
        double twittCount = 0;
        for (Image image:images) {
            twittCount +=image.getPostCount();
        }
        for (Image image:images){
            if (image.getPostCount() / twittCount > MAX_PERCENT){
                int count = (int) (MAX_PERCENT * image.getPostCount());
                twittCount -= image.getPostCount() + count;
                image.setPostCount(count);
            }
        }

        for (Image image:images){
            double percent = image.getPostCount() / twittCount;
            image.setPercent(percent);
            image.setWidth((int) (width * percent));
            image.setHeight((int) (width * percent));
//            image.setWidth(100);
//            image.setHeight(100);
        }
    }

}