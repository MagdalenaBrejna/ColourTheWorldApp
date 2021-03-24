package pl.magdalena.brejna.colourtheworldapp.exceptions;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageSettingException extends Exception{

    public ImageSettingException(String s){
        super(s);
    }

    public void printStackTrace(){
        super.printStackTrace();
        System.out.println("Wrong file");
    }

    //public void loadErrorPhoto(ImageView photo){
    //    photo.setImage(new Image("errorPhoto.png"));
    //}
}
