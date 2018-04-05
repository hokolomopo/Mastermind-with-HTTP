import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class HTTPRequest extends HTTP{

    private HashSet<HTTPHeader> headers;
    private HTTPMethod method;
    private String url;

    public HTTPRequest(InputStream in) throws BadRequestException, BadMethodException, BadVersionException, IOException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringTokenizer token = new StringTokenizer(reader.readLine(), " ");

            method = HTTPMethod.getCorrespondingMethod(token.nextToken());
            url = token.nextToken(); //TODO: vérif validité url?
            if(Float.parseFloat(token.nextToken()) != HTTP_VERSION)
                throw new BadVersionException();

            headers = new HashSet<HTTPHeader>();

            String tmp = reader.readLine();

            while(!tmp.isEmpty()){
                headers.add(new HTTPHeader(tmp));
                tmp = reader.readLine();
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

    public HashSet<HTTPHeader> getHeaders() {
        return headers; //TODO: clone?
    }
}
