package transFile;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.security.MessageDigest;

public class DotFile implements Serializable {

	private static final long serialVersionUID = 3945534175345740258L;
	private static int SPLIT_SIZE = 256 * 1024;
	
	private byte[] dataArray;
	private int dataSize;
	private String hashCode;
	
	public DotFile(){
		dataArray = new byte[SPLIT_SIZE];
		
	}
	
	private String getHashCode() throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(dataArray,0,dataSize);
		return HashUtil.calculateHash(MessageDigest.getInstance("MD5"), bais);
	}
	
	private boolean checkData() throws Exception{
		return hashCode.equals(getHashCode());
	}
}
