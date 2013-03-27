package One;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Client1 {  
    static Socket server;  
  
    public static void main(String[] args) throws Exception {  
        server = new Socket("127.000.000.001", 8081);  
        BufferedReader in = new BufferedReader(new InputStreamReader(  
                server.getInputStream()));  
        PrintWriter out = new PrintWriter(server.getOutputStream());  
        BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
       
            Date str = new Date();  
            out.println(str);  
            out.flush();  
              
            System.out.println(in.readLine());   
            server.close();  
    }  
}  
