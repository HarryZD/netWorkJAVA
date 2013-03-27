package One;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server1 {
	    public static void main(String[] args) throws IOException {  
	        ServerSocket server = new ServerSocket(8081);  
	        Socket fromClient = server.accept();  
	        BufferedReader in = new BufferedReader(new InputStreamReader(  
	                fromClient.getInputStream()));  
	        PrintWriter out = new PrintWriter(fromClient.getOutputStream());  
	            String str = in.readLine();  
	            System.out.println(str +"from client");  
	            out.println(new Date() +"from server");  
	            out.flush();  
	            fromClient.close();  
	    }  
	}  
