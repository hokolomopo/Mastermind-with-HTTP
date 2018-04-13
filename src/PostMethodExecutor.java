import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PostMethodExecutor extends MethodExecutor {

    public PostMethodExecutor() {
    }

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException {
    	
    	System.out.println("Post things");
    	
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
            if(!(token.nextToken().equals("colors")))
                throw new BadRequestException();

            Combination combi = new Combination(token.nextToken());
            
            combi.evaluate(this.cookie.getRightCombination());
            this.cookie.addTry(combi);

            //check for victory/deafeat
            if(combi.getResults()[0] == Combination.COMBI_LENGTH || cookie.getCurrentTry() == HTMLPage.LIVES) {
            	cookie.reset();
            }

            return new HTTPRedirectionReply(HTMLPage.HTML_FILE);
        }
        catch (NoSuchElementException | BadFormatException | BadColorException e) {
            throw new BadRequestException();
        }
    }
}