package logViewer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogReader {

	public static void main(String[] args) throws Exception{
		Runtime run = Runtime.getRuntime();
		Process p = run.exec("cmd");

		try {
			LogStream ls = new LogStream(p);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
			
			bw.write("ping");
			bw.write(13);
			bw.newLine();
			bw.flush();

//			Thread t = new Thread(ls);
//			t.start();
			
			do{
				System.out.println(br.readLine());
			}while(br.ready());
			
			System.out.println("---ee-e-e-e");
			
//			ls.setExecute(false);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				p.waitFor();
			}finally{
				p.getInputStream().close();
				p.getOutputStream().close();
				p.getErrorStream().close();
				p.destroy();
			}
		}

	}
}
