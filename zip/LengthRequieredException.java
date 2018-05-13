public class LengthRequieredException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LengthRequieredException(){
        System.err.println("Length header required");
    }
}
