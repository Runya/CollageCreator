package collage.controller;

import collage.entity.Image;
import collage.controller.impl.Twitter4jParser;

import java.util.HashMap;

/**
 * Created by byte on 9/13/15.
 */
public class UserFactory {

    private Twitter4jParser twitterParser;
    private HashMap<Long, Image> userHashMap;

    public UserFactory(Twitter4jParser parser){
        this.twitterParser = twitterParser;
    }





}
