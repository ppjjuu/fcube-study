package imageToHexa;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class ImageToHexa {
	public static String byteArrayToHexa(int byteLength, byte[] ba){
		if(ba == null || byteLength == 0){
			return null;
		}
		
		StringBuffer sb = new StringBuffer(byteLength * 2);
		String hexaNumber;
		
		for(int x=0; x<byteLength; x++){
			hexaNumber = "0" + Integer.toHexString(0xff & ba[x]);
			
			sb.append(hexaNumber.substring(hexaNumber.length() - 2));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception{
	/*	File image = new File(new URI("file:///d:/Cover.jpg"));
		FileInputStream fs = new FileInputStream(image);
		byte[] ba = new byte[51200];
		int byteLength = 0;
		byteLength = fs.read(ba);
		
		System.out.println("ByteLength = " + byteLength);
		System.out.println("----Data----" + "\n" + byteArrayToHexa(byteLength, ba));*/
//		String ori = "4534gerge";
//		byte[] ba = ori.getBytes();
		Byte bs = new Byte("d");  
		System.out.println(bs);
	}
}
