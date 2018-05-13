import java.net.Socket;

/**
 * class representing a server thread
 */
public class Worker implements Runnable{


    private Socket socket;
    private int number;

    /**
     * constructor
     *
     * @param number number to identify the worker
     * @param s      a connection socket
     */
    public Worker(int number, Socket s){
        this.number = number;
        this.socket = s;

    }

    @Override
    public void run(){

        System.out.println("Start of thread number " + this.number);

        //Handle the request then close the socket
        try{
            RequestHandler.handleRequest(socket);
            socket.close();
        }
        catch (Exception e){
            System.err.println("Error occured when handling the client connection number " + this.number + " : " + e.getMessage());
        }
        
        System.out.println("End of thread number " + this.number);

    }


}
