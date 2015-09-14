package collage;

import collage.controller.ImageFactory;
import collage.controller.impl.Twitter4jParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by byte on 9/14/15.
 */

@Configuration
public class AppConfig {

    @Bean
    public ImageFactory imageFactory(){
        return new ImageFactory(parser());
    }

    @Bean
    public Twitter4jParser parser(){
        return new Twitter4jParser(ConfigProperty.POOL_SIZE);
    }
}
