package logViewer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LogStream implements Runnable{
	private InputStreamReader isr;;
	private BufferedReader br;
	private boolean execute;
	
	public LogStream(Process p){
		isr = new InputStreamReader(p.getInputStream());
		br = new BufferedReader(isr);
	}
	
	public void run(){
		execute = true;
		try{
			System.out.println("--------");
			do{
				System.out.println(br.readLine());
			}while(br.ready() && execute);
			System.out.println("End");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setExecute(boolean stat){
		execute = stat;
	}

}
