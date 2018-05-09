import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * class representing a server thread
 */
public class Worker implements Runnable
{


    private Socket socket;
    private int number;

    BufferedWriter out;
    BufferedReader in;

    HTMLPage html;

    /**
     * constructor
     *
     * @param number number to indentify the worker
     * @param s      a connection socket
     */
    public Worker(int number, Socket s)
    {
        this.number = number;
        this.socket = s;

        System.out.println("Do things number " + this.number);
    }

    @Override
    public void run()
    {

        //Handle the request then close the socket
        try
        {
            RequestHandler.handleRequest(socket);
            socket.close();
        }
        catch (Exception e)
        {
            System.err.println("Error occured when handling the client connection number " + this.number + " : " + e.getMessage());
        }
    }


}
