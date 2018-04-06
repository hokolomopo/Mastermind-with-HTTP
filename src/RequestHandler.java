import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler{

    public void handleRequest(Socket sock, HTMLPage page) throws IOException{

        HTTPReply rep;

        try {
            HTTPRequest req = new HTTPRequest(sock.getInputStream());
            rep = req.getMethod().process(req.getUrl(), req.getHeaders(), page);
        }
        catch(BadRequestException e){
            rep = new HTTPReply(ReturnCode.badRequest, new HashMap<HTTPOption, HTTPHeader>(), new String[0]);
        }
        catch(BadMethodException e){
            rep = new HTTPReply(ReturnCode.notImplemented, new HashMap<HTTPOption, HTTPHeader>(), new String[0]);
        }
        catch(BadVersionException e){
            String[] body = new String[1];
            body[0] = "The only supported version is " + HTTP.HTTP_VERSION; //Todo: mettre juste la version et pas de message?
            rep = new HTTPReply(ReturnCode.HTTPVersionNotSupported, new HashMap<HTTPOption, HTTPHeader>(), body);
        }

        try {
            rep.reply(sock.getOutputStream());
        }
        catch(OptionNotPresentException | BadFileException e){
            // if we get there, no BadRequestException was raised, therefore have those exception is not possible
        }
    }
}