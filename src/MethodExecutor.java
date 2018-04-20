import java.util.Calendar;
import java.util.HashMap;

public abstract class MethodExecutor extends HTTP
{

    //Duration of cookie (in minutes)
    private final static int COOKIE_DURATION = 10;

    protected Cookie cookie;


    /**
     * process the get request in order to build the appropriate reply.
     *
     * @param url         the url of the get request
     * @param headers     the headers of the get request
     * @param requestBody the body of the get request
     * @return the appropriate reply held by a HTTPReply object
     * @throws BadRequestException in case the request is not valid
     * @throws NotFoundException   in case the url does not correspond to an existing page
     */
    public abstract HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException;

    /**
     * Retrieve data for the cookie used from the user or create a new cookie if needed
     *
     * @param requestHeaders	the headers of the received request
     * @param replyHeaders	the header of the reply that we will sned back to the client
     */    protected void manageCookies(HashMap<HTTPOption, HTTPHeader> requestHeaders, HashMap<HTTPOption, HTTPHeader> replyHeaders)
    {
        //Get a cookie header if any
        HTTPHeader cookieHeader = requestHeaders.get(HTTPOption.COOKIE);


        //Create new cookie if no cookie set in client
        if (cookieHeader == null)
        {
            //Create a cookie
            Combination combi = new Combination();
            combi.setRandomCombi();
            this.cookie = new Cookie(combi);

            //Add the cookie to the server's cookie list
            WebServer.addCookie(cookie);
            replyHeaders.put(HTTPOption.SET_COOKIE, new HTTPHeader(HTTPOption.SET_COOKIE, cookie.getId() + "; path=/"));
        }else
        {
            //Try to fetch the cookie from the server's cookie list
            String headerValue = cookieHeader.getValue();
            this.cookie = WebServer.getCookie(headerValue);

            //No such cookie in server's cookie list
            if (this.cookie == null)
            {

                //Create a new cookie
                Combination combi = new Combination();
                combi.setRandomCombi();
                this.cookie = new Cookie(combi, headerValue);

                //Add the cookie to the server's cookie list
                WebServer.addCookie(this.cookie);
            }
            //The cookie was found
            else
            {
                //Check if cookie hasn't lived past it's expiration date, reset if if it has
                Calendar currentTime = Calendar.getInstance();
                currentTime.add(Calendar.MINUTE, -COOKIE_DURATION);
                if (cookie.getCreationTime().before(currentTime))
                    cookie.reset();
            }
        }
    }

}