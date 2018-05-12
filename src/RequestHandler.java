import java.io.IOException;
import java.net.Socket;

/**
 * class handling the requests
 */
public class RequestHandler{

    private RequestHandler(){
    }

    /**
     * handle one request (read the request, process it and reply)
     *
     * @param sock The socket from which the request will be received and the reply sent
     * @throws IOException in case of error with the socket
     */
    public static void handleRequest(Socket sock) throws IOException{

        HTTPReply rep;

        try{
            //read the request on the InputStream
            HTTPRequest req = new HTTPRequest(sock.getInputStream());
            
            //process the request in order to build the appropriate reply
            rep = req.getMethod().process(req.getUrl(), req.getHeaders(), req.getBody());
        }
        //handle the exceptions and send the corresponding error return code
        catch (BadRequestException e){
            rep = new HTTPErrorReply(ReturnCode.BAD_REQUEST);
        }
        catch (BadMethodException e){
            rep = new HTTPErrorReply(ReturnCode.NOT_IMPLEMENTED);
        }
        catch (BadVersionException e){
            rep = new HTTPErrorReply(ReturnCode.HTTP_VERSION_NOT_SUPPORTED);
        }
        catch (NotFoundException e){
            rep = new HTTPErrorReply(ReturnCode.NOT_FOUND);
        }
        catch (LengthRequieredException e){
            rep = new HTTPErrorReply(ReturnCode.LENGTH_REQUIERED);
        }

        try{
            //send the reply on the OutputStream
            rep.reply(sock.getOutputStream());
        }
        catch (OptionNotPresentException | BadFileException e){
            // if we get there, no BadRequestException was raised, therefore having those exceptions is not possible
        }
    }
}
