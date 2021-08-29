package pl.magdalena.brejna.colourtheworldapp.exceptions;

public class DatabaseException extends Exception{

    public DatabaseException(String s){
        super(s);
    }

    public void printStackTrace(){
        System.out.println("Database error");
    }
}
