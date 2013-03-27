package Four;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Client4 {

	@SuppressWarnings("resource")
	public static void main(String[] args){
		try{
			Socket s = new Socket("127.0.0.1",8080);
			// 用于获取服务端传输来的信息
			BufferedReader buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// 用于获取客户端准备响应的信息
			InputStreamReader input = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(input); 
			// 发送客户端准备传输的信息	
			PrintWriter printWriter = new PrintWriter(s.getOutputStream(),true);
	    	//printWriter.println("有客户端请求");
	    	
	    	
	    	new ClientOutNet(printWriter,bufferedReader);
	    	String fileName = bufferedReader.readLine();
	    	
			
			    FileWriter fw;
			    fw = new FileWriter("G:\\Client\\"+fileName);
			    String strIn = buff.readLine();
			    while(strIn!=null){
			    fw.write(strIn);
			    
			    }
			    fw.flush();
			    fw.close();
			    Thread.sleep(100);
		
			   
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}


/**
 * Client 发送线程 ，向服务端发送信息
 * 
 */
class ClientOutNet extends Thread{
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	
	ClientOutNet(PrintWriter pw,BufferedReader in){
		this.printWriter = pw;
		this.bufferedReader=in;
		start();
	 }
	
	
	public void run(){
		
			try{
				String inStr = bufferedReader.readLine();
				if(inStr.equals("exit")){
					printWriter.close();
					bufferedReader.close();
					
				}else {
					printWriter.println(inStr);
				}
				sleep(300);
			}catch(Exception e){
				printWriter = null;
				bufferedReader = null;
				throw new RuntimeException(e);
			}
		}
	 }

