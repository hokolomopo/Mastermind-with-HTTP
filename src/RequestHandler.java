import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler {

    public static void handleRequest(Socket sock, HTMLPage page) throws IOException {

        HTTPReply rep;

        try {
            System.out.println("begin handling");
            HTTPRequest req = new HTTPRequest(sock.getInputStream());
            System.out.println("got request");
            rep = req.getMethod().process(req.getUrl(), req.getHeaders(), req.getBody(), page);
            System.out.println("got reply");
        }
        catch (BadRequestException e) {
            rep = new HTTPReply(ReturnCode.badRequest, new HashMap<HTTPOption, HTTPHeader>());
        }
        catch (BadMethodException e) {
            rep = new HTTPReply(ReturnCode.notImplemented, new HashMap<HTTPOption, HTTPHeader>());
        }
        catch (BadVersionException e) {
            String[] body = new String[1];
            body[0] = "The only supported version is " + HTTP.HTTP_VERSION; //Todo: mettre juste la version et pas de message?
            rep = new HTTPReply(ReturnCode.HTTPVersionNotSupported, new HashMap<HTTPOption, HTTPHeader>(), body);
        }

        try {
            rep.reply(sock.getOutputStream());
            System.out.println("replied");
        }
        catch (OptionNotPresentException | BadFileException e) {
            // if we get there, no BadRequestException was raised, therefore have those exception is not possible
        }
    }
}