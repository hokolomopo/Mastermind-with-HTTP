import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PostMethodExecutor extends MethodExecutor {

    public PostMethodExecutor() {
    }

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException {
    	
    	System.out.println("Post things");
    	
        if (!url.equals("/play.html"))
            throw new BadRequestException();

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

            HTMLPage page = null;
            try {
                page = new HTMLPage();
            } catch (IOException e) {
                throw new BadRequestException();
            }

            if(this.cookie.isDuplicate(combi)){
                return new HTTPRedirectionReply(HTMLPage.HTML_FILE);
            }

            combi.evaluate(this.cookie.getRightCombination());
            this.cookie.addTry(combi);

            this.cookie.setUpHTMLPage(page);

            System.out.println("generate code");
            String replyBody = page.getHtmlCode();

            replyHeaders.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, getServerTime()));
            replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
            //Todo: la ligne en dessous est de la grosse merde juste en attendant de faire chunck encoding car ca va surement changer
            // replyHeaders.put(HTTPOption.CONTENT_LENGTH, new HTTPHeader(HTTPOption.CONTENT_LENGTH, String.valueOf(replyBody.length() * 4)));
            replyHeaders.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
            replyHeaders.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));

            //check for victory/deafeat
            if(combi.getResults()[0] == Combination.COMBI_LENGTH || cookie.getCurrentTry() == HTMLPage.LIVES) {
            	cookie.reset();
            }

            return new HTTPReply(ReturnCode.OK, replyHeaders, replyBody);
        }
        catch (NoSuchElementException | BadFormatException | BadColorException e) {
            throw new BadRequestException();
        }
    }
}