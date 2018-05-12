import java.util.HashMap;

/**
 * class that represent a redirection reply of the HTTP protocol
 */
public class HTTPRedirectionReply extends HTTPReply{

    /**
     * constructor
     *
     * @param location url of the object without the beginning /
     */
    public HTTPRedirectionReply(String location){

        ret = ReturnCode.SEE_OTHER;

        headers = new HashMap<HTTPOption, HTTPHeader>();
        headers.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, HTTP.getServerTime()));
        headers.put(HTTPOption.LOCATION, new HTTPHeader(HTTPOption.LOCATION, "/" + location));

        body = null;
    }
}
