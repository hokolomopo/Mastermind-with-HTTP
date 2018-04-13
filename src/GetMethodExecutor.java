import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GetMethodExecutor extends MethodExecutor {

	
    public GetMethodExecutor() {
    }

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> requestHeaders, String requestBody) throws BadRequestException, NotFoundException {

        if (url.charAt(0) != '/')
            throw new NotFoundException();

        if(url.equals("/")){
            return new HTTPRedirectionReply(HTMLPage.HTML_FILE);
        }

        HashMap<HTTPOption, HTTPHeader> replyHeaders = new HashMap<HTTPOption, HTTPHeader>();
        Object replyBody;

        //Todo: add other headers
        replyHeaders.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, getServerTime()));
        this.manageCookies(requestHeaders, replyHeaders);
        
        boolean isRequest = false;

        try {
            isRequest = url.startsWith("request?", 1);
        }
        catch (IndexOutOfBoundsException e) {
            //let isRequest to false;
        }
        if (isRequest) {
            String request = url.substring(9);
            String[] splittedRequest = request.split("=");
            if (!splittedRequest[0].equals("colors") || splittedRequest.length != 2)
                throw new BadRequestException();

            try {
            	
            	
                Combination testedCombi = new Combination(splittedRequest[1]);
                testedCombi.evaluate(this.cookie.getRightCombination());
                this.cookie.addTry(testedCombi);
                int[] results = testedCombi.getResults();
                
                replyBody = (this.cookie.getCurrentTry() - 1) + "+" + results[0] + "+" + results[1];

                replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
                replyHeaders.put(HTTPOption.CONTENT_LENGTH, new HTTPHeader(HTTPOption.CONTENT_LENGTH, String.valueOf(((String)replyBody).length())));
                
                //Check for victory
                if(results[0] == Combination.COMBI_LENGTH || cookie.getCurrentTry() == HTMLPage.LIVES) {              	
                	cookie.reset();
                }
            }
            catch(BadFormatException | BadColorException e){
                return new HTTPRedirectionReply(HTMLPage.HTML_FILE); // invalid combination -> get back to the page
            }
        }

        else if (url.endsWith(".png")) { // it is an image
            try {
                replyBody = ImageIO.read(new File(url.substring(1)));
                replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.PNG.getContentType()));

                replyHeaders.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
                replyHeaders.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));

            }
            catch (IOException e) {
                throw new BadRequestException();
            }
        }
        else if (url.equals("/" + HTMLPage.HTML_FILE)) { // it is the page
        	HTMLPage page = null;
        	
			try {
				page = new HTMLPage();
			} catch (IOException e) {
                throw new BadRequestException();
			}
			
        	this.cookie.setUpHTMLPage(page);
            replyBody = page.getHtmlCode();
            replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
            replyHeaders.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
            replyHeaders.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));

        }
        else { // invalid url
            throw new NotFoundException();
        }

        return new HTTPReply(ReturnCode.OK, replyHeaders, replyBody);
    }
    
}