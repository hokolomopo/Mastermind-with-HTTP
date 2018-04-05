import java.util.ArrayList;

public abstract class MethodExecutor{

    public abstract HTTPReply process(String url, ArrayList<HTTPHeader> headers, HTMLPage html) throws BadRequestException;

}