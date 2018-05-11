import java.util.ArrayList;
import java.util.Calendar;

public class cookieCleaner extends HTTP implements Runnable
{
    private ArrayList<Cookie> cookies;

    public cookieCleaner(ArrayList<Cookie> cookies)
    {
        this.cookies = cookies;
    }

    @Override
    public void run()
    {
        while(true)
        {
            int currentIndex = 0;

            for (int i = 0; i < cookies.size(); i++)
            {
                Cookie cookie = cookies.get(currentIndex);
                synchronized(cookie)
                {
                    Calendar currentTime = Calendar.getInstance();
                    currentTime.add(Calendar.MINUTE, -WebServer.getCookieDuration());
                    if (cookie.getCreationTime().before(currentTime))
                        cookies.remove(cookie);
                    else
                        currentIndex++;
                }
            }
            try
            {
                Thread.sleep(600000); // 600 000 ms = 10 minutes
            }
            catch(InterruptedException e)
            {
                //as no other threads are designed to interrupt this one, it will never happen
            }
        }
    }
}
