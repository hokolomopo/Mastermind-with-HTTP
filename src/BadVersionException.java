/**
 * thrown when the HTTP version of a request is not the one used by the server
 */
public class BadVersionException extends Exception{
    private static final long serialVersionUID = 1L;

    public BadVersionException(){
        System.err.println("Wrong http version");
    }
}
