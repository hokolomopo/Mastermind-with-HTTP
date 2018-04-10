import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

public class HTTPReply extends HTTP {
	private static final int CHUNCK_SIZE = 128;
	
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
        writer.flush();

        if (body != null) { // write body only if it exists
            System.out.println("body not null");
            FileType type;

            type = FileType.getCorrespondingFileType(headers.get(HTTPOption.CONTENT_TYPE).getValue());
            
            if (type == null) {
                throw new OptionNotPresentException();
            }

            if (type == FileType.PNG) { // it is an image
            	
            	ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
            	
                if (!ImageIO.write((BufferedImage) body, "png", imgStream)) {
                    throw new IOException();
                }
                
                this.sendInChunks(this.convertToGzipStream(imgStream), out);
            }
            else { // it is not an image and it is thus a string
            	this.sendInChunks(this.convertToGzipStream((String)body), out);
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

