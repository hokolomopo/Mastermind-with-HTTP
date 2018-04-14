import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PostMethodExecutor extends MethodExecutor {

    public PostMethodExecutor() {
    }

    /*
     * Manage HTTP reply to a POST method and update the cookie of the client
     * 
     * The body of the POST request is expected to have this format : 'colors=red+yellow+blue+black'
     * 
     * Returns a HTTPReply object suitable for the message receiver
     * 
     * Throws BadRequestException if :
     * 	-The HTTP header isn't as expected
     * 	-The request body isn't formatted correctly
     * 	-Colors are spelled wrong
     */
    /**
     * process the post request in order to build the appropriate reply.
     * @param url the url of the post request
     * @param headers the headers of the post request
     * @param requestBody the body of the post request
     * @return the appropriate reply held by a HTTPReply object
     * @throws BadRequestException in case the request is not valid
     * @throws NotFoundException in case the url does not correspond to an existing page
     */
    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException {
    	    	
        if (!url.equals("/play.html"))
            throw new NotFoundException();

        String type = headers.get(HTTPOption.CONTENT_TYPE).getValue();

        try {
            if (type == null || FileType.getCorrespondingFileType(type) != FileType.URL)
                throw new BadRequestException();
        }
        catch (BadFileException e) {
            throw new BadRequestException();
        }
        
        HashMap<HTTPOption, HTTPHeader> replyHeaders = new HashMap<HTTPOption, HTTPHeader>();

        this.manageCookies(headers, replyHeaders);
        StringTokenizer token = new StringTokenizer(requestBody, "=");
        
        try {
        	//Check
            if(!(token.nextToken().equals("colors")))
                throw new BadRequestException();

            Combination combi = new Combination(token.nextToken());
            
            combi.evaluate(this.cookie.getRightCombination());
            this.cookie.addTry(combi);

            //Check for victory/defeat
            if(combi.getResults()[0] == Combination.COMBI_LENGTH || cookie.getCurrentTry() == HTMLPage.LIVES) {
            	cookie.reset();
            }
        }
        catch (NoSuchElementException | BadFormatException | BadColorException e) {
            //invalid request, do not add.
        }
        
        return new HTTPRedirectionReply(HTMLPage.HTML_FILE);
        
    }
}