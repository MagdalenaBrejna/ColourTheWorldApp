package pl.magdalena.brejna.colourtheworldapp.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NegativeFilter {

    public static BufferedImage makeNegativePhoto(BufferedImage img){
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                //Retrieving the values of a pixel
                int pixel = img.getRGB(x,y);

                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);

                //Retrieving the RGB values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                //Subtracting RGB from 255 to convert into negative
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;

                //Creating new Color object
                color = new Color(red, green, blue);
                int newPixel = color.getRGB();

                //Setting new Color object to the image
                img.setRGB(x, y, newPixel);
            }
        }
        return img;
    }
}
