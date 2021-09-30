package pl.magdalena.brejna.colourtheworldapp.algorithms;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
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

public final class EdgeDetection {

    //Laplacian of Gauss algorithm - edge detection
    public static final Image detectEdges(final Project activeProject) throws ImageProcessingException{
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            final Mat dst = detect(Paths.get(URI.create(activeProject.getSourceFile())));
            return createImage(activeProject, dst);
        } catch (Exception imageProcessingException) {
            throw new ImageProcessingException("wrong source file");
        }
    }

    //detect edges in an image
    private static final Mat detect(final Path sourceFile){
        Mat sourceImage = Imgcodecs.imread(sourceFile.toString());
        Mat gray = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type());
        Mat edges = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type());
        Mat dst = new Mat(sourceImage.rows(), sourceImage.cols(), sourceImage.type(), new Scalar(0));

        processImageWithAlgorithms(sourceImage, gray, dst);
        sourceImage.copyTo(dst, edges);
        return dst;
    }

    //process image using Laplacian of Gauss algorithm
    private static final void processImageWithAlgorithms(final Mat sourceImage, final Mat gray, final Mat dst){
        Imgproc.cvtColor(sourceImage, gray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(sourceImage, dst, new Size(3,3), 0.5);
        Imgproc.Laplacian(sourceImage, dst, -10, 3);
    }

    //create final image
    private static final WritableImage createImage(final Project activeProject, final Mat dst){
        final BufferedImage image = imposeImageFilters(activeProject, dst);
        return SwingFXUtils.toFXImage((BufferedImage) image, null);
    }

    //set negative and contrast filters to an image
    private static final BufferedImage imposeImageFilters(final Project activeProject, final Mat dst){
        imposeImageDilation(activeProject, dst);
        BufferedImage image = (BufferedImage) HighGui.toBufferedImage(dst);
        image = NegativeFilter.makeNegativeImage(image);
        return makeContrastPixels(image, activeProject.getContrastValue());
    }

    private static final void imposeImageDilation(final Project activeProject, final Mat dst){
        final Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size((2*activeProject.getDilationValue()) + 1, (2*activeProject.getDilationValue())+1), new Point(activeProject.getDilationValue(), activeProject.getDilationValue()));
        Imgproc.dilate(dst, dst, kernel);
    }

    //make contrast pixels
    private static final BufferedImage makeContrastPixels(final BufferedImage image, final Double contrastFactor){
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                final int newPixelRgbColor = getPixelContrastColor(image, contrastFactor, new Point2D(x, y));
                image.setRGB(x, y, newPixelRgbColor);
            }
        return image;
    }

    //get pixel's RGB value and return its new value
    private static final int getPixelContrastColor(final BufferedImage image, final Double contrastFactor, final Point2D point){
        final int pixelRgbColor = image.getRGB((int)point.getX(), (int)point.getY());
        return (makePixelBlackOrWhite(pixelRgbColor, contrastFactor)).getRGB();
    }

    //make pixel value black or white
    private static final Color makePixelBlackOrWhite(final int pixelRgbColor, final Double contrastFactor){
        final Color pixelColor = new Color(pixelRgbColor, true);
        if(pixelColor.getRed() >= contrastFactor)
            return new Color(255, 255, 255);
        else
            return new Color(0, 0, 0);
    }
}
