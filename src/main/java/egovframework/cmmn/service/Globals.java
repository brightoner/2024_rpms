package egovframework.cmmn.service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * <pre>
 *  시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 * 
 * </pre>
 * @FileName : Globals.java
 * @package  : egovframework.cmmn.service
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
public class Globals {   
	public static final  ResourceBundle BUNDLE = ResourceBundle.getBundle("eds");
    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "tempPath";
	
	public static final String RISS_LIST_URL = "";
	//EDS LIST URL
	public static final String LIST_URL = BUNDLE.getString("LIST_URL");
	//EDS DTL URL
	public static  final String DTL_URL = BUNDLE.getString("DTL_URL");
	//EDS MAIL URL
	public static final String MAIL_URL = BUNDLE.getString("MAIL_URL");
	//EDS DTL URL
	public static final String TAS_SMS_URL = BUNDLE.getString("TAS_SMS_URL");
	//EDS DTL URL
	public static final String TAS_MAIL_URL = BUNDLE.getString("TAS_MAIL_URL");
	//EDS DTL URL
	public static final String SEARCH_HOST = BUNDLE.getString("EARCH_HOST");
	//EDS MAIL URL
	public static final int SEARCH_PORT = Integer.parseInt(BUNDLE.getString("EARCH_PORT"));
	
	public static List<String> CONFIM_IP= new ArrayList<String>();
	public static List<String> MENU_IP= new ArrayList<String>();
	
   static{

	   String ip_config = BUNDLE.getString("IP_LIST");
	   
	   String[] ip_list =  ip_config.split(",");
	   
	   for (String ip : ip_list) {
		   CONFIM_IP.add(ip); 
	   }
	   
   }
}
