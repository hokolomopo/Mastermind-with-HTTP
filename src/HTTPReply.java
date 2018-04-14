import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;


/**
 * class the represent a reply of the HTTP protocol
 */
public class HTTPReply extends HTTP {
	private static final int CHUNCK_SIZE = 128;
	
    protected ReturnCode ret;
    protected HashMap<HTTPOption, HTTPHeader> headers;
    protected Object body;

    /**
     * constructor of a reply that has a body
     * @param ret the return code of the reply
     * @param headers the headers of the reply
     * @param body the body of the reply
     */
    public HTTPReply(ReturnCode ret, HashMap<HTTPOption, HTTPHeader> headers, Object body) {
        this.ret = ret;
        this.headers = headers;
        this.body = body;
    }

    /**
     * constructor of a reply that has not a body
     * @param ret the return code of the reply
     * @param headers the headers of the reply
     */
    public HTTPReply(ReturnCode ret, HashMap<HTTPOption, HTTPHeader> headers) {
        this.ret = ret;
        this.headers = headers;
        this.body = null;
    }

    protected HTTPReply(){}

    /**
     * reply on the OutputStream given in argument as it is defined by the HTTP protocol
     * @param out the OutputStream on which to reply
     * @throws IOException in case of error writing on the OutputStream
     * @throws OptionNotPresentException in case there is a body but no Content-type header
     * @throws BadFileException in case the content-type header value is not a valid type
     */
    public void reply(OutputStream out) throws IOException, OptionNotPresentException, BadFileException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        //write first line of the reply
        writer.write("HTTP/" + HTTP_VERSION + " " + ret.getCode() + " " + ret.getStatus() + "\r\n");

        System.out.println("headerValue = ");

        //write the headers
        for (HTTPHeader header : headers.values()){
            System.out.println(header.getOption().getName() + ":" + header.getValue());
            writer.write(header.getOption().getName() + ":" + header.getValue() + "\r\n");
        }

        writer.write("\r\n");
        writer.flush();

        if (body != null) { // write body only if it exists
            System.out.println("body not null");

            //get content-type header
            HTTPHeader typeHeader = headers.get(HTTPOption.CONTENT_TYPE);

            //no content-type header
            if (typeHeader == null) {
                throw new OptionNotPresentException();
            }

            //get the FileType corresponding to the content-type header value.
            FileType type = FileType.getCorrespondingFileType(typeHeader.getValue());
            

            if (type == FileType.PNG) { // it is an image
            	
            	ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            	
                if (!ImageIO.write((BufferedImage) body, "png", imgStream)) {
                    throw new IOException();
                }
                
                this.sendInChunks(this.convertToGzipStream(imgStream), out);
            }
            else { // it is not an image and it is thus a string
            	
            	if(headers.get(HTTPOption.CONTENT_ENCODING) == null || headers.get(HTTPOption.TRANSFER_ENCODING) == null) {
            		System.out.println("AJAX");
            		writer.write((String)body);
            	}
            	else {
            		System.out.println("Normalement");
            		this.sendInChunks(this.convertToGzipStream((String)body), out);
            	}
            }
        }

        writer.flush();
    }
    

    private ByteArrayOutputStream convertToGzipStream(ByteArrayOutputStream s) throws IOException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(stream);
        gzip.write(s.toByteArray());
        gzip.close();

		return stream;

    }

    private ByteArrayOutputStream convertToGzipStream(String toSend) throws IOException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(stream);
		gzip.write(toSend.getBytes());
		gzip.close();

		return stream;

    }
    
    private void sendInChunks(ByteArrayOutputStream stream, OutputStream out) throws IOException {
    	String separator = "\r\n";

    	int sizeSent = 0 ;
    	byte[] b = stream.toByteArray();
    	
    	int i = 0;
    	while(i < b.length) {
    		if(b.length - i >= CHUNCK_SIZE) 
    			sizeSent = CHUNCK_SIZE;
    		else
    			sizeSent = b.length - i;
    		
    		out.write((Integer.toHexString(sizeSent) + separator).getBytes());
    		out.write(b, i, sizeSent);
    		out.write(separator.getBytes());
    		
    		i+= sizeSent;

    	}
    	
		out.write((Integer.toHexString(0) + separator).getBytes());
		out.write(separator.getBytes());
		out.flush();
    }


    public void setRet(ReturnCode ret) {
        this.ret = ret;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setHeaders(HashMap<HTTPOption, HTTPHeader> headers) {
        this.headers = headers;
    }

    public ReturnCode getRet() {
        return ret;
    }

    public HashMap<HTTPOption, HTTPHeader> getHeaders() {
        return headers;
    }

    public Object getBody() {
        return body;
    }
    
    
}

