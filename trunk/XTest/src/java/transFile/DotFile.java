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
	private int splitNumber;
	private boolean lastFile;
	
	public DotFile(int splitNumber, byte[] dataArray, int dataSize, boolean isLast) throws Exception{
		this.dataArray = new byte[SPLIT_SIZE];
		this.dataArray = dataArray.clone();
		this.splitNumber = splitNumber;
		this.dataSize = dataSize;
		this.hashCode = setHashCode();
		this.lastFile = isLast;
	}
	
	private String setHashCode() throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(dataArray,0,dataSize);
//		return HashUtil.calculateHash(MessageDigest.getInstance("MD5"), bais);
		return HashUtil.calculateHash(new MD4(), bais);
	}
	
	public String getHashCode(){
		return this.hashCode;
	}
	
	public int getSplitNumber(){
		return this.splitNumber;
	}
	
	public int getDataSize(){
		return this.dataSize;
	}
	
	private boolean checkData() throws Exception{
		return hashCode.equals(setHashCode());
	}
	
	public byte[] getDataArray() throws Exception{
		if(checkData()){
			return dataArray;
		}else{
			throw new Exception("Data Broken Error!");
		}
	}
	
	public boolean isLastFile(){
		return lastFile;
	}
}
