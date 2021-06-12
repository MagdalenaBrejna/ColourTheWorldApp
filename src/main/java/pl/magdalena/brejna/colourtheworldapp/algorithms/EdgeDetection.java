package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class EdgeDetection {

    //Laplacian of Gauss algorithm - edge detection
    public static Image detectEdges(File sourceFile, Double dilationFactor, Double contrastFactor){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );

        Mat sourceImage = Imgcodecs.imread(sourceFile.getPath());

        Mat gray = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type());
        Mat edges = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type());
        Mat dst = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type(), new Scalar(0));

        Imgproc.cvtColor(sourceImage, gray, Imgproc.COLOR_RGB2GRAY);

        Imgproc.GaussianBlur(sourceImage, dst, new Size(3,3), 0.5);

        Imgproc.Laplacian(sourceImage, dst, -10, 3);

        sourceImage.copyTo(dst, edges);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size((2*dilationFactor) + 1, (2*dilationFactor)+1), new Point(dilationFactor, dilationFactor));
        Imgproc.dilate(dst, dst, kernel);

        BufferedImage img = (BufferedImage) HighGui.toBufferedImage(dst);
        img = NegativeFilter.makeNegativePhoto(img);
        img = contrast(img, contrastFactor);

        WritableImage readyImage = SwingFXUtils.toFXImage((BufferedImage) img, null);

        return readyImage;
    }

    private static BufferedImage contrast(BufferedImage img, Double contrastFactor){

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

                if(contrastFactor == null)
                    System.out.print("!");

                //make black or white
                if(red >= contrastFactor){
                    red = 255;
                    green = 255;
                    blue = 255;
                }else{
                    red = 0;
                    green = 0;
                    blue = 0;
                }

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
