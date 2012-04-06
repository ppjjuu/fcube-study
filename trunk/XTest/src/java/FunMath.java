import java.util.Map;

public class FunMath {
	static int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	static String[] b = { "+", "-", "*" };
	
	public static String next(String sic, int depth, int cnt) {
		if (depth == 9)
			return sic + "end";
		return a[depth] + b[cnt%3] + next(sic, depth + 1, (int) (cnt / 3));
	}

	public static void main(String[] args) {

//		double result = 3d;
//
//		for(int i=0; i<19683;i++){
////		for (int i = 0; i < 100; i++) {
//			System.out.println(next(new String(), 0, (int) (i / 3)).substring(0, 17));
//		}
		
		int hour,min,sec;
		int lenH, lenM, lenS;
		
		for(int i = 0; i <= 12*60*60; i++){
			hour = i / 3600;
			min = i / 60 % 60;
			sec = i % 60;
			lenH = i%(12*60*60);
			lenM = (i%60)*60;
			lenS = (i%60)*60*60;
			if(lenH == lenM && lenS == lenH)System.out.println(hour + ":" + min + ":" + sec);
		}
	}
}
