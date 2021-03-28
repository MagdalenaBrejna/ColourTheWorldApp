package pl.magdalena.brejna.colourtheworldapp.exceptions;

public class ImageException extends Exception{

    public ImageException(String s){
        super(s);
    }

    public void printStackTrace(){
        super.printStackTrace();
        System.out.println("Wrong file");
    }
}
