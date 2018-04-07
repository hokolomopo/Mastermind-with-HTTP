import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GetMethodExecutor extends MethodExecutor{

    //Todo: gérer la possibilité de set des paramètres dans l'url
    public GetMethodExecutor(){}

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> requestHeaders, String requestBody, HTMLPage page) throws BadRequestException{

        if(url.charAt(0) != '/')
            throw new BadRequestException();

        HashMap<HTTPOption, HTTPHeader> replyHeaders = new HashMap<HTTPOption, HTTPHeader>();
        Object replyBody;

        //Todo: add other headers
        replyHeaders.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, getServerTime()));

        if(url.endsWith(".png")){ // it is an image
            try {
                replyBody = ImageIO.read(new File(url.substring(1)));
                replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.PNG.getContentType()));

                //compute image file size
                ByteArrayOutputStream tmp = new ByteArrayOutputStream();
                ImageIO.write((BufferedImage) replyBody, "png", tmp);
                replyHeaders.put(HTTPOption.CONTENT_LENGTH, new HTTPHeader(HTTPOption.CONTENT_LENGTH, String.valueOf(tmp.size())));
            }
            catch(IOException e){
                throw new BadRequestException();
            }
        }
        else if(url.equals("/") || url.equals("/page.html")){ // it is the page
            replyBody = page.getHtmlCode();
            replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
            //Todo: la ligne en dessous est de la grosse merde juste en attendant de faire chunck encoding car ca va surement changer
            replyHeaders.put(HTTPOption.CONTENT_LENGTH, new HTTPHeader(HTTPOption.CONTENT_LENGTH, String.valueOf(((String) replyBody).length()*4)));
        }
        else{ // invalid url
            throw new BadRequestException();
        }

        return new HTTPReply(ReturnCode.OK, replyHeaders, replyBody);
    }
}