import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class HTTPRequest extends HTTP{

    private HashMap<HTTPOption, HTTPHeader> headers;
    private HTTPMethod method;
    private String url;
    private String body;

    public HTTPRequest(InputStream in) throws BadRequestException, BadMethodException, BadVersionException, IOException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringTokenizer token = new StringTokenizer(reader.readLine(), " ");

            method = HTTPMethod.getCorrespondingMethod(token.nextToken());
            url = token.nextToken(); //TODO: vérif validité url?
            if(Float.parseFloat(token.nextToken()) != HTTP_VERSION)
                throw new BadVersionException();

            headers = new HashMap<HTTPOption, HTTPHeader>();

            String tmp = reader.readLine();
            HTTPHeader tmpHeader;

            while(!tmp.isEmpty()){
                tmpHeader = new HTTPHeader(tmp);
                headers.put(tmpHeader.getOption(), tmpHeader);
                tmp = reader.readLine();
            }

            if((tmp = headers.get(HTTPOption.CONTENT_TYPE).getValue()) != null){ // request has a body

                try {
                    if (!FileType.getCorrespondingFileType(tmp).isString())
                        throw new BadRequestException();
                }
                catch(BadFileException e){
                    throw new BadRequestException();
                }

                tmp = headers.get(HTTPOption.CONTENT_LENGTH).getValue();

                if(tmp == null)
                    throw new BadRequestException();

                try{
                    int length = Integer.parseInt(tmp); // Todo: length en byte ou en char?
                    body = new String();
                    for(int i = 0; i < length; i++){
                        body += reader.read();
                    }
                }
                catch(NumberFormatException e){
                    throw new BadRequestException();
                }
            }
            else{
                body = null;
            }
        }
        catch (IndexOutOfBoundsException | BadHeaderException e) {
            throw new BadRequestException();
        }
        catch(NumberFormatException e){
            throw new BadVersionException();
        }
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<HTTPOption, HTTPHeader> getHeaders() {
        return headers; //TODO: clone?
    }
}
