package collage.controller.impl;

import twitter4j.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Twitter4jParser {

    private Twitter twitter;
    private ExecutorService service;
    private int poolSize;


    public Twitter4jParser(int poolSize) {
        this.twitter = new TwitterFactory().getInstance();
        service = Executors.newFixedThreadPool(poolSize);
        this.poolSize = poolSize;
    }


    public List<Long> getFollowers(Long userId) throws TwitterException {
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


    public List<Long> getFollowers(String login) throws TwitterException {
        return getFollowers(new FollowersInterface() {
            @Override
            public IDs next() throws TwitterException {
                if (cursor == 0) return null;
                IDs iDs = null;
                try {
                    iDs = twitter.getFriendsIDs(login, cursor);
                    cursor = iDs.getNextCursor();
                } catch (TwitterException e) {
                    throw e;
                }
                return iDs;
            }
        });
    }

    private List<Long> getFollowers(FollowersInterface fol) throws TwitterException {
        List<Long> res = new LinkedList<>();
        IDs iDs;
        try {
            while ((iDs = fol.next()) != null)
                for (long id : iDs.getIDs()) {
                    res.add(id);
                }

        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return res;
    }


    public HashMap<String, Integer> getImagesMap(List<Long> usersId) {
        List<Handler> handlers = new LinkedList<>();
        CountHandler countHandler = new CountHandler();
        for (int i = 0; i < poolSize; i++) {
            Handler handler = new Handler(usersId, i, poolSize, twitter, countHandler);
            service.execute(handler);
            handlers.add(handler);
        }
        while (!countHandler.isEmpty()) try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, Integer> res = new HashMap<>();
        for (Handler handler : handlers) {
            res.putAll(handler.getImgCount());
        }
        return res;

    }


    public User getUser(String login) throws TwitterException {
        return twitter.showUser(login);
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
    private CountHandler removeHadlers;

    public Handler(List<Long> userIds, int handlerId, int step, Twitter parser, CountHandler removeHandlers) {
        this.userIds = userIds;
        this.handlerId = handlerId;
        this.step = step;
        this.parser = parser;
        imgCount = new HashMap<>();
        this.removeHadlers = removeHandlers;
        removeHandlers.inc();
    }

    @Override
    public void run() {
        int i = handlerId;
        int cours;
        while ((cours = i * STEP_SIZE) < userIds.size()) {
            long[] userIdsStep = new long[Math.min(STEP_SIZE, userIds.size() - i)];
            i += step;
            for (int j = 0; j < Math.min(STEP_SIZE, userIds.size() - cours); j++) {
                userIdsStep[j] = userIds.get(cours + j);
            }
            try {
                List<User> users = parser.lookupUsers(userIdsStep);
                for (User user : users) {
                    String img = user.getOriginalProfileImageURL();
                    int postCount = user.getStatusesCount();
                    imgCount.put(img, postCount + 1);
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        removeHadlers.dec();
    }
}

class CountHandler {
    private int count;

    public void inc() {
        count++;
        System.out.println("inc count: " + count);
    }

    public boolean isEmpty() {
        return count <= 0;
    }

    public void dec() {
        System.out.println("dec count:" + count);
        count--;
    }

}

