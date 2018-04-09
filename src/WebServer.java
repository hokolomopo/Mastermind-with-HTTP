import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private static final int port = 8001;
    private static ArrayList<Cookie> cookies;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        int i = 0;

        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Serveur lanced sur le port : " + port);
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // repeatedly wait for connections, and process
        while (true) {

            Socket clientSocket = serverSocket.accept();

            executorService.submit(new Worker(i++, clientSocket));
            System.err.println("New client connected");


        }

    }
    
    public static Cookie getCookie(String id) {
    	for(Cookie c : cookies)
    		if(c.getId().compareTo(id) == 0)
    			return c;
    	return null;
    }
    
    public static void addCookie(Cookie cookie) {
    	cookies.add(cookie);
    }
    

}
