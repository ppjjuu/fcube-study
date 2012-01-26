package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.util.StringUtils;

public class ConnectDb {
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
	
	public Connection getConnection(String db) throws SQLException{
		String dbUrl = null;
		String id = null;
		String passwd = null;
		
		db = db.toLowerCase();
		
		if(db.equals("his")){
			dbUrl = "jdbc:oracle:thin:@192.168.100.224:1521:his";
			id = "medi2005";
			passwd = "medi123";
		}else if(db.equals("mis030dv")){
			dbUrl = "jdbc:oracle:thin:@192.168.100.186:1521:mis030";
			id = "mis";
			passwd = "mis";
		}else if(db.equals("mis030ed")){
			dbUrl = "jdbc:oracle:thin:@172.18.10.77:1521:mis030";
			id = "misu01";
			passwd = "misu01";
		}else if(db.equals("mis030db")){
			dbUrl = "jdbc:oracle:thin:@192.168.100.176:1521:mis030";
			id = "devadmin";
			passwd = "adminmis";
		}
		
		return dbUrl == null ? null : DriverManager.getConnection(dbUrl, id, passwd);
	}
	
	public String coding(String input) throws Exception{
		return input == null ? "" : new String(input.getBytes("ISO-8859-1"),"EUC-KR");
	}
	
	public void exe(String[] args){
		Connection con = null;
		Connection con2 = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = getConnection("mis030ed");
			con2 = getConnection("mis030dv");
		
			stmt = con.createStatement();
			rs = stmt.executeQuery("     select * from emr.mmodmrsm@his031db@dblu01 " +
					"       where instcd = '031' " +
					"         and genrdd = '20120120'" +
					"         and genrno < 100 ");
			
			rsmd = rs.getMetaData();
			
			//pstmt = con2.prepareStatement("insert into mis.map_pur0101 (SYS_DATE,USER_ID,UPD_DATE,PUM_CODE,JAESAENG_GUBUN     ,AVG_SOYO_QTY       ,FIX_JAEGO_QTY      ,REED_TIME          ,HYUN_JAEGO_QTY     ,HYUN_JAEGO_KEUM    ,PUM_HNAME          ,PUM_ENAME          ,DG_KYEYAK_YN       ,ACCT_ID            ,GU_DANUI           ,BUL_DANUI          ,KYUKYEOK           ,BOX_SU             ,DANGA1             ,PUM_GUBUN          ,SG_CODE            ,CUST_CODE          ,JEJO_COM           ,LAST_IBGO_DATE     ,LAST_CHULGO_DATE   ,DEL_GUBUN          ,DEL_DATE           ,IBCHAL_DANGA       ,JY_DATE            ,PUMJUL_GUBUN       ,PUMJUL_DATE        ,FIRST_INDATE       ,BOGAN_GUBUN        ,SUGA_GUBUN         ,KANRI_GUBUN        ,SG_DANUI           ,SG_HWANSAN_QTY     ,BUL_HWANSAN_QTY    ,KB_PUM_CODE        ,NEW_PUM_CODE       ,CHAKIM             ,GIGAN1             ,GIGAN2             ,DG_KYEYAK_DATE     ,JEJO_COM2          ,JEJO_COM3          ,NABPUM_PLACE       ,NABPUM_PLACE2      ,NABPUM_PLACE3      ,PUM_USER           ,CHANGO_GUBUN       ,AUTO_YN            ,BUNRYU1            ,BUNRYU2            ,BUNRYU3            ,BUNRYU4            ,PUMJONG            ,PUMMYUNG           ,ACCT_BUNRYU        ,FIX_SER            ,JANGBI_GUBUN       ,BIGO               ,SETPKG_YN          ,DRG_SINGO          ,YONGDO             ,USE_BUSEO          )values(?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  )");
			
						
			/*int count = 0;
			while(rs.next()){
				count++;
				for(int i=1;i<=66;i++){
//					System.out.println(i + " : " + rsmd.getColumnClassName(i) + " / " + rsmd.getColumnTypeName(i) + " / " + rsmd.getColumnType(i));
					
					switch(rsmd.getColumnType(i)){
					case 93:
						pstmt.setDate(i,rs.getDate(i));
						break;
					case 12:
						pstmt.setString(i,coding(rs.getString(i)));
						break;
					case 2:
						pstmt.setBigDecimal(i, rs.getBigDecimal(i));
					}
				}
				pstmt.executeUpdate();
				if(count % 100 == 0) System.out.println("Copy Row : " + count);
			}
			System.out.println("Commit Copy Rows : " + count);
			con2.commit();*/
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(rs != null)rs.close();
				if(stmt != null)stmt.close();
				if(con != null)con.close();
				if(con2 != null)con2.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new ConnectDb().exe(null);
	}
}
