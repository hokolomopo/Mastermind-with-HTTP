import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GetMethodExecutor extends MethodExecutor{

    public GetMethodExecutor{}

    public HTTPReply process(String url, ArrayList<HTTPHeader> requestHeaders, HTMLPage page) throws BadRequestException{
        if(url.charAt(0) != '/')
            throw new BadRequestException();

        ReturnCode ret;
        ArrayList<HTTPHeader> replyHeaders = new ArrayList<HTTPHeader>();
        Object body;

        if(url.contains("png")){ // it is an image
            replyHeaders.add(new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.PNG.getContentType()));
            try {
                body = ImageIO.read(new File(url.substring(1)));
            }
            catch(IOException e){
                throw new BadRequestException();
            }
        }
        else if(url.equals("/") || url.equals("/page.html")){ // it is the page
            replyHeaders.add(new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.HTML.getContentType()));
            body = page.getHtmlCode();
        }
        else{ // invalid url
            throw new BadRequestException();
        }

        ret = ReturnCode.OK; //Todo: gérer les différents cas pour les retrun code, si nécessaire?

        return new HTTPReply(ret, replyHeaders, body);
    }
}