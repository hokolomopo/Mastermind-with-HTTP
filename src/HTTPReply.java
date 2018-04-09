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

        if (body != null) { // write body only if it exists
            FileType type;

            type = FileType.getCorrespondingFileType(headers.get(HTTPOption.CONTENT_TYPE).getValue());
            if (type == null) {
                throw new OptionNotPresentException();
            }

            if (type == FileType.PNG) { // it is an image
                if (!ImageIO.write((BufferedImage) body, "png", out))
                    throw new IOException();
            }
            else { // it is not an image and it is thus a string
                writer.write((String) body);
            }
        }

        writer.flush();
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
