package pl.magdalena.brejna.colourtheworldapp.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NegativeFilter {

    public static BufferedImage makeNegativePhoto(BufferedImage img){
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                //Retrieving the values of a pixel
                int pixel = img.getRGB(x,y);

                //Creating new Color object
                Color color = setPixelColor(pixel);
                int newPixel = color.getRGB();

                //Setting new Color object to the image
                img.setRGB(x, y, newPixel);
            }
        }
        return img;
    }

    private static Color setPixelColor(int pixel){
        Color color = new Color(pixel, true);

        //Subtracting RGB from 255 to convert into negative
        int red = 255 - color.getRed();
        int green = 255 - color.getGreen();
        int blue = 255 - color.getBlue();

        //Creating new Color object
        return new Color(red, green, blue);
    }
}
