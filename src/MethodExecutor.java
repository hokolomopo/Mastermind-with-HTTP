import java.util.HashMap;

public abstract class MethodExecutor extends HTTP {
	protected Cookie cookie;
	
    public abstract HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException;

    protected void manageCookies(HashMap<HTTPOption, HTTPHeader> requestHeaders, HashMap<HTTPOption, HTTPHeader> replyHeaders) {
    	//Get a cookie header if any
    	HTTPHeader cookieHeader = requestHeaders.get(HTTPOption.COOKIE);

    	
    	//Create new cookie if no cookie set in client
    	if(cookieHeader == null) {
    		System.out.println("No cookie set");
    		Combination combi = new Combination();
    		combi.setRandomCombi();
    		this.cookie = new Cookie(combi);
    		System.out.println("New cookie id = " + cookie.getId());
    		WebServer.addCookie(cookie);
            replyHeaders.put(HTTPOption.SET_COOKIE, new HTTPHeader(HTTPOption.SET_COOKIE, cookie.getId() + "; path=/"));
    	}
    	else {
        	String headerValue = cookieHeader.getValue();
        	
	    	//Check if ther's useless spaces at the start and delete them
	    	while(headerValue.startsWith(" "))
	    		headerValue = headerValue.substring(1);

	    	this.cookie = WebServer.getCookie(headerValue);
    		
    		if(this.cookie == null) {
        		System.out.println("No cookie found");
        		
        		Combination combi = new Combination();
        		combi.setRandomCombi();

        		this.cookie = new Cookie(combi, headerValue);
        		
        		WebServer.addCookie(this.cookie);
        		System.out.println("Cookie id = " + this.cookie.getId());
    		}
    		else {
        		System.out.println("Cookie found id = " + this.cookie.getId());
        		WebServer.addCookie(this.cookie);
    		}

    	}

    }

}