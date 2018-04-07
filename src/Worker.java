import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
/*
public class Worker implements Runnable{


	private Socket socket;
	private int number;
	
	BufferedWriter out;
	BufferedReader in;

	HTMLPage html;

	public Worker(int number, Socket s) {
		this.number = number;
		this.socket = s;
		System.out.println("Do things number "+this.number);
		
		try {
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void run() {
		
		String request = "";
		
		try {			
			html = new HTMLPage();
			
			boolean img = false;
	        String s;
	        request = in.readLine();
	        System.out.println(request);
	        if(request.contains("png")) {
	        	img = true;
	        }
	        while ((s = in.readLine()) != null) {
	            System.out.println(s);
	            if (s.isEmpty()) {
	                break;
	            }
	        }
	        
	        if(request.contains("request")) {
		        out.write(HTTP.getHeader(HTTP.FileType.HTML, "1+1".length()));
		        out.write("1+1");
	        }
	        else if(img == false) {		        
		        String toSend = html.getHtmlCode();
		        out.write(HTTP.getHeader(FileType.HTML, toSend.length()));
		        out.write(toSend);
	        }
	        else {
	        	//Split the request header (
	        	String[] tokens = request.split("\\s+");
	        	String color = tokens[1].substring(1);
	        	System.out.println(color);
	        	
	            BufferedImage image = ImageIO.read(new File(color));

	            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	            ImageIO.write(image,"png",byteArrayOutputStream);

	            
	            out.write(HTTP.getHeader(FileType.PNG, byteArrayOutputStream.size()));
	            out.flush();
	            ImageIO.write(image,"png",socket.getOutputStream());
	            socket.getOutputStream().flush();
	        }
	
	        System.err.println("End connexion");
	        out.close();
	        in.close();
	        socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	
	

}
*/