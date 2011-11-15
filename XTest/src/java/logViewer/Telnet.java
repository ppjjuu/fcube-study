package logViewer;

//--------------------------------------------------------------
//telnet 프로그램 Telnet.java
//
//이 프로그램은 설정된 주소의 포트에 표준 입/출력으로 접속한다.
//접속 포트가 23번인 경우, 협상을 행한다.
//
//사용법(1) :  java   Telnet   서버주소   포트번호
//사용법(2) :  java   Telnet   서버주소
//-------------------------------------------------------------

//라이브러리 이용
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

//Telnet 클래스
public class Telnet {
	Socket serverSocket;// 접속용 소켓
	public OutputStream serverOutput;// 네트워크 출력용 스트림
	public BufferedInputStream serverInput;// 네트워크 입력용 스트림
	String host;// 접속 서버의 주소
	int port; // 접속 서버의 포트 번호

	private String script = null;

	static Logger logger = Logger.getLogger(Telnet.class);

	static final int DEFAULT_TELNET_PORT = 23;// telnet의 포트 번호(23)

	// Constructor(1) 주소와 포트 번호가 지정된 경우
	public Telnet(String host, int port) {
		this.host = host;
		this.port = port;
	}

	// Constructor(2) 주소만 지정된 경우
	public Telnet(String host) {
		this(host, DEFAULT_TELNET_PORT);// telnet 포트를 설정
	}

	// openConnection 메소드
	// 주소와 포트 번호로부터 소켓을 만들고 스트림을 작성한다.
	public void openConnection() throws IOException, UnknownHostException {
		serverSocket = new Socket(host, port);
		serverOutput = serverSocket.getOutputStream();
		serverInput = new BufferedInputStream(serverSocket.getInputStream());
		// 접속 상대방이 telnet 포트번호이므로 협상을 행한다.
		if (port == DEFAULT_TELNET_PORT) {
			negotiation(serverInput, serverOutput);
		}
	}

	// main_proc 메소드
	// 네트워크와의 통신을 행하는 슬롯을 시작시킨다.
	public void main_proc() throws Exception {

		try {
			// 슬롯용 클래스 StreamConnector의 객체를 생성한다.
			StreamConnector stdin_to_socket = new StreamConnector(System.in,
					serverOutput);
			// StreamConnector socket_to_stdout = new
			// StreamConnector(serverInput,
			// System.out);
			LogFileWriter lfw = new LogFileWriter(serverInput, serverOutput);
			// 슬롯을 생성한다.
			Thread input_thread = new Thread(stdin_to_socket);
			// Thread output_thread = new Thread(socket_to_stdout);
			Thread lfw_thread = new Thread(lfw);
			// 슬롯을 시작한다.
			input_thread.start();
			// output_thread.start();
			lfw_thread.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print(e);
			System.exit(1);
		}
	}

	// 협상에 사용하는 코맨드의 정의
	static final byte IAC = (byte) 255;
	static final byte DONT = (byte) 254;
	static final byte DO = (byte) 253;
	static final byte WONT = (byte) 252;
	static final byte WILL = (byte) 251;

	// negotiation 메소드
	// NVT에 의한 통신을 협상한다.
	static void negotiation(BufferedInputStream in, OutputStream out)
			throws IOException {
		byte[] buff = new byte[3];// 코맨드 수신용 배열
		while (true) {
			in.mark(buff.length);
			if (in.available() >= buff.length) {
				in.read(buff);
				if (buff[0] != IAC) {// 협상 종료
					in.reset();
					return;
				} else if (buff[1] == DO) {// DO 코맨드에 대해서는
					buff[1] = WONT;// WON'T는 반환한다.
					out.write(buff);
				}
			}
		}
	}

	// main 메소드
	// TCP 연결을 열어서 처리를 개시한다.
	public static void main(String[] arg) {
		try {
			Telnet t = null;
			// 인수의 개수에 의해 constructor가 달라진다.
			switch (arg.length) {
			case 1:// 서버 주소만을 정의
				t = new Telnet(arg[0]);
				break;
			case 2:// 주소와 포트를 확정
				t = new Telnet(arg[0], Integer.parseInt(arg[1]));
				break;
			default:// 사용법이 다른 경우
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

// StreamConnector 클래스
// 스트림을 받아서 처리한 후 양자를 통합하여 데이터를 받아서 넘겨준다.
// StreamConnector 클래스는 슬롯을 구성하기 위한 클래스이다.
class StreamConnector implements Runnable {
	InputStream src = null;
	OutputStream dist = null;

	// Constructor 입출력 스트림을 받아서 처리한다.
	public StreamConnector(InputStream in, OutputStream out) {
		src = in;
		dist = out;
	}

	// 처리 부분
	// 스트림 읽고 쓰기를 무한히 반복한다.
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

	// Constructor 입출력 스트림을 받아서 처리한다.
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