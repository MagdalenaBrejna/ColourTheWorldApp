package pl.magdalena.brejna.colourtheworldapp.exceptions;

public abstract class ExecutionException extends RuntimeException{

    public ExecutionException(String s){
        super(s);
    }

    public abstract void callErrorMessage();
}
