package downLoadFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	int port = 8821;

	void start() {
		Socket s = null;
		try {
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				
				String filePath = "G:\\Server\\";
				File inServer = new File(filePath);
				s = ss.accept();
				System.out.println("socket is already build");
				//form client's String is file name and make sure what file will be downLoad
				//todo check file is not here
				String getFile = getFile(s);
				String fileOnLock = filePath + getFile;
				if(fileIsNotInServer(getFile , inServer)){
					System.out.println("file is not in FILEs ,socket will be close");
					break;
				}
				File fi = new File(fileOnLock);

				System.out.println("fileLength:" + (int) fi.length());
				DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				dis.readByte();
				//for os file system 

				DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileOnLock)));
				DataOutputStream ps = new DataOutputStream(s.getOutputStream());
				
				ps.writeUTF(fi.getName());
				ps.flush();
				ps.writeLong((long) fi.length());
				ps.flush();

				int bufferSize = 8192;
				byte[] buf = new byte[bufferSize];

				while (true) {
					int read = 0;
					if (fis != null) {
						read = fis.read(buf);
					}

					if (read == -1) {
						break;
					}
					ps.write(buf, 0, read);
				}
				ps.flush();
				         
				fis.close();
				s.close();                
				System.out.println("file transproms is complete");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private boolean fileIsNotInServer(String fileOnLock, File inServer) {
		String[] files = inServer.list();
		for(String file: files){
			if(file.equals(fileOnLock)){
				return(false);
			}
		}
		return true;
	}

	private String getFile(Socket ss) throws Exception {
		BufferedReader fromClient = new BufferedReader(new InputStreamReader(ss.getInputStream()));
		String inStr = fromClient.readLine();
		
		
		return inStr;
	}

	public static void main(String arg[]) {
		new Server().start();
	}
}