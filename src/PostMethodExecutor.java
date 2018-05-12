import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PostMethodExecutor extends MethodExecutor{

    public PostMethodExecutor(){
    }

    /**
     * process the request as a post method;
     * it updates the cookies of the client;
     * The body of the POST request is expected to have this format : "colors=color1+color2+color3+color4"
     * it then redirect to the main page
     *
     * @param url         the url of the post request
     * @param headers     the headers of the post request
     * @param requestBody the body of the post request
     * @return a redirection reply to the mastermind page
     * @throws BadRequestException in case the request is not valid
     * @throws NotFoundException   in case the url does not correspond to an existing page
     */
    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException{

        if (!url.equals("/play.html"))
            throw new NotFoundException();

        //get the content-type
        String type = headers.get(HTTPOption.CONTENT_TYPE).getValue();

        try{
            //if the content-type field does not exist or has an invalid value
            if (type == null || FileType.getCorrespondingFileType(type) != FileType.URL)
                throw new BadRequestException();
        }
        catch (BadFileException e){
            throw new BadRequestException();
        }

        HashMap<HTTPOption, HTTPHeader> replyHeaders = new HashMap<HTTPOption, HTTPHeader>();

        // set the cookie field if the Executor according the cookie header of he request and set the reply cookie header
        this.manageCookies(headers, replyHeaders);
        //split the body between the "colors" part and the part that hold the combination
        StringTokenizer token = new StringTokenizer(requestBody, "=");

        try{
            //Check if the first part is "colors"
            if (!(token.nextToken().equals("colors")))
                throw new BadRequestException();

            // build the combination according to what's given in the body
            Combination combi = new Combination(token.nextToken());

            //evaluate the combination
            combi.evaluate(this.cookie.getRightCombination());

            //addthis try to the game state
            this.cookie.addTry(combi);

            //Check for victory/defeat
            if (combi.getResults()[0] == Combination.COMBI_LENGTH || cookie.getCurrentTry() == HTMLPage.LIVES){
                cookie.reset();
            }
        }
        catch (NoSuchElementException | BadFormatException | BadColorException e){
            //invalid request, do not add.
        }

        return new HTTPRedirectionReply(HTMLPage.HTML_FILE);

    }
}