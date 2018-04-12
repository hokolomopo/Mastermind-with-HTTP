import java.util.HashMap;

public class HeadMethodExecutor extends MethodExecutor {

    public HeadMethodExecutor() {
    }

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> requestHeaders, String body) throws BadRequestException, NotFoundException {

        HTTPReply reply = new GetMethodExecutor().process(url, requestHeaders, body);
        reply.setBody(null);
        return reply;
    }
}