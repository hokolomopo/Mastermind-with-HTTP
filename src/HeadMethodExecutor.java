import java.util.HashSet;

public class HeadMethodExecutor extends MethodExecutor{

    public HeadMethodExecutor{}

    public HTTPReply process(String url, HashSet<HTTPHeader> requestHeaders, HTMLPage html) throws BadRequestException{

        HTTPReply reply = new GetMethodExecutor().process(url, requestHeaders, html);
        reply.setBody(null);
        return reply;
    }
}