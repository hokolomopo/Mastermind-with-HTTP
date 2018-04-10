import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class HTTPReply extends HTTP {

    private ReturnCode ret;
    private HashMap<HTTPOption, HTTPHeader> headers;
    private Object body;

    public HTTPReply(ReturnCode ret, HashMap<HTTPOption, HTTPHeader> headers, Object body) {
        this.ret = ret;
        this.headers = headers;
        this.body = body;
    }

    public HTTPReply(ReturnCode ret, HashMap<HTTPOption, HTTPHeader> headers) {
        this.ret = ret;
        this.headers = headers;
        this.body = null;
    }

    public void reply(OutputStream out) throws IOException, OptionNotPresentException, BadFileException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        writer.write("HTTP/" + HTTP_VERSION + " " + ret.getCode() + " " + ret.getStatus() + "\r\n");

        System.out.println("headerValue = ");
        for (HTTPHeader header : headers.values()){
            System.out.println(header.getOption().getName() + ":" + header.getValue());
            writer.write(header.getOption().getName() + ":" + header.getValue() + "\r\n");
        }

        writer.write("\r\n");
<<<<<<< HEAD

        System.out.println("body?");
=======
        writer.flush();
        
>>>>>>> master
        if (body != null) { // write body only if it exists
            System.out.println("body not null");
            FileType type;

            type = FileType.getCorrespondingFileType(headers.get(HTTPOption.CONTENT_TYPE).getValue());
            
            if (type == null) {
                throw new OptionNotPresentException();
            }

            if (type == FileType.PNG) { // it is an image
            	
            	
                if (!ImageIO.write((BufferedImage) body, "png", out)) {
                    throw new IOException();
                }
            }
            else { // it is not an image and it is thus a string
            	this.sendInChunks((String)body, writer);
            }
        }

        writer.flush();
    }
    

    private void sendInChunks(String toSend, BufferedWriter writer) throws IOException {

    	int chunkSize = 128;
    	String separator = "\r\n";
    	
    	int sizeSent = 0 ;
    	while(toSend.length() > 0) {
    		
    		if(toSend.length() >= chunkSize) 
    			sizeSent = chunkSize;
    		else
    			sizeSent = toSend.length();
    		
			writer.write(Integer.toHexString(sizeSent) + separator);
			writer.write(toSend.substring(0, sizeSent) + separator);
			toSend = toSend.substring(sizeSent);

    	}
    	
		writer.write(Integer.toHexString(0) + separator);
		writer.write(separator);
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

