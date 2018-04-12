import java.util.HashMap;

public class HTTPRedirectionReply extends HTTPReply{

    public HTTPRedirectionReply(String location){

        ret = ReturnCode.SEE_OTHER;

        headers = new HashMap<HTTPOption, HTTPHeader>();
        headers.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, HTTP.getServerTime()));
        headers.put(HTTPOption.LOCATION, new HTTPHeader(HTTPOption.LOCATION, "/" + location));

        body = null;
    }
}
