/**
 * thrown when a header is missing
 */
public class OptionNotPresentException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OptionNotPresentException(){
        System.err.println("Missing header");
    }
}
