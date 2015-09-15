package collage.controller;

import java.util.HashMap;

/**
 * Created by byte on 9/15/15.
 */
public class RedirectController {

    private static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("index", "index");
        map.put("images", "images");
    }

    public String getPage(String page) {
        String s;
        if ((s = map.get(page)) != null) return s;
        return "error";
    }

}
