package userservice.exceptions;


public class AccessDeniedException  extends  Exception{
    public AccessDeniedException(String error){
        super(error);
    }
}
