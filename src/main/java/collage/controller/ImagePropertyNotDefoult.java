package collage.controller;

import collage.entity.Image;

import java.util.List;


class ImagePropertyNotDefoult implements ImageProperty {

    @Override
    public void setImagesProperty(List<Image> images, int width, int height) {
        double twittCount = 0;
        for (Image image : images) {
            twittCount += image.getPostCount();
        }
        for (Image image : images) {
            if (image.getPostCount() / twittCount > ImageFactory.MAX_PERCENT) {
                int count = (int) (ImageFactory.MAX_PERCENT * image.getPostCount());
                twittCount -= image.getPostCount() + count;
                image.setPostCount(count);
            }
        }

        for (Image image : images) {
            double percent = image.getPostCount() / twittCount;
            image.setWidth((int) (width * percent));
            image.setHeight((int) (width * percent));
        }
    }
}
