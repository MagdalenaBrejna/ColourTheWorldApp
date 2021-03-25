package pl.magdalena.brejna.colourtheworldapp.exceptions;

public class ImageSettingException extends Exception{

    public ImageSettingException(String s){
        super(s);
    }

    public void printStackTrace(){
        super.printStackTrace();
        System.out.println("Wrong file");
    }
}
