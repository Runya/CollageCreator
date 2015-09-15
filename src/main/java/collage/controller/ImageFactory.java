package collage.controller;

import collage.controller.impl.Twitter4jParser;
import collage.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.*;
import java.util.stream.Collectors;

public class ImageFactory {

    static final double MAX_PERCENT = 0.3;
    @Autowired
    private Twitter4jParser parser;
    @Autowired
    @Qualifier("randomImageProp")
    private ImageProperty imageProperty;



    public List<Image> getUserImages(String login, int width, int height) throws TwitterException {
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

    public User check(String login) throws TwitterException {
        return parser.getUser(login);
    }
}


