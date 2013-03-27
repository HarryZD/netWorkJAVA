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
			// ���ڻ�ȡ����˴���������Ϣ
			BufferedReader buff = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// ���ڻ�ȡ�ͻ���׼����Ӧ����Ϣ
			InputStreamReader input = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(input); 
			// ���Ϳͻ���׼���������Ϣ	
			PrintWriter printWriter = new PrintWriter(s.getOutputStream(),true);
	    	//printWriter.println("�пͻ�������");
	    	
	    	
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
 * Client �����߳� �������˷�����Ϣ
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

