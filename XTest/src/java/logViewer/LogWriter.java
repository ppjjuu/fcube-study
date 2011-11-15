package logViewer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class LogWriter {
	private PrintWriter log;
	String Fname = "/tmp/debug.log";

	public LogWriter() {

		try {
			log = new PrintWriter(new FileWriter("/tmp/debug.log", true), true);
		} catch (IOException e) {
			System.err
					.println("Can't open the log file: /sig/htdocs/logs/debug.log");
			log = new PrintWriter(System.err);
		}
	}

	public LogWriter(String logfile) {
		Fname = logfile;
		/*
		 * try { log = new PrintWriter(new FileWriter(logfile, true), true); }
		 * catch (IOException e) {
		 * System.err.println("Can't open the log file: " + logfile); log = new
		 * PrintWriter(System.err); }
		 */
	}

	public LogWriter(String logfile, String msg) {
		try {
			FileWriter out = new FileWriter(logfile, true);
			log = new PrintWriter(out, true);
			log.println(System.currentTimeMillis() + ": " + msg);
			out.close();
			log.close();
		} catch (IOException e) {
			System.err.println("Can't open the log file: " + logfile);
			// log = new PrintWriter(System.err);
		}
	}

	public void log(String msg) {
		try {
			FileWriter out = new FileWriter(Fname, true);
			log = new PrintWriter(out, true);
			log.println(System.currentTimeMillis() + ": " + msg);
			out.close();
			log.close();
		} catch (IOException e) {
			System.err.println("Can't open the log file: " + Fname);
			// log = new PrintWriter(System.err);
		}
	}

	/*
	 * public void log(String msg) { //log.println(new Date() + ": " +
	 * Hanguel.getStringKSC5601(msg));
	 * log.println(Util.getNowTime("yyyyMMdd HH:mm:ss") + ": " + msg); }
	 */
	public void log(Throwable e, String msg) {
		// log.println(new Date() + ": " + Hanguel.getStringKSC5601(msg));
		log.println(new Date() + ": " + msg);
		e.printStackTrace(log);
	}
}
