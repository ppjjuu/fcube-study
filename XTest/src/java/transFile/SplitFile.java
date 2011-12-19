package transFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.security.MessageDigest;

public class SplitFile {
	final static int BUFF_SIZE = 256 * 1024;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		long lapTime = startTime;
		long endTime;

		MessageDigest md5 = MessageDigest.getInstance("MD5");

		File targetFile = new File(new URI("file:///d:/XTest/test.exe"));

		File writeFile = new File(targetFile.getAbsolutePath() + ".df");
		FileOutputStream fos = new FileOutputStream(writeFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		FileInputStream fis = new FileInputStream(targetFile);
		byte[] temp = new byte[BUFF_SIZE];

		for (int i = 0; i * temp.length < targetFile.length(); i++) {
			int readByte = fis.read(temp);
			DotFile df = new DotFile(i, temp, readByte,
					!((i + 1) * temp.length < targetFile.length()));

			oos.writeObject(df);
			System.out.println("Write : [" + i + "]" + df.getHashCode());
		}
		oos.close();
		fos.close();

		endTime = System.currentTimeMillis();
		System.out.println(endTime - lapTime + "ms");
		lapTime = System.currentTimeMillis();

		File copyFile = new File(targetFile.getPath() + ".clon");
		FileOutputStream fos2 = new FileOutputStream(copyFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos2);

		int i = 0;
		File readFile = new File(targetFile.getAbsolutePath() + ".df");
		FileInputStream fis2 = new FileInputStream(readFile);
		ObjectInputStream ois = new ObjectInputStream(fis2);

		if (readFile.canRead()) {
			do {
				DotFile df = (DotFile) ois.readObject();

				bos.write(df.getDataArray(), 0, df.getDataSize());
				System.out.println("Write : [" + i + "]" + df.getHashCode());
				i += 1;
				if (df.isLastFile())
					break;
			} while (true);

		}
		ois.close();
		fis2.close();

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
