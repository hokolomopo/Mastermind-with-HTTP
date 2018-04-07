import java.util.HashMap;

public enum HTTPMethod{

    GET("GET", new GetMethodExecutor()),
    POST("POST", new PostMethodExecutor()),
    HEAD("HEAD", new HeadMethodExecutor());

    private MethodExecutor exec;

    private String name;

    private HTTPMethod(String name, MethodExecutor exec){
        this.name = name;
        this.exec = exec;
    }

    public HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody, HTMLPage page) throws BadRequestException{
        return exec.process(url, headers, requestBody, page);
    }

    public static HTTPMethod getCorrespondingMethod(String name) throws BadMethodException{

        for(HTTPMethod request : HTTPMethod.values()){
            if(request.name.equals(name))
                return request;
        }
        throw new BadMethodException();
    }

    public String getName() {
        return name;
    }

    public MethodExecutor getExec() {
        return exec;
    }
}