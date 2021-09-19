package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import pl.magdalena.brejna.colourtheworldapp.exceptions.ImageProcessingException;
import pl.magdalena.brejna.colourtheworldapp.objects.Project;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EdgeDetection {

    //Laplacian of Gauss algorithm - edge detection
    public static Image detectEdges(Project activeProject) throws ImageProcessingException{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat dst = null;
        try {
            dst = detect(Paths.get(URI.create(activeProject.getSourceFile())));
        } catch (Exception e) {
            throw new ImageProcessingException("wrong source file");
        }

        Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size((2*activeProject.getDilationValue()) + 1, (2*activeProject.getDilationValue())+1), new Point(activeProject.getDilationValue(), activeProject.getDilationValue()));
        Imgproc.dilate(dst, dst, kernel);

        BufferedImage img = (BufferedImage) HighGui.toBufferedImage(dst);
        img = NegativeFilter.makeNegativePhoto(img);
        img = makeContrastPixels(img, activeProject.getContrastValue());

        WritableImage readyImage = SwingFXUtils.toFXImage((BufferedImage) img, null);
        return readyImage;
    }

    //detect edges
    private static Mat detect(Path sourceFile){
        Mat sourceImage = Imgcodecs.imread(sourceFile.toString());
        Mat gray = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type());
        Mat edges = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type());
        Mat dst = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type(), new Scalar(0));
        Imgproc.cvtColor(sourceImage, gray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(sourceImage, dst, new Size(3,3), 0.5);
        Imgproc.Laplacian(sourceImage, dst, -10, 3);

        sourceImage.copyTo(dst, edges);
        return dst;
    }

    //make contrast
    private static BufferedImage makeContrastPixels(BufferedImage image, Double contrastFactor){
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {

                int pixel = image.getRGB(x,y);
                Color color = new Color(pixel, true);

                if(color.getRed() >= contrastFactor)
                    color = new Color(255, 255, 255);
                else
                    color = new Color(0, 0, 0);

                int newPixel = color.getRGB();
                image.setRGB(x, y, newPixel);
            }

        return image;
    }
}
