package collage.controller.impl;

import twitter4j.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by byte on 9/13/15.
 */
public class Twitter4jParser {

    private Twitter twitter;
    private ExecutorService service;
    private int poolSize;


    public Twitter4jParser(int poolSize) {
        this.twitter = new TwitterFactory().getInstance();
        service = Executors.newFixedThreadPool(poolSize);
        this.poolSize = poolSize;
    }


    public List<Long> getFollowers(Long userId) {
        return getFollowers(new FollowersInterface() {
            @Override
            public IDs next() {
                IDs iDs;
                try {
                    iDs = twitter.getFriendsIDs(userId, cursor);
                } catch (TwitterException e) {
                    return null;
                }
                cursor = iDs.getNextCursor();
                return iDs;
            }
        });
    }


    public List<Long> getFollowers(String login) {
        return getFollowers(new FollowersInterface() {
            @Override
            public IDs next() {
                if (cursor == 0) return null;
                IDs iDs = null;
                try {
                    iDs = twitter.getFriendsIDs(login, cursor);
                } catch (TwitterException e) {
                    return null;
                }
                cursor = iDs.getNextCursor();
                return iDs;
            }
        });
    }

    private List<Long> getFollowers(FollowersInterface fol) {
        List<Long> res = new LinkedList<>();
        IDs iDs;
        try {
            while ((iDs = fol.next()) != null)
                for (long id : iDs.getIDs()) {
                    res.add(id);
                }

        } catch (TwitterException ignored) {
        }
        return res;
    }


    public HashMap<String, Integer> getImagesMap(List<Long> usersId) {
        List<Handler> handlers = new LinkedList<>();
        for (int i = 0; i < poolSize; i++) {
            Handler handler = new Handler(usersId, i, poolSize, twitter);
            service.execute(handler);
            handlers.add(handler);
        }
        while (service.isShutdown()) try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {
        }
        HashMap<String, Integer> res = new HashMap<>();
        for (Handler handler:handlers){
            res.putAll(handler.getImgCount());
        }
        return res;

    }


}

abstract class FollowersInterface {

    long cursor = -1;

    abstract IDs next() throws TwitterException;

}

class Handler implements Runnable {

    private List<Long> userIds;
    private int handlerId;
    private int step;

    public HashMap<String, Integer> getImgCount() {
        return imgCount;
    }

    private HashMap<String, Integer> imgCount;
    private Twitter parser;
    private static final int STEP_SIZE = 100;

    public Handler(List<Long> userIds, int handlerId, int step, Twitter parser) {
        this.userIds = userIds;
        this.handlerId = handlerId;
        this.step = step;
        this.parser = parser;
        imgCount = new HashMap<>();
    }

    @Override
    public void run() {
        int i = handlerId;
        int cours;
        while ((cours = i++ * STEP_SIZE) < userIds.size()) {
            long[] userIdsStep = new long[Math.min(STEP_SIZE, userIds.size())];
            for (int j = i; j < Math.min(i + step, userIds.size()); j++) {
                userIdsStep[j] = userIds.get(j);
            }
            try {
                List<User> users = parser.lookupUsers(userIdsStep);
                for (User user : users) {
                    String img = user.getOriginalProfileImageURL();
                    int postCount = user.getListedCount();
                    imgCount.put(img, postCount + 1);
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }
}

