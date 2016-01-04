import java.io.File;


public class FileFinder {
	
	public long fileList(File directory) {
		
		File[] fileList = directory.listFiles();
		long sumSize = 0l;
		long size = 0;
		
		if(fileList != null && fileList.length != 0){
			for (File tempFile : fileList) {
				
				String tempFileName = tempFile.getAbsolutePath();
				
				if (tempFile.isFile()) {
					sumSize += tempFile.length();
					
				} else {
					size = fileList(tempFile);
					sumSize += size;
					if(size < 10l * 1024 * 1024) {
						System.out.println("Path = " + tempFileName + " (" + viewSize(size) + ")");
//						deleteDir(tempFile);
//						if(size == 0l) deleteDir(tempFile); 
					}
				}
			}
		}
		
		return sumSize;
	}
	
	public void deleteDir(File directory) {
		
		if(!directory.canWrite()) {
			System.out.println("Can't Delete!!");
			return;
		}
		
		File[] fileList = directory.listFiles();
		
		if(fileList != null && fileList.length != 0){
			for (File tempFile : fileList) {
				
				try {
					
					if(tempFile.isFile()) {
						tempFile.delete();
					} else {
						deleteDir(tempFile);
					}
					 
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			directory.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Delete!!");
	}
	
	public String viewSize(long size) {
		String outputSizeString = "";
		
		
		if(size < 1024) {
			outputSizeString = size + " Byte";
		} else if(size/1024 < 1024) {
			outputSizeString = Math.round(size/1024d) + " KByte";
		} else if(size/1024/1024 < 1024) {
			outputSizeString = Math.round(size/1024d/1024d) + " MByte";
		} else {
			outputSizeString = Math.round(size/1024d/1024d/1024d) + " GByte";
		}
		
		return outputSizeString;
	}
	
	public static void main(String[] args) {
//		new FileFinder().fileList(new File("D:\\Test"));
		new FileFinder().fileList(new File("\\\\server.cubenet\\shares\\Disk3\\.hidden"));
		System.out.println("¿Ï·á!!");
	}
	
}
