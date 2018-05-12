/**
 * thrown when a color that is not part of the mastermind's color is sent to the server
 */
public class BadColorException extends Exception{
    private static final long serialVersionUID = 1L;

    public BadColorException(){
    }
}
