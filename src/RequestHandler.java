import java.util.ArrayList;

public class RequestHandler{

    public String[] reply(ArrayList<String> request) {

        HTTPReply reply;

        try {
            HTTPRequest req = new HTTPRequest(request);
        }
        catch(BadMessageException e){
            reply = new HTTPReply(ReturnCode.badRequest, new HTTPHeader[0], new String[0]);
            return reply.buildReply();
        }
        catch(BadMethodException e){
            reply = new HTTPReply(ReturnCode.notImplemented, new HTTPHeader[0], new String[0]);
            return reply.buildReply();
        }
        catch(BadVersionException e){
            String[] body = new String[1];
            body[0] = "The only supported version is " + HTTP.HTTP_VERSION;
            reply = new HTTPReply(ReturnCode.HTTPVersionNotSupported, new HTTPHeader[0], body);
            return reply.buildReply();
        }
    }
}