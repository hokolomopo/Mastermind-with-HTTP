public class HTTPRedirectionReply extends HTTPReply{

    public HTTPRedirectionReply(String location){

        ret = ReturnCode.SEE_OTHER;

        headers.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
        headers.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
        headers.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));

        body = location;
    }
}
