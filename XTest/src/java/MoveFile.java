import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Date;

public class MoveFile {
	public MoveFile() {

	}

	public static void main(String args[]) {
		String path = "/home/fcube-test";
//		String path = "/media/sf_VM_Share";

		new MoveFile().fileList(new File(path));
		System.out.println(new File(path).getTotalSpace()/1024/1024/1024);
		System.out.println(new File(path).getUsableSpace()/1024/1024/1024);
		System.out.println("end program!!");
	}
	
	public void fileList(File directory) {
		File[] fileList = directory.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if(pathname.isDirectory()) return true;
				return pathname.getName().endsWith(".txt");
			}
		});

		if(fileList != null && fileList.length != 0){
			for (File tempFile : fileList) {
				if (tempFile.isFile()) {
					String tempFileName = tempFile.getAbsolutePath();
					System.out.println("FileName=" + tempFileName);
					System.out.println(new Date(tempFile.lastModified()).toString());
					if(tempFile.renameTo(new File("/home/fcube-test/" + tempFile.getName()))) {
						System.out.println("성공");
					} else {
						System.out.println("실패");
					}
				} else {
					String tempPath = tempFile.getAbsolutePath();
					System.out.println("Path=" + tempPath);
					fileList(tempFile);
				}
			}
		}
	}
}
