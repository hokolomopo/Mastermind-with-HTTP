import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class RequestHandler{
	
	
    public static void handleRequest(Socket sock) throws IOException {

        HTTPReply rep;

        try {
            System.out.println("begin handling");
            HTTPRequest req = new HTTPRequest(sock.getInputStream());
            System.out.println("got request");
            rep = req.getMethod().process(req.getUrl(), req.getHeaders(), req.getBody());
            System.out.println("got reply");
        }
        catch (BadRequestException e) {
        	System.out.println("bad request");
            rep = new HTTPReply(ReturnCode.BAD_REQUEST, new HashMap<HTTPOption, HTTPHeader>());
        }
        catch (BadMethodException e) {
        	System.out.println("not implemented");
            rep = new HTTPReply(ReturnCode.NOT_IMPLEMENTED, new HashMap<HTTPOption, HTTPHeader>());
        }
        catch (BadVersionException e) {
            String[] body = new String[1];
            body[0] = "The only supported version is " + HTTP.HTTP_VERSION; //Todo: mettre juste la version et pas de message?
            rep = new HTTPReply(ReturnCode.HTTP_VERSION_NOT_SUPPORTED, new HashMap<HTTPOption, HTTPHeader>(), body);
        }
        catch (NotFoundException e){
            System.out.println("Not found");
            rep = new HTTPReply(ReturnCode.NOT_FOUND, new HashMap<HTTPOption, HTTPHeader>());
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