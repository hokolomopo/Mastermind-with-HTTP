import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;

public class TestServer {

    public static void main(String args[]) {
        try {
            ServerSocket sock = new ServerSocket(8001);
            Socket client = sock.accept();

            /*
            InputStream in = client.getInputStream();
            int test = in.read();
            System.out.println("test = " + test);
            */

            HTMLPage html = new HTMLPage();

            while (true) {
                RequestHandler.handleRequest(client, html);
            }
        }
        catch (Exception e) {
            System.out.println("et merde");
        }
    }
}
