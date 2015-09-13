package collage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import collage.controller.impl.Twitter4jParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        Twitter4jParser twitter4jParser = new Twitter4jParser(5);
        List<Long> userId = twitter4jParser.getFollowers("durov");
        HashMap<String, Integer> hashMap = twitter4jParser.getImagesMap(userId);
        for (String key: hashMap.keySet()){
            System.out.println(key + " " + hashMap.get(key));
        }
        return "welcome";
    }

    @RequestMapping("/foo")
    public String foo(Map<String, Object> model) {
        throw new RuntimeException("Foo");
    }

}