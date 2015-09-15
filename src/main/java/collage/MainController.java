package collage;

import java.util.*;

import collage.controller.ImageFactory;
import collage.controller.ImageProperty;
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
            @RequestParam(value = "selectProp", required = false, defaultValue = "0") int builder,
            Map<String, Object> map) {
        if (width < 0 || height < 0 || width > 10000 || height > 10000){
            map.put("errorMessage", "Please write normal height or width");
            return "error";
        }
        try {
            imageFactory.check(login);
            ImageProperty imageProperty = (ImageProperty) applicationContext.getBean(ConfigProperty.PROP[builder]);
            map.put("images", imageFactory.getUserImages(login, width, height, imageProperty));
        } catch (TwitterException e) {
            if (e.getErrorMessage().equals("Rate limit exceeded"))
                map.put("errorMessage", "Rate limit, please wait " + e.getRateLimitStatus().getSecondsUntilReset() / 60 + ":" + e.getRateLimitStatus().getSecondsUntilReset() % 60);
            else if (e.getErrorMessage().equals("Sorry, that page does not exist."))
                map.put("errorMessage", "login:\"" + login + "\" not found");
            else {
                map.put("errorMessage", "Sorry, error");
            }
            return "errorMessage";
        }
        map.put("collageW", width);
        map.put("collageH", height);
        return "viewImage";
    }

    @RequestMapping(value = "*")
    public String redirect(){
        return "index";
    }

}