import java.io.*;
import java.net.Socket;

public class TestClient {
    public static void main(String args[]) {
        try {
            Socket sock = new Socket("localhost", 8001);

            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();

            byte test = 9;
            out.write(test);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            String connection = "GET / HTTP/1.1\r\n" +
                    "Host: localhost:8001\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Upgrade-Insecure-Request: 1\r\n" +
                    "User-Agent= Mozilla/5.0\r\n" +
                    "Accept: text/html\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: fr-FR\r\n" +
                    "\r\n";

            String getPage = "GET /page.html HTTP/1.1\r\n" +
                    "Host: localhost:8001\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Upgrade-Insecure-Request: 1\r\n" +
                    "User-Agent= Mozilla/5.0\r\n" +
                    "Accept: text/html\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: fr-FR\r\n" +
                    "\r\n";

            String getEmpty = "GET /empty.png HTTP/1.1\r\n" +
                    "Host: localhost:8001\r\n" +
                    "Connection: keep-alive\r\n" +
                    "User-Agent= Mozilla/5.0\r\n" +
                    "Accept: image/wepb, image/apng\r\n" +
                    "Referer: http://localhost:8001/\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: fr-FR\r\n" +
                    "\r\n";

            System.out.println("begin connection test");
            writer.write(connection);
            System.out.println("sent");
            char[] tmpReply = new char[2000];
            System.out.println("allocated");
            reader.read(tmpReply);
            System.out.println("received");
            String reply = new String(tmpReply);
            System.out.println("connection reply:");
            System.out.println(reply);

            System.out.println("begin getPage test");
            writer.write(getPage);
            System.out.println("sent");
            tmpReply = new char[2000];
            reader.read(tmpReply);
            System.out.println("received");
            reply = new String(tmpReply);
            System.out.println("");
            System.out.println("getPage reply:");
            System.out.println(reply);

            System.out.println("begin getEmpty test");
            writer.write(getEmpty);
            System.out.println("sent");
            tmpReply = new char[2000];
            reader.read(tmpReply);
            System.out.println("received");
            reply = new String(tmpReply);
            System.out.println("");
            System.out.println("getEmpty reply:");
            System.out.println(reply);
        }
        catch (Exception e) {
            System.out.println("et merde une exception");
        }
    }
}
