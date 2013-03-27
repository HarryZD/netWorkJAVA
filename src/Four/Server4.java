package Four;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server4 {
	// �Ժ���Ϊ��λ 
	private final static long TIMELIMIT = 1000000000 ;
	private Map<String,String> map = Collections.synchronizedMap(new HashMap<String,String>());
	private FileReader fileReader;
		
	public void ServerSocketNet(){
		int ch =0;
		try{
			 ServerSocket ss = new ServerSocket(8080);
			 Socket s = ss.accept();
			 BufferedReader formClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
			 // ���ڻ�ȡ�����׼����Ӧ����Ϣ
			 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			 // ���ͷ����׼����Ӧ����Ϣ
			 PrintWriter pw = new PrintWriter(s.getOutputStream(),true);			 
			// pw.println("���Ѿ��ɹ���������!");
			 System.out.println("�Ѿ���������");
			 String inStr = formClient.readLine();
				File inServer = new File("G:\\Server");
				File lockInFile  ;
				String[] fileList = inServer.list();
				for(String fileName : fileList){
					if(inStr.equals(fileName)){
						lockInFile = new File("G:\\Server\\"+fileName);
						fileReader = new FileReader(lockInFile);
						while((ch = fileReader.read())!=-1 )
						{
							pw.println((char)ch);
						}
						pw.flush();
						break;
					}
				}
				
			 
//			 new ServerOutNet(pw, formClient);
			// new ServerTimeListener(map, TIMELIMIT);
			 
			 // ��ʾ�ͻ��˵Ĵ���������Ϣ
			 while(true){ 
				 String str = formClient.readLine();
				 if(str.equals("exit")){
					 break;
				 }
			     System.out.println("��Ҫ������ļ���"+str);
				 Thread.sleep(100);
			}
			s.close();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
	 public static void main(String[] args){
		 new Server4().ServerSocketNet();
	 }
}

/**
 * server�����߳� ��ͻ��˷����ļ���Ϣ
 * @author ids-user
 *
 */
//class ServerOutNet extends Thread{
//	private PrintWriter printWriter;
//	private BufferedReader bufferedReader;
//	private FileReader fileReader;
//	
//	ServerOutNet(PrintWriter pw,BufferedReader in){
//		this.printWriter = pw;
//		this.bufferedReader = in;
//		start();
//	 }
//	
//	public void run(){
//		
//			int ch = 0;
//			try{
//				String inStr = bufferedReader.readLine();
//				File inServer = new File("G:\\Server");
//				File lockInFile  ;
//				String[] fileList = inServer.list();
//				for(String fileName : fileList){
//					if(inStr.equals(fileName)){
//						lockInFile = new File("G:\\Server\\"+fileName);
//						fileReader = new FileReader(lockInFile);
//						while((ch = fileReader.read())!=-1 )
//						{
//							printWriter.println((char)ch);
//						}
//						printWriter.flush();
//						break;
//					}
//				}
//				sleep(300);
//			}catch(Exception e){
//				printWriter = null;
//				bufferedReader = null;
//				throw new RuntimeException(e);
//			}
//		}
//	 }
//}

// ʱ������߳��ࡣ
class ServerTimeListener extends Thread{
	
	private long timeLimit;
	Map<String, String> map = new HashMap<String, String>();

	
	ServerTimeListener(Map<String, String> map , long timeLimit){
		this.map = map;
		this.timeLimit = timeLimit;
		start();
	}
	
	
	public void run(){
		while(true){
			try{
				// ѭ��map
				 long currentTime = System.currentTimeMillis();
				 for(String mapKey : map.keySet()){
			    	 long oldTime = Long.parseLong(mapKey.substring(0, mapKey.indexOf("_")));
			    	 System.out.println("currentTime - oldTime=" + (currentTime - oldTime));
			    	 if(currentTime - oldTime >= timeLimit){
			    		 map.remove(mapKey);
			    	 }
			     }
			     
				TimeUnit.MILLISECONDS.sleep(timeLimit);
				
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	 }
}
