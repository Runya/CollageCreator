package collage.controller;

import collage.Entity.MyUser;
import collage.controller.impl.Twitter4jParser;

import java.util.HashMap;

/**
 * Created by byte on 9/13/15.
 */
public class UserFactory {

    private Twitter4jParser twitterParser;
    private HashMap<Long, MyUser> userHashMap;

    public UserFactory(Twitter4jParser parser){
        this.twitterParser = twitterParser;
    }





}
