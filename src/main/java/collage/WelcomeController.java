package collage;

import java.util.*;

import collage.controller.ImageFactory;
import collage.controller.impl.Twitter4jParser;
import collage.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @RequestMapping(value = "/collage", method = RequestMethod.GET)
    public String welcome(@RequestParam(value = "login", required = true, defaultValue = "welcome") String login) {
        return "viewImage";
    }

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public String showImages(
            @RequestParam(value = "login", required = false, defaultValue = "durov") String login,
            @RequestParam(value = "width", required = false, defaultValue = "1000") int width,
            @RequestParam(value = "height", required = false, defaultValue = "800") int height,
            Map<String, Object> map) {
        ImageFactory imageFactory = new ImageFactory(new Twitter4jParser(5));
        map.put("images", imageFactory.getUserImages(login, width, height));
        map.put("collageW", width);
        map.put("collageH", height);
        return "viewImage";
    }


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/foo")
    public String foo(Map<String, Object> model) {
        throw new RuntimeException("Foo");
    }

}