package collage;

import java.util.*;

import collage.controller.ImageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.TwitterException;


@Controller
public class MainController {

    @Autowired
    private ImageFactory imageFactory;

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/makeCollage", method = RequestMethod.GET)
    public String showImages(
            @RequestParam(value = "login", required = false, defaultValue = "durov") String login,
            @RequestParam(value = "width", required = false, defaultValue = "1000") int width,
            @RequestParam(value = "height", required = false, defaultValue = "800") int height,
            @RequestParam(value = "imageBuilder", required = false, defaultValue = "1") int builder,
            Map<String, Object> map) {
        try {
            imageFactory.check(login);
            map.put("images", imageFactory.getUserImages(login, width, height));
        } catch (TwitterException e) {
            if (e.getErrorMessage().equals("Rate limit exceeded"))
                map.put("rateLimit", "Rate limit, please wait " + e.getRateLimitStatus().getSecondsUntilReset() / 60 + ":" + e.getRateLimitStatus().getSecondsUntilReset() % 60);
            else if (e.getErrorMessage().equals("Sorry, that page does not exist."))
                map.put("rateLimit", "login:\"" + login + "\" not found");
            else {
                map.put("rateLimit", "Sorry, error");
            }
            return "index";
        }
        map.put("collageW", width);
        map.put("collageH", height);
        return "viewImage";
    }

    @RequestMapping(value = "*")
    public String defString() {
        return "index";
    }


    @RequestMapping("/foo")
    public String foo(Map<String, Object> model) {
        throw new RuntimeException("Foo");
    }

}