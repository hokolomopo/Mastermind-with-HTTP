import java.util.Calendar;
import java.util.HashMap;

public abstract class MethodExecutor extends HTTP {
	
	//Duration of cookie (in minutes)
	private final static int COOKIE_DURATION = 1;
	
	protected Cookie cookie;
	
    public abstract HTTPReply process(String url, HashMap<HTTPOption, HTTPHeader> headers, String requestBody) throws BadRequestException, NotFoundException;

    /*
     * Retrieve data for the cookie used from the user or create a new cookie if needed
     */
    protected void manageCookies(HashMap<HTTPOption, HTTPHeader> requestHeaders, HashMap<HTTPOption, HTTPHeader> replyHeaders) {
    	//Get a cookie header if any
    	HTTPHeader cookieHeader = requestHeaders.get(HTTPOption.COOKIE);

    	
    	//Create new cookie if no cookie set in client
    	if(cookieHeader == null) {
    		//Create a cookie
    		Combination combi = new Combination();
    		combi.setRandomCombi();
    		this.cookie = new Cookie(combi);
    		
    		//Add the cookie to the server's cookie list
    		WebServer.addCookie(cookie);
            replyHeaders.put(HTTPOption.SET_COOKIE, new HTTPHeader(HTTPOption.SET_COOKIE, cookie.getId() + "; path=/"));
    	}
    	
    	else {
    		//Try to fetch the cookie from the server's cookie list
        	String headerValue = cookieHeader.getValue();
	    	this.cookie = WebServer.getCookie(headerValue);
    		
	    	//No such cookie in server's cookie list
    		if(this.cookie == null) {
        		
    			//Create a new cookie
        		Combination combi = new Combination();
        		combi.setRandomCombi();
        		this.cookie = new Cookie(combi, headerValue);
        		
        		//Add the cookie to the server's cookie list
        		WebServer.addCookie(this.cookie);
    		}
    		//The cookie was found
    		else {
    			//Check if cookie hasn't lived past it's expiration date, reset if if it has
    			Calendar currentTime = Calendar.getInstance();
    			currentTime.add(Calendar.MINUTE, - COOKIE_DURATION);
    			if(cookie.getCreationTime().after(currentTime)) 
    				cookie.reset();
    		}
    	}
    }

}