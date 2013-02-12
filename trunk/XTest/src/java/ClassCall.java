import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ClassCall {

	public static void main(String[] args) throws Exception {

//		File dir = new File(new URI("file:///d:/"));
		File dir = new File(new URI("file:///home/"));

		ClassCall.printFileList(System.out, dir, 0);
	}

	public static void printFileList(PrintStream print, File file, int depth) {
		Map<String, File> dirList = new TreeMap<String, File>();
		Map<String, File> fileList = new TreeMap<String, File>();

		File[] tempList;
		for (int i = 0; i < depth; i++)
			print.print("\t");
		if (file.getParent() == null)
			print.println("[" + file.getPath() + "]");
		else
			print
					.println("["
							+ file.getPath().substring(
									file.getParent().length()) + "]");
		tempList = file.listFiles();
		if (depth > 2)
			return;

		if (tempList == null)
			return;
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				dirList.put(tempList[i].getPath().substring(
						tempList[i].getParent().length()).toUpperCase(),
						tempList[i]);
			} else {
				fileList.put(tempList[i].getName().toUpperCase(), tempList[i]);
			}
		}
		Iterator<File> dirIterator = dirList.values().iterator();
		while (dirIterator.hasNext()) {
			printFileList(print, dirIterator.next(), depth + 1);
		}
		Iterator<File> fileIterator = fileList.values().iterator();
		while (fileIterator.hasNext()) {
			for (int i = 0; i < depth; i++)
				print.print("\t");
			print.println(fileIterator.next().getName());
		}
	}
}
