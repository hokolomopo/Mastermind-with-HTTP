public class HTTPReply extends HTTP{

    private ReturnCode ret;
    private HTTPHeader[] headers;
    private String[] body;

    public HTTPReply(ReturnCode ret, HTTPHeader[] headers, String[] body){
        this.ret = ret;
        this.headers = headers;
        this.body = body;
    }

    public String[] buildReply(){

        String[] reply = new String[2+headers.length+body.length]; // the 2 take into account first line and header/body separation

        reply[0] = "HTTP/" + HTTP_VERSION + " " + ret.getCode() + " " + ret.getStatus();

        for(int i = 0; i < headers.length; i++)
            reply[i+1] = headers[i].getOption().getName() + ":" + headers[i].getValue();

        reply[headers.length+1] = "";

        for(int i = 0; i < body.length; i++){
            reply[headers.length+2+i] = body[i];
        }

        return reply;
    }
}
