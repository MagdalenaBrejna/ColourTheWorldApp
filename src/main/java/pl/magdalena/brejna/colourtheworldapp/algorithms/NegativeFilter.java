package pl.magdalena.brejna.colourtheworldapp.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class NegativeFilter {

    private static final int MAX_COLOR = 255;

    //create negative image
    public final static BufferedImage makeNegativeImage(final BufferedImage image){
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixelRgbColor = image.getRGB(x,y);
                final int newPixelRgbColor = (makeNewPixelColor(pixelRgbColor)).getRGB();
                image.setRGB(x, y, newPixelRgbColor);
            }
        return image;
    }

    //create negative pixel
    private static final Color makeNewPixelColor(final int pixel){
        final Color color = new Color(pixel, true);
        final int red = MAX_COLOR - color.getRed();
        final int green = MAX_COLOR - color.getGreen();
        final int blue = MAX_COLOR - color.getBlue();
        return new Color(red, green, blue);
    }
}
