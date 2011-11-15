package logViewer;

//--------------------------------------------------------------
//telnet ���α׷� Telnet.java
//
//�� ���α׷��� ������ �ּ��� ��Ʈ�� ǥ�� ��/������� �����Ѵ�.
//���� ��Ʈ�� 23���� ���, ������ ���Ѵ�.
//
//����(1) :  java   Telnet   �����ּ�   ��Ʈ��ȣ
//����(2) :  java   Telnet   �����ּ�
//-------------------------------------------------------------

//���̺귯�� �̿�
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

//Telnet Ŭ����
public class Telnet {
	Socket serverSocket;// ���ӿ� ����
	public OutputStream serverOutput;// ��Ʈ��ũ ��¿� ��Ʈ��
	public BufferedInputStream serverInput;// ��Ʈ��ũ �Է¿� ��Ʈ��
	String host;// ���� ������ �ּ�
	int port; // ���� ������ ��Ʈ ��ȣ

	private String script = null;

	static Logger logger = Logger.getLogger(Telnet.class);

	static final int DEFAULT_TELNET_PORT = 23;// telnet�� ��Ʈ ��ȣ(23)

	// Constructor(1) �ּҿ� ��Ʈ ��ȣ�� ������ ���
	public Telnet(String host, int port) {
		this.host = host;
		this.port = port;
	}

	// Constructor(2) �ּҸ� ������ ���
	public Telnet(String host) {
		this(host, DEFAULT_TELNET_PORT);// telnet ��Ʈ�� ����
	}

	// openConnection �޼ҵ�
	// �ּҿ� ��Ʈ ��ȣ�κ��� ������ ����� ��Ʈ���� �ۼ��Ѵ�.
	public void openConnection() throws IOException, UnknownHostException {
		serverSocket = new Socket(host, port);
		serverOutput = serverSocket.getOutputStream();
		serverInput = new BufferedInputStream(serverSocket.getInputStream());
		// ���� ������ telnet ��Ʈ��ȣ�̹Ƿ� ������ ���Ѵ�.
		if (port == DEFAULT_TELNET_PORT) {
			negotiation(serverInput, serverOutput);
		}
	}

	// main_proc �޼ҵ�
	// ��Ʈ��ũ���� ����� ���ϴ� ������ ���۽�Ų��.
	public void main_proc() throws Exception {

		try {
			// ���Կ� Ŭ���� StreamConnector�� ��ü�� �����Ѵ�.
			StreamConnector stdin_to_socket = new StreamConnector(System.in,
					serverOutput);
			// StreamConnector socket_to_stdout = new
			// StreamConnector(serverInput,
			// System.out);
			LogFileWriter lfw = new LogFileWriter(serverInput, serverOutput);
			// ������ �����Ѵ�.
			Thread input_thread = new Thread(stdin_to_socket);
			// Thread output_thread = new Thread(socket_to_stdout);
			Thread lfw_thread = new Thread(lfw);
			// ������ �����Ѵ�.
			input_thread.start();
			// output_thread.start();
			lfw_thread.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e);
			System.exit(1);
		}
	}

	// ���� ����ϴ� �ڸǵ��� ����
	static final byte IAC = (byte) 255;
	static final byte DONT = (byte) 254;
	static final byte DO = (byte) 253;
	static final byte WONT = (byte) 252;
	static final byte WILL = (byte) 251;

	// negotiation �޼ҵ�
	// NVT�� ���� ����� �����Ѵ�.
	static void negotiation(BufferedInputStream in, OutputStream out)
			throws IOException {
		byte[] buff = new byte[3];// �ڸǵ� ���ſ� �迭
		while (true) {
			in.mark(buff.length);
			if (in.available() >= buff.length) {
				in.read(buff);
				if (buff[0] != IAC) {// ���� ����
					in.reset();
					return;
				} else if (buff[1] == DO) {// DO �ڸǵ忡 ���ؼ���
					buff[1] = WONT;// WON'T�� ��ȯ�Ѵ�.
					out.write(buff);
				}
			}
		}
	}

	// main �޼ҵ�
	// TCP ������ ��� ó���� �����Ѵ�.
	public static void main(String[] arg) {
		try {
			Telnet t = null;
			// �μ��� ������ ���� constructor�� �޶�����.
			switch (arg.length) {
			case 1:// ���� �ּҸ��� ����
				t = new Telnet(arg[0]);
				break;
			case 2:// �ּҿ� ��Ʈ�� Ȯ��
				t = new Telnet(arg[0], Integer.parseInt(arg[1]));
				break;
			default:// ������ �ٸ� ���
				System.out
						.println("usage: java Telnet <host name> {<port number>}");

				t = new Telnet("172.18.10.77");
				// return;
			}

			t.openConnection();
			t.main_proc();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

// StreamConnector Ŭ����
// ��Ʈ���� �޾Ƽ� ó���� �� ���ڸ� �����Ͽ� �����͸� �޾Ƽ� �Ѱ��ش�.
// StreamConnector Ŭ������ ������ �����ϱ� ���� Ŭ�����̴�.
class StreamConnector implements Runnable {
	InputStream src = null;
	OutputStream dist = null;

	// Constructor ����� ��Ʈ���� �޾Ƽ� ó���Ѵ�.
	public StreamConnector(InputStream in, OutputStream out) {
		src = in;
		dist = out;
	}

	// ó�� �κ�
	// ��Ʈ�� �а� ���⸦ ������ �ݺ��Ѵ�.
	public void run() {
		byte[] buff = new byte[1024];
		while (true) {
			try {
				int n = src.read(buff);
				if (n > 0)
					dist.write(buff, 0, n);
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e);
				System.exit(1);
			}
		}
	}
}

class LogFileWriter implements Runnable {
	InputStream src = null;
	OutputStream serverOut = null;
	FileOutputStream dist = null;
	PrintStream ps = null;

	File logFile = null;

	// Constructor ����� ��Ʈ���� �޾Ƽ� ó���Ѵ�.
	public LogFileWriter(InputStream in, OutputStream out) throws Exception {
		logFile = new File(new URI("file:///d:/95031.log"));
		src = in;
		serverOut = out;
	}

	public void run() {
		byte[] buff = new byte[1024];
		try {
			dist = new FileOutputStream(logFile, true);
			ps = new PrintStream(dist);
			while (true) {
				try {
					int n = src.read(buff);
					if (n > 0) {
						ps.write(buff, 0, n);
						
						String str = new String(buff).trim();
						
						if (str.lastIndexOf("ogin:") == 1) {
							System.out.println("Login !!");
							serverOut.write("devu01\n".getBytes());
						} else if (str.lastIndexOf("assword:") == 1) {
							System.out.println("Password !!");
							serverOut.write("devu01\n".getBytes());
						} else if (str.lastIndexOf("/data]") > 0) {
							System.out.println("Change Path !!" + "[" + str.substring(str.length()-20)
									+ "]" + str.length() + " "
									+ str.lastIndexOf("/data]"));
							serverOut
									.write("cd logs/jeus6mis/live.logs/users/95031\n"
											.getBytes());
							serverOut
									.write("tail -100f user.log\n".getBytes());
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.err.print(e);
					System.exit(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ps.close();
			try{
				dist.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}