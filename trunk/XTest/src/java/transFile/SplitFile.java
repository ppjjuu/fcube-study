package transFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.security.MessageDigest;

public class SplitFile {
	final static int BUFF_SIZE = 256 * 1024; 
	public static void main(String[] args)throws Exception{
		long startTime = System.currentTimeMillis();
		long lapTime = startTime;
		long endTime;
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		
		File targetFile = new File(new URI("file:///d:/XTest/test.exe"));
		System.out.println(targetFile.isFile());
		System.out.println(targetFile.length());
		System.out.println(HashUtil.calculateHash(md5, targetFile));
		
		FileInputStream fis = new FileInputStream(targetFile);
		byte[] temp = new byte[BUFF_SIZE];
		
		for(int i=0; i*temp.length<targetFile.length(); i++){
			int readByte = fis.read(temp);
			File writeFile = new File(targetFile.getAbsolutePath()+"."+i);
			FileOutputStream fos = new FileOutputStream(writeFile);
			System.out.println("Write : " + writeFile.getName());
			fos.write(temp, 0, readByte);
			fos.close();
		}
		endTime = System.currentTimeMillis();
		System.out.println(endTime - lapTime + "ms");
		lapTime = System.currentTimeMillis();
		File copyFile = new File(targetFile.getPath()+".clon");
		FileOutputStream fos2 = new FileOutputStream(copyFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos2);
		
		int i = 0;
		do{
			File readFile = new File(targetFile.getAbsolutePath()+"."+i);
			if(readFile.canRead()){
				FileInputStream fis2 = new FileInputStream(readFile);
				int readByte = fis2.read(temp);
				bos.write(temp,0,readByte);
				System.out.println(HashUtil.calculateHash(md5, readFile));
			}else{
				break;
			}
			i += 1;
		}while(i!=0);
		endTime = System.currentTimeMillis();
		System.out.println(endTime - lapTime + "ms");
		lapTime = System.currentTimeMillis();
		System.out.println(HashUtil.calculateHash(md5, targetFile));
		System.out.println(HashUtil.calculateHash(md5, copyFile));
		endTime = System.currentTimeMillis();
		System.out.println(endTime - lapTime + "ms");
		System.out.println("Total : " + (endTime - startTime) + "ms");
		
	}
}
