
public class HTMLErrorPage{

    private static final String head = "<!DOCTYPE html>\r\n" +
                                       "<html>\r\n" +
                                       "<head>\r\n" +
                                       "<style>\r\n" +
                                       "body{\r\n" +
                                       "	font-size: 40px;\r\n" +
                                       "}\r\n" +
                                       "</style>\r\n" +
                                       "</head>\r\n" +
                                       "<body>\r\n" +
                                       "<p><b>";

    private static final String tail = "</b></p>\r\n" +
                                       "</body>\r\n" +
                                       "</html>";

    private String body;

    /**
     * constructor
     *
     * @param errorType : the type of HTTP error
     */
    public HTMLErrorPage(ReturnCode errorType){
        body = "Error " + Integer.toString(errorType.getCode()) + " : " + errorType.getStatus();
    }

    /**
     * Get the HTML code of the error page
     *
     * @return the HTML code
     */
    public String getHtml(){
        return head + body + tail;
    }
}
