import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * class handling the requests
 */
public class RequestHandler
{

    private RequestHandler(){}

    /**
     * handle one request (read the request, process it and reply)
     *
     * @param sock The socket from which the request will be received and the reply sent
     * @throws IOException in case of error with the socket
     */
    public static void handleRequest(Socket sock) throws IOException
    {

        HTTPReply rep;

        try
        {
            System.out.println("begin handling");
            //read the request on the InputStream
            HTTPRequest req = new HTTPRequest(sock.getInputStream());
            System.out.println("got request");
            //process the request in order to build the appropriate reply
            rep = req.getMethod().process(req.getUrl(), req.getHeaders(), req.getBody());
            System.out.println("got reply");
        }
        //handle the exceptions and send the corresponding error return code
        catch (BadRequestException e)
        {
            System.out.println("bad request");
            rep = new HTTPReply(ReturnCode.BAD_REQUEST, new HashMap<HTTPOption, HTTPHeader>());
        }
        catch (BadMethodException e)
        {
            System.out.println("not implemented");
            rep = new HTTPReply(ReturnCode.NOT_IMPLEMENTED, new HashMap<HTTPOption, HTTPHeader>());
        }
        catch (BadVersionException e)
        {
            String[] body = new String[1];
            body[0] = "The only supported version is " + HTTP.HTTP_VERSION; //Todo: mettre juste la version et pas de message?
            rep = new HTTPReply(ReturnCode.HTTP_VERSION_NOT_SUPPORTED, new HashMap<HTTPOption, HTTPHeader>(), body);
        }
        catch (NotFoundException e)
        {
            System.out.println("Not found");
            rep = new HTTPReply(ReturnCode.NOT_FOUND, new HashMap<HTTPOption, HTTPHeader>());
        }

        try
        {
            //send the reply on the OutputStream
            rep.reply(sock.getOutputStream());
            System.out.println("replied");
        }
        catch (OptionNotPresentException | BadFileException e)
        {
            // if we get there, no BadRequestException was raised, therefore having those exception is not possible
        }
    }
}
