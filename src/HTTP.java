import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * class holding some general information/method about the HTP protocol
 */
public abstract class HTTP {
    protected static final float HTTP_VERSION = 1.1f;

    /**
     * get the current time in the HTTP format
     * @return the current time in the HTTP format
     */
    public static String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Get the server time + the argument (in minute) in in the HTTP format
     * @param offsetInMinutes the offset that we want to add to the current date
     * @return the current time + the offset in the HTTP format
     */
    public static String getServerTime(int offsetInMinutes) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        calendar.add(Calendar.MINUTE, offsetInMinutes);
        return dateFormat.format(calendar.getTime());
    }


    public static float getVersion() {
        return HTTP_VERSION;
    }
}
