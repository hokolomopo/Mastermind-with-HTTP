import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer
{

    private static final int PORT = 8001;
    private static final int THREAD_POOL_SIZE = 15;

    //Static array storing the cookies presents on the server
    private static ArrayList<Cookie> cookies = new ArrayList<Cookie>();

    //@SuppressWarnings because there is no "clean" way to close the server (you have to CTRL+C/kill the process), so we never close the serverSocket
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        try
        {
            int i = 0;

            //create socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.err.println("Serveur lanced sur le port : " + PORT);
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            //Repeatedly wait for connections, and process
            while (true)
            {
                //accept new request
                Socket clientSocket = serverSocket.accept();

                //use a thread to process the request
                executorService.submit(new Worker(i++, clientSocket));
                System.err.println("New client connected");

            }
        }
        catch (IOException e)
        {
            System.err.println("Socket error, ending the program" + e.getMessage());
        }

    }

    /**
     * Return the cookie with the id given in argument, or null if it does not exist
     *
     * @param id the id of the cookie
     * @return the cookie with the id given in argument
     */
    public static Cookie getCookie(String id)
    {
        for (Cookie c : cookies)
        {
            if (c.getId().equals(id))
            {
                return c;
            }
        }
        return null;
    }

    /**
     * Add a new cookie to the server
     *
     * @param cookie the cookie to be added
     */
    public static void addCookie(Cookie cookie)
    {
        cookies.add(cookie);
    }


}
