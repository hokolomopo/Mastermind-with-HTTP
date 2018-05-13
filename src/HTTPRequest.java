import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * class representing a request of the HTTP protocol
 */
public class HTTPRequest extends HTTP{

    private HashMap<HTTPOption, HTTPHeader> headers;
    private HTTPMethod method;
    private String url;
    private String body;

    /**
     * construct a request based on the datas given by an InputStream which is meant to be connected to the client's browser
     *
     * @param in the InputStream connected to the client's browser
     * @throws BadRequestException in case the request is invalid
     * @throws BadMethodException  in case the method of the request doesn ot exist
     * @throws BadVersionException in case the HTTP version is not the one expected by the server
     * @throws IOException         in case of error with the InputStream
     */
    public HTTPRequest(InputStream in) throws BadRequestException, BadMethodException, BadVersionException, IOException, LengthRequieredException{

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));


            // read the first line and split it into the different elements
            StringTokenizer token = new StringTokenizer(reader.readLine(), " ");

            // read the method (the first element)
            method = HTTPMethod.getCorrespondingMethod(token.nextToken());

            //read the url (the second element)
            url = token.nextToken();

            //read the HTTP version (the third element)
            String version = token.nextToken();
            //check version validity
            StringTokenizer versionToken = new StringTokenizer(version, "/");
            if (!versionToken.nextToken().equals("HTTP") || Float.parseFloat(versionToken.nextToken()) != HTTP_VERSION)
                throw new BadVersionException();


            headers = new HashMap<HTTPOption, HTTPHeader>();

            //read the headers
            String tmp = reader.readLine();

            HTTPHeader tmpHeader;

            while (!tmp.isEmpty()){
                tmpHeader = new HTTPHeader(tmp);
                headers.put(tmpHeader.getOption(), tmpHeader);
                tmp = reader.readLine();
            }



            //read the body
            HTTPHeader content;
            if ((content = headers.get(HTTPOption.CONTENT_TYPE)) != null){ // request has a body

                //check if the body of the request is a string
                try{
                    if (!FileType.getCorrespondingFileType(content.getValue()).isString())
                        throw new BadRequestException();
                }
                catch (BadFileException e){
                    System.err.println("Bad file");
                    throw new BadRequestException();
                }

                //get the length of the body
                tmp = headers.get(HTTPOption.CONTENT_LENGTH).getValue();

                if (tmp == null)
                    throw new LengthRequieredException();

                try{
                    int length = Integer.parseInt(tmp);
                    body = new String();
                    //read the body
                    for (int i = 0; i < length; i++){
                        body += (char) reader.read();
                    }


                }
                catch (NumberFormatException e){
                    throw new BadRequestException();
                }
            }
            else{
                body = null;
            }
        }
        catch (IndexOutOfBoundsException | BadHeaderException e){
            throw new BadRequestException();
        }
        catch (NumberFormatException e){
            throw new BadVersionException();
        }
    }

    public HTTPMethod getMethod(){
        return method;
    }

    public String getUrl(){
        return url;
    }

    public HashMap<HTTPOption, HTTPHeader> getHeaders(){
        return headers; 
    }

    public String getBody(){
        return body;
    }

    public HTTPHeader getHeader(HTTPOption option){
        return headers.get(option);
    }
}
