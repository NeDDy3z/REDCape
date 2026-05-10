package org.redcape.view.ui.elements;

import lombok.Getter;
import org.redcape.util.FileHandling;

import java.awt.*;
import java.awt.image.ImageObserver;


/**
 * CustomImage class for handling image loading and drawing.
 * <p>
 * This class is responsible for loading images from files and drawing them on the screen.
 * </p>
 */
@Getter
public final class CustomImage {
    private final Image image;

    /**
     * Constructor for the CustomImage class
     *
     * @param image of this class
     */
    public CustomImage(Image image) {
        this.image = image;
    }

    /**
     * Constructor for the CustomImage class
     *
     * @param imagePath of the image
     */
    public CustomImage(String imagePath) {
        this.image = FileHandling.loadImage(imagePath);
    }

    /**
     * Draws the image so it fits the screen.
     *
     * @param g object
     * @param observer of the image
     * @param width of the screen
     * @param height of the screen
     */
    public void drawFittedImage(Graphics g, ImageObserver observer, int width, int height) {
        int imgWidth = image.getWidth(observer);
        int imgHeight = image.getHeight(observer);
        if (imgWidth > 0 && imgHeight > 0) {
            float scale = Math.max((float) width / imgWidth, (float) height / imgHeight);
            int drawWidth = (int) (imgWidth * scale);
            int drawHeight = (int) (imgHeight * scale);
            int x = (width - drawWidth) / 2;
            int y = (height - drawHeight) / 2;
            g.drawImage(image, x, y - 30, drawWidth, drawHeight, observer);
        }
    }
}
