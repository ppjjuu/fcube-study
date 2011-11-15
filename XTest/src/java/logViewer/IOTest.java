package logViewer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

public class IOTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String content = "���Ͼ��� ���� ����";
		URI fileUri = new URI("file:///d:/aaa.txt");
		File file = new File(fileUri);
		
		System.out.println(file.length());
		if (file.length() == 0) {
			try {
				FileWriter file_writer = new FileWriter(file);
				BufferedWriter buff_writer = new BufferedWriter(file_writer);
				PrintWriter print_writer = new PrintWriter(buff_writer, true);
				print_writer.println(content);

				if (print_writer.checkError()) {
					System.out.println("print_writer error!!");
				}

				file.createNewFile();

				System.out.println("���� ���� ����~��");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("IO Exceiption!");
				e.printStackTrace();
			}
		} else // �̾��
		{
			BufferedWriter buff_writer = new BufferedWriter(new FileWriter(
					file, true));
			PrintWriter print_writer = new PrintWriter(buff_writer, true);
			print_writer.println(content);

			if (print_writer.checkError()) {
				System.out.println("print_writer error!!");
			}
			System.out.println("�̾�� ����?!��");
		}
	}

}
