/**
 * thrown when a header is ill formatted
 */
public class BadHeaderException extends Exception{
    private static final long serialVersionUID = 1L;

    public BadHeaderException(){
        System.err.println("Header ill formatted");
    }
}
