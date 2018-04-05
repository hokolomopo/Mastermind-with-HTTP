import java.util.ArrayList;

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

    public HTTPReply process(String url, ArrayList<HTTPHeader> headers, HTMLPage page){
        return exec.process(url, headers, page);
    }

    public static HTTPMethod getCorrespondingMethod(String name) throws BadMethodException{

        for(HTTPMethod request : HTTPMethod.values()){
            if(request.name == name)
                return request;
        }
        throw new BadMethodException();
    }
}