public class CharSetFind {
	public static void charsetFind(String s) {
		try {
			String[] charset = { "EUC-KR", "KSC5601", "ISO-8859-1", "UTF-8",
					"ASCII" };
			for (int i = 0; i < charset.length; i++) {
				for (int j = 0; j < charset.length; j++) {
					if (i == j)
						continue;

					System.out.print(charset[i] + "->" + charset[j] + ":");
					System.out.println(new String(s.getBytes(charset[i]),
							charset[j]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		CharSetFind.charsetFind("©ö?©ö¢ç¨ù¡¾");
	}
}
