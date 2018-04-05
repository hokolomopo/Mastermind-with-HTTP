import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;

public class HTTPReply extends HTTP{

    private ReturnCode ret;
    private HashSet<HTTPHeader> headers;
    private Object body;

    public HTTPReply(ReturnCode ret, HashSet<HTTPHeader> headers, Object body){
        this.ret = ret;
        this.headers = headers;
        this.body = body;
    }

    public HTTPReply(ReturnCode ret, HashSet<HTTPHeader> headers){
        this.ret = ret;
        this.headers = headers;
        this.body = null;
    }

    public void reply(OutputStream out) throws IOException {

        BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(out));

        writer.write("HTTP/" + HTTP_VERSION + " " + ret.getCode() + " " + ret.getStatus() + "\r\n");

        for(HTTPHeader header : headers)
            writer.write(header.getOption().getName() + ":" + header.getValue() + "\r\n");

        writer.write("\r\n");

        if(body != null) { // write body only if it exists
            FileType type;

            try {
                type = HTTPHeader.findHeaderValue(headers, HTTPOption.CONTENT_TYPE);

                if (type == FileType.PNG) { // it is an image
                    if (!ImageIO.write((BufferedImage) body, "png", out))
                        throw new IOException();
                } else { // it is not an image and it is thus a string
                    writer.write((String) body);
                }
            }
            catch (OptionNotPresentException | BadFileException e) {
                // that should not happen while reply is built by us.
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

    public void setHeaders(HashSet<HTTPHeader> headers) {
        this.headers = headers;
    }

    public ReturnCode getRet() {
        return ret;
    }

    public HashSet<HTTPHeader> getHeaders() {
        return headers;
    }

    public Object getBody() {
        return body;
    }
}
