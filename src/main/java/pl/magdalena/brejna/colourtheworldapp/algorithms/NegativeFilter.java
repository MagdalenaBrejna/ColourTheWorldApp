package pl.magdalena.brejna.colourtheworldapp.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class NegativeFilter {

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
        final int red = 255 - color.getRed();
        final int green = 255 - color.getGreen();
        final int blue = 255 - color.getBlue();
        return new Color(red, green, blue);
    }
}
