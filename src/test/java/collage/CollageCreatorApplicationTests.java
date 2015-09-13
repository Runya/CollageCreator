package collage;

import collage.controller.impl.Twitter4jParser;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CollageCreatorApplication.class)
@WebAppConfiguration
public class CollageCreatorApplicationTests {

	@Test
	public void contextLoads() {
	}

}
