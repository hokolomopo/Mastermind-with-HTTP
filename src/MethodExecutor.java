import java.util.HashMap;

public abstract class MethodExecutor extends HTTP {

    public abstract HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody, HTMLPage html) throws BadRequestException;

}