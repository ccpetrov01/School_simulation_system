package ccpetrov01.studentApp.exceptions;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String message){
        super(message);
    }
}
