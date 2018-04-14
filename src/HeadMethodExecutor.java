import java.util.HashMap;

public class HeadMethodExecutor extends MethodExecutor {

    public HeadMethodExecutor() {
    }

    /**
     * process the head request in order to build the appropriate reply.
     * @param url the url of the head request
     * @param requestHeaders the headers of the head request
     * @param body the body of the head request
     * @return the appropriate reply held by a HTTPReply object
     * @throws BadRequestException in case the request is not valid
     * @throws NotFoundException in case the url does not correspond to an existing page
     */
    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> requestHeaders, String body) throws BadRequestException, NotFoundException {

        HTTPReply reply = new GetMethodExecutor().process(url, requestHeaders, body);
        reply.setBody(null);
        return reply;
    }
}