import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PostMethodExecutor extends MethodExecutor{

    public PostMethodExecutor{}

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody, HTMLPage html) throws BadRequestException{

        if(!url.equals("/page/html"))
            throw new BadRequestException();

        String type = headers.get(HTTPOption.CONTENT_TYPE).getValue();

        try {
            if (type == null || FileType.getCorrespondingFileType(type) != FileType.URL)
                throw new BadRequestException();
        }
        catch(BadFileException e){
            throw new BadRequestException();
        }

        StringTokenizer token = new StringTokenizer(requestBody, "=");
        try {
            if (token.nextToken() != "colors")
                throw new BadRequestException();

            Combination combi = new Combination(token.nextToken());
            combi.evaluate(html.getCorrectCombi());

            String body = combi.getResults()[0] + " + " + combi.getResults()[1];

            HashMap<HTTPOption, HTTPHeader> replyHeaders = new HashMap<HTTPOption, HTTPHeader>();
            replyHeaders.put(HTTPOption.DATE, new HTTPHeader(HTTPOption.DATE, getServerTime()));
            replyHeaders.put(HTTPOption.CONTENT_TYPE, new HTTPHeader(HTTPOption.CONTENT_TYPE, FileType.URL.getContentType()));
            //Todo: la ligne en dessous est de la grosse merde juste en attendant de faire chunck encoding car ca va surement changer
            replyHeaders.put(HTTPOption.CONTENT_LENGTH, new HTTPHeader (HTTPOption.CONTENT_LENGTH, String.valueOf(body.length()*4)));

            return new HTTPReply(ReturnCode.OK, replyHeaders, body);
        }
        catch(NoSuchElementException | BadFormatException | BadColorException e){
            throw new BadRequestException();
        }
    }
}