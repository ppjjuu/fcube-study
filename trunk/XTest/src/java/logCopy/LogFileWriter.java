package logCopy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;

public class LogFileWriter implements Runnable {
	private InputStream input = null;
	private OutputStream output = null;
	private FileOutputStream dist = null;
	private PrintStream ps = null;
	private File logFile = null;

	// Constructor 입출력 스트림을 받아서 처리한다.
	public LogFileWriter(InputStream input, OutputStream output, File logFile) throws Exception {
		this.logFile = logFile;
		this.input = input;
		this.output = output;
	}

	public void run() {
		byte[] buff = new byte[1024];
		while (true) {
			try {
				dist = new FileOutputStream(logFile, true);
				ps = new PrintStream(dist);
				int n = input.read(buff);
				if (n > 0) {
					ps.write(buff, 0, n);
					ps.flush();

					String str = new String(buff).trim();
					;
					if (str.lastIndexOf("ogin:") == 1) {
						System.out
								.println("Login !!" + "[" + str + "]"
										+ str.length() + " "
										+ str.lastIndexOf("ogin:"));
						output.write("devu01\n".getBytes());
					} else if (str.lastIndexOf("assword:") == 1) {
						System.out.println("Password !!");
						output.write("devu01\n".getBytes());
					} else if (str.lastIndexOf("/data]") > 0) {
						System.out.println("Change Path !!" + "[" + str + "]"
								+ str.length() + " "
								+ str.lastIndexOf("/data]"));
						output
								.write("cd logs/jeus6mis/live.logs/users/95031\n"
										.getBytes());
						output.write("tail -1000f user.log\n".getBytes());
					}
				}
				ps.close();
				dist.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.print(e);
				System.exit(1);
			}
		}
	}
}