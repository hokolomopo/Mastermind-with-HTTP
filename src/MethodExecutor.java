import java.util.HashSet;

public abstract class MethodExecutor extends HTTP{

    public abstract HTTPReply process(String url, HashSet<HTTPHeader> headers, HTMLPage html) throws BadRequestException;

}