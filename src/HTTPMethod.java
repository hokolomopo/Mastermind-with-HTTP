import java.util.HashMap;

/**
 * enumeration holding the different method of the http protocol
 */
public enum HTTPMethod
{

    GET("GET", new GetMethodExecutor()),
    POST("POST", new PostMethodExecutor()),
    HEAD("HEAD", new HeadMethodExecutor());

    //the executor associated to the method, this is what describe the behaviour of the method
    private MethodExecutor exec;

    private String name;

    private HTTPMethod(String name, MethodExecutor exec)
    {
        this.name = name;
        this.exec = exec;
    }

    /**
     * call the process method of the executor associated to the HTTPMethod object
     *
     * @param url         the url of the get request
     * @param headers     the headers of the get request
     * @param requestBody the body of the get request
     * @return the appropriate reply held by a HTTPReply object
     * @throws BadRequestException in case the request is not valid
     * @throws NotFoundException   in case the url does not correspond to an existing page
     */
    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException
    {
        return exec.process(url, headers, requestBody);
    }

    /**
     * get the method corresponding to the name given in argument
     *
     * @param name a string holding the name of the searched method
     * @return the method which name match the name argument
     * @throws BadMethodException in case there is not method which name match the name argument
     */
    public static HTTPMethod getCorrespondingMethod(String name) throws BadMethodException
    {

        for (HTTPMethod request : HTTPMethod.values())
        {
            if (request.name.equals(name))
                return request;
        }
        throw new BadMethodException();
    }

    public String getName()
    {
        return name;
    }

    public MethodExecutor getExec()
    {
        return exec;
    }
}