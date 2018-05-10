import java.util.HashMap;

public class HTTPErrorReply extends HTTPReply {

	 /**
     * constructor
     *
     * @param Error type /
     */
    public HTTPErrorReply(ReturnCode error)
    {

        ret = error;

        headers = new HashMap<HTTPOption, HTTPHeader>();
        headers.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, HTTP.getServerTime()));
        headers.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
        headers.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
        headers.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));

        body = new HTMLErrorPage(ReturnCode.NOT_FOUND).getHtml();
    } 
}

