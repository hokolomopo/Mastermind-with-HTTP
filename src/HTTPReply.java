import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class HTTPReply extends HTTP{

    private ReturnCode ret;
    private ArrayList<HTTPHeader> headers;
    private Object body;

    public HTTPReply(ReturnCode ret, ArrayList<HTTPHeader> headers, Object body){
        this.ret = ret;
        this.headers = headers;
        this.body = body;
    }

    public void reply(OutputStream out) throws IOException {

        BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(out));

        writer.write("HTTP/" + HTTP_VERSION + " " + ret.getCode() + " " + ret.getStatus() + "\r\n");

        for(int i = 0; i < headers.size(); i++)
            writer.write(headers.get(i).getOption().getName() + ":" + headers.get(i).getValue() + "\r\n");

        writer.write("\r\n");

        FileType type;

        try{
            type = HTTPHeader.findHeaderValue(headers, HTTPOption.CONTENT_TYPE);

            if(type == FileType.PNG){ // it is an image
                if(!ImageIO.write((BufferedImage)body, "png", out))
                    throw new IOException();
            }
            else{ // it is not an image and it is thus a string
                writer.write((String) body);
            }
        }
        catch(OptionNotPresentException | BadFileException e){
            // that should not happen while reply is built by us.
        }
    }
}
