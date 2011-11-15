package logViewer;

public class CheckEncoding {

	public static void main(String args[]) throws Exception {

		try {
			// 임으로 깨진 문자를 가만해서 넣은 것이며 글자가 깨졌을경우 특수키로
			// 문자열 지정이 안될 수 있으니 파라미터로 처리 하는것이 좋을듯 합니다.
			String chkStr = "     --+ ?迷源廢?";

			String[] arrayOfString = null;
			arrayOfString = new String[] { "KS_C_5601-1987", "EUC-KR",
//					"ISO_8859-1", "ISO_8859-2", "ISO-10646-UCS-2", "IBM037",
//					"IBM273", "IBM277", "IBM278", "IBM280", "IBM284", "IBM285",
					"IBM297", "IBM420", "IBM424", "IBM437", "IBM500", "IBM775",
//					"IBM850", "IBM852", "IBM855", "IBM857", "IBM860", "IBM861",
//					"IBM862", "IBM863", "IBM864", "IBM865", "IBM866", "IBM868",
//					"IBM869", "IBM870", "IBM871", "IBM918", "IBM1026",
					"Big5-HKSCS", /*"UNICODE-1-1",*/ "UTF-16BE", "UTF-16LE",
					"UTF-16", "UTF-8", "ISO-8859-13", "ISO-8859-15", "GBK",
//					"GB18030", "JIS_Encoding", "Shift_JIS", "Big5", "TIS-620",
//					"us-ascii", "iso-8859-1", "iso-8859-2", "iso-8859-3",
//					"iso-8859-4", "iso-8859-5", "iso-8859-6", "iso-8859-7",
					"iso-8859-8", "iso-8859-9", "koi8-r", "euc-cn", "euc-tw",
					"big5", "euc-jp", "shift_jis", "euc-kr" };

			for (int i = 0; i < arrayOfString.length; ++i) {
				for (int j = 0; j < arrayOfString.length; ++j) {
					if (i == j)
						continue;
					String str = new String(chkStr.getBytes(arrayOfString[i]),
							arrayOfString[j]);

					System.out.print(arrayOfString[i]);
					System.out.print(" -> ");
					System.out.print(arrayOfString[j]);
					System.out.print(" : ");
					System.out.println(str);
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}
