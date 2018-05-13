import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * An executor which process function correspond the one executed when a GET method is sent
 */
public class GetMethodExecutor extends MethodExecutor{


    public GetMethodExecutor(){
    }

    /**
     * process the get request in order to build the appropriate reply.
     *
     * @param url            the url of the get request
     * @param requestHeaders the headers of the get request
     * @param requestBody    the body of the get request
     * @return the appropriate reply held by a HTTPReply object
     * @throws BadRequestException in case the request is not valid
     * @throws NotFoundException   in case the url does not correspond to an existing page
     */
    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> requestHeaders, String requestBody) throws BadRequestException, NotFoundException{

        //every url should start with '/'
        if (url.charAt(0) != '/')
            throw new NotFoundException();

        //redirect to root page
        if (url.equals("/")){
            return new HTTPRedirectionReply(HTMLPage.HTML_FILE);
        }


        //the headers are held in a hashMap to facilitate their research according to their HTTPOption (the type of value held by the header)
        HashMap<HTTPOption, HTTPHeader> replyHeaders = new HashMap<HTTPOption, HTTPHeader>();
        Object replyBody;

        //add date header
        replyHeaders.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, getServerTime()));

        //manage the cookie in order to set the cookie objected associated to this executor to the one corresponding to the headers of the request
        this.manageCookies(requestHeaders, replyHeaders);

        // boolean indicating whether the request is an AJAX request to set a new try
        boolean isRequest = false;

        try{
            isRequest = url.startsWith("request?", 1);
        }
        catch (IndexOutOfBoundsException e){
            //let isRequest to false;
        }

        synchronized (cookie){
            // if the request is an AJAX request to set a new try
            if (isRequest){

                // remove the "request?" part
                String request = url.substring(9);

                //split into "colors" and the part that hold the combination
                String[] splittedRequest = request.split("=");

                if (!splittedRequest[0].equals("colors") || splittedRequest.length != 2)
                    throw new BadRequestException();

                try{
                    //build a new combination according to the combination part of the request
                    Combination testedCombi = new Combination(splittedRequest[1]);

                    //evaluate the combination
                    testedCombi.evaluate(this.cookie.getRightCombination());

                    //save the new try in the cookie
                    this.cookie.addTry(testedCombi);

                    //build the body with the results of the evaluation
                    int[] results = testedCombi.getResults();
                    replyBody = (this.cookie.getCurrentTry() - 1) + "+" + results[0] + "+" + results[1];

                    // add type and length headers
                    replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
                    replyHeaders.put(HTTPOption.CONTENT_LENGTH, new HTTPHeader(HTTPOption.CONTENT_LENGTH, String.valueOf(((String) replyBody).length())));

                    //Check for victory
                    if (results[0] == Combination.COMBI_LENGTH || cookie.getCurrentTry() == HTMLPage.LIVES){
                        cookie.reset();
                    }
                }
                catch (BadFormatException | BadColorException e){
                    return new HTTPRedirectionReply(HTMLPage.HTML_FILE); // invalid combination -> get back to the page
                }
            }

            // it is a known file
            else if (WebsiteFiles.getFile(url.substring(1)) != null){

                WebsiteFiles file = WebsiteFiles.getFile(url.substring(1));

                try{
                    replyBody = file.file2String();
                }
                catch (IOException e){
                    throw new NotFoundException();
                }

                replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, file.getContentType()));
                replyHeaders.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
                replyHeaders.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));
                replyHeaders.put(HTTPOption.EXPIRES, new HTTPHeader(HTTPOption.EXPIRES, "Fri, 31 Dec 2100 23:59:59 GMT"));


            }

            // it is an image
            else if (url.endsWith(".png")){
                try{
                    // set the body to a bufferedImage read in the file corresponding the path given in the url
                    replyBody = ImageIO.read(new File(url.substring(1)));

                    //set headers
                    replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.PNG.getContentType()));
                    replyHeaders.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
                    replyHeaders.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));
                    replyHeaders.put(HTTPOption.EXPIRES, new HTTPHeader(HTTPOption.EXPIRES, "Fri, 31 Dec 2100 23:59:59 GMT"));

                }
                catch (IOException e){
                    throw new BadRequestException();
                }
            }

            //the page is requested
            else if (url.equals("/" + HTMLPage.HTML_FILE)){
                HTMLPage page = null;

                try{
                    page = new HTMLPage();
                }
                catch (IOException e){
                    throw new BadRequestException();
                }

                //set the page according to the game's state
                this.cookie.setUpHTMLPage(page);

                //set the body with the page's HTML code
                replyBody = page.getHtmlCode();

                //set the headers
                replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
                replyHeaders.put(HTTPOption.TRANSFER_ENCODING, new HTTPHeader(HTTPOption.TRANSFER_ENCODING, "chunked"));
                replyHeaders.put(HTTPOption.CONTENT_ENCODING, new HTTPHeader(HTTPOption.CONTENT_ENCODING, "gzip"));

            }
            else{ // invalid url
                throw new NotFoundException();
            }
        }

        // if we get here, all went ok, return the reply with OK return code
        return new HTTPReply(ReturnCode.OK, replyHeaders, replyBody);
    }

}