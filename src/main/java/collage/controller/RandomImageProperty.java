package collage.controller;

import collage.controller.collage.Canvas;
import collage.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class RandomImageProperty implements ImageProperty {

    @Autowired
    private Canvas canvas;

    @Override
    public void setImagesProperty(List<Image> images, int width, int height) {
        double twittCount = 0;
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.setImageList(images);

        for (Image image : images) {
            twittCount+=image.getPostCount();
        }

        for (Image image : images) {
            double percent = image.getPostCount() / twittCount;
            image.setWidth((int) Math.sqrt(width * height * percent));
            image.setHeight((int) Math.sqrt(width * height * percent));
        }

        Collections.sort(images, ((o1, o2) -> o2.getPostCount() - o1.getPostCount()));

        canvas.build();

    }
}
