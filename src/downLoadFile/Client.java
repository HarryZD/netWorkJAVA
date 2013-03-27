package downLoadFile;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Client {
	private ClientSocket cs = null;

	private String ip = "localhost";// 设置成服务器IP

	private int port = 8821;

	private String sendMessage = "Windows";

	public Client() {
		try {
			if (createConnection()) {
				sendMessage();
				getMessage();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean createConnection() {
		cs = new ClientSocket(ip, port);
		try {
			
			cs.CreateConnection();
			System.out.print("server is online!" + "\n");
			return true;
		} catch (Exception e) {
			System.out.print("server has some problem!" + "\n");
			return false;
		}

	}

	private void sendMessage() {
		if (cs == null)
			return;
		try {
			
			
			cs.sendMessage(sendMessage);
		} catch (Exception e) {
			System.out.print("fa song xiaoxi shibai!" + "\n");
		}
	}

	private void getMessage() {
		if (cs == null)
			return;
		DataInputStream inputStream = null;
		try {
			inputStream = cs.getMessageStream();
		} catch (Exception e) {
			System.out.print("accept news is false\n");
			return;
		}

		try {

			//本地保存路径，文件名会自动从服务器端继承而来。
			String savePath = "G:\\Client\\";
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len=0;

			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
			len = inputStream.readLong();

			System.out.println("file length is :" + len + "\n");
			System.out.println("already accept file!" + "\n");

			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				
				System.out.println(  (passedlen * 100/ len) + "%\n");
				fileOut.write(buf, 0, read);
			}
			System.out.println("complete! file is saved @" + savePath + "\n");

			fileOut.close();
		} catch (Exception e) {
			System.out.println("erros" + "\n");
			return;
		}
	}

	public static void main(String arg[]) {
		new Client();
	}
}