import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HTTP {
	private static final float HTTP_VERSION = 1.1f;
	public enum FileType {
			  HTML("text/html"),
			  PNG("image/png");
		
			public String content_type;
			private FileType(String s) {
				this.content_type  = s;
			}
		}
	
	//Return a HTTP header for a given type and sive of message
	static String getHeader(FileType type, int msgSize) {
		String s = "";
		
        s+= "HTTP/"+HTTP_VERSION+" 200 OK\r\n";
        s+="Date: "+getServerTime()+"\r\n";
        s+="Content-Type: "+type.content_type+"\r\n";
        s+="Content-Length: "+ msgSize +"\r\n";
        s+="\r\n";
        
        return s;
	}
	
	//Return the time in the HTTP format
	private static String getServerTime() {
	    Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
	        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    return dateFormat.format(calendar.getTime());
	}

}