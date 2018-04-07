import java.util.HashMap;

public class HeadMethodExecutor extends MethodExecutor{

    public HeadMethodExecutor(){}

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> requestHeaders, String body, HTMLPage html) throws BadRequestException{

        HTTPReply reply = new GetMethodExecutor().process(url, requestHeaders, body, html);
        reply.setBody(null);
        return reply;
    }
}