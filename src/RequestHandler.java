import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;

public class RequestHandler{

    public void handleRequest(Socket sock, HTMLPage page) throws IOException{

        HTTPReply rep;

        try {
            HTTPRequest req = new HTTPRequest(sock.getInputStream());
            rep = req.getMethod().process(req.getUrl(), req.getHeaders(), page);
        }
        catch(BadRequestException e){
            rep = new HTTPReply(ReturnCode.badRequest, new HashSet<HTTPHeader>(), new String[0]);
        }
        catch(BadMethodException e){
            rep = new HTTPReply(ReturnCode.notImplemented, new HashSet<HTTPHeader>(), new String[0]);
        }
        catch(BadVersionException e){
            String[] body = new String[1];
            body[0] = "The only supported version is " + HTTP.HTTP_VERSION;
            rep = new HTTPReply(ReturnCode.HTTPVersionNotSupported, new HashSet<HTTPHeader>(), body);
        }
        rep.reply(sock.getOutputStream());
    }
}