package ccpetrov01.studentApp.exceptions;

public class GlobalException extends RuntimeException{
    public GlobalException(String message , Throwable cause){
        super(message , cause);
    }
}
