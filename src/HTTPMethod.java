import java.util.HashMap;

/**
 * enumeration holding the different method of the http protocol
 */
public enum HTTPMethod{

    GET("GET", "GetMethodExecutor"),
    POST("POST", "PostMethodExecutor"),
    HEAD("HEAD", "HeadMethodExecutor");

    //the executor associated to the method, this is what describe the behaviour of the method
    private Class exec;

    private String name;

    private HTTPMethod(String name, String exec){
        this.name = name;
        try{
            this.exec = Class.forName(exec);
        }
        catch (ClassNotFoundException e){
            System.err.println("The file " + exec + ".java is missing");
            System.exit(1);
        }
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
    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException{
        try{
            MethodExecutor execInstance = (MethodExecutor) exec.getConstructor().newInstance();
            return execInstance.process(url, headers, requestBody);
        }
        catch (ReflectiveOperationException e){
            System.err.println("Unexpected error");
            System.exit(1);
        }

        return null; //will not be executed, just for the code to compile.
    }

    /**
     * get the method corresponding to the name given in argument
     *
     * @param name a string holding the name of the searched method
     * @return the method which name match the name argument
     * @throws BadMethodException in case there is not method which name match the name argument
     */
    public static HTTPMethod getCorrespondingMethod(String name) throws BadMethodException{

        for (HTTPMethod request : HTTPMethod.values()){
            if (request.name.equals(name))
                return request;
        }
        throw new BadMethodException();
    }

    public String getName(){
        return name;
    }
}