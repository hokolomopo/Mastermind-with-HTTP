import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class HTTPRequest extends HTTP {

    private HashMap<HTTPOption, HTTPHeader> headers;
    private HTTPMethod method;
    private String url;
    private String body;

    public HTTPRequest(InputStream in) throws BadRequestException, BadMethodException, BadVersionException, IOException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            System.out.println("before reading");
            String mdr = "mdr";
            try {
                mdr = reader.readLine();
            }
            catch (IOException e) {
                System.out.println("IOException");
            }
            System.out.println("mdr = " + mdr);
            StringTokenizer token = new StringTokenizer(mdr, " ");
            System.out.println("read");
            method = HTTPMethod.getCorrespondingMethod(token.nextToken());
            System.out.println("method = " + method.getName());
            url = token.nextToken(); //TODO: vérif validité url?
            System.out.println("URL = " + url);
            String version = token.nextToken();
            StringTokenizer versionToken = new StringTokenizer(version, "/");
            if (!versionToken.nextToken().equals("HTTP") || Float.parseFloat(versionToken.nextToken()) != HTTP_VERSION)
                throw new BadVersionException();
            System.out.println("HTTP_VERSION OK");

            headers = new HashMap<HTTPOption, HTTPHeader>();

            String tmp = reader.readLine();
            System.out.println("tmp = " + tmp);
            HTTPHeader tmpHeader;

            while (!tmp.isEmpty()) {
                tmpHeader = new HTTPHeader(tmp);
                System.out.println("headerOption = " + tmpHeader.getOption().getName());
                System.out.println("headerValue = " + tmpHeader.getValue());
                headers.put(tmpHeader.getOption(), tmpHeader);
                tmp = reader.readLine();
                System.out.println("tmp = " + tmp);
            }

            System.out.println("end of loop");

            HTTPHeader content;
            if ((content = headers.get(HTTPOption.CONTENT_TYPE)) != null) { // request has a body

                try {
                    if (!FileType.getCorrespondingFileType(content.getValue()).isString())
                        throw new BadRequestException();
                }
                catch (BadFileException e) {
                    throw new BadRequestException();
                }

                tmp = headers.get(HTTPOption.CONTENT_LENGTH).getValue();

                if (tmp == null)
                    throw new BadRequestException();

                try {
                    int length = Integer.parseInt(tmp); // Todo: length en byte ou en char?
                    body = new String();
                    for (int i = 0; i < length; i++) {
                        body += reader.read();
                    }
                }
                catch (NumberFormatException e) {
                    throw new BadRequestException();
                }
            }
            else {
                body = null;
            }
        }
        catch (IndexOutOfBoundsException | BadHeaderException e) {
            throw new BadRequestException();
        }
        catch (NumberFormatException e) {
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

    public String getBody() {
        return body;
    }
}
