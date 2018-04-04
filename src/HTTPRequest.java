import java.util.ArrayList;
import java.util.StringTokenizer;

public class HTTPRequest extends HTTP{

    private ArrayList<HTTPHeader> headers;
    private HTTPMethod method;
    private String url;

    public HTTPRequest(ArrayList<String> request) throws BadMessageException, BadMethodException, BadVersionException {

        try {
            StringTokenizer token = new StringTokenizer(request.get(0), " ");

            method = HTTPMethod.getCorrespondingMethod(token.nextToken());
            url = token.nextToken(); //TODO: vérif validité url?
            if(Float.parseFloat(token.nextToken()) != HTTP_VERSION)
                throw new BadVersionException();

            headers = new ArrayList<HTTPHeader>();

            int i = 1;
            String tmpHeader = request.get(i);

            while (!tmpHeader.isEmpty()) {
                headers.add(new HTTPHeader(tmpHeader));
                i++;
                tmpHeader = request.get(i);
            }
        }
        catch (IndexOutOfBoundsException | BadHeaderException e) {
            throw new BadMessageException();
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

    public ArrayList<HTTPHeader> getHeaders() {
        return headers; //TODO: clone?
    }
}
