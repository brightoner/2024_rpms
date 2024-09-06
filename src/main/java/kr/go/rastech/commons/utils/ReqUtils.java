package kr.go.rastech.commons.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;




public final class ReqUtils  {
	  
	  
	public static Map getParameterMap(HttpServletRequest request) {
		Map map = new HashMap();
        try {
            Map paramerterMap = request.getParameterMap();
            Iterator iter = paramerterMap.keySet().iterator();
            String key = null;            
            String[] value = null;
            while(iter.hasNext()) {
                key = (String)iter.next();
                value = (String []) paramerterMap.get(key);
                if(value.length > 1){
                	String[] reValue = new String[value.length];
                	for(int i=0; i<value.length; i++){
                		String rVal = value[i];
                		if(value[i] == null || value[i].equals("") || value[i].equals("null")){
                    		rVal = "";
                    	}else{
                    		rVal = filter(rVal);
                    	}
                		reValue[i] = rVal;
                	}
                	map.put(key, reValue);
                }else{
                	String rVal = value[0];
                	if(value[0] == null || value[0].equals("") || value[0].equals("null")){
                		rVal = "";
                	}else{
                		rVal = filter(rVal);
                		
                	}
                	map.put(key, rVal);
                }
            }
        } catch(RuntimeException e) {
        	Logger.getLogger(ReqUtils.class).debug("err getParameterMap");
        }
        return map;
	}
	
	public static Map getEmptyResult(String resultColumns) {
		Map map = null;
		if(resultColumns != null && !resultColumns.equals("")){
			map = new HashMap();
			String[] columns = StringUtils.split(resultColumns, ",");
			for(int i = 0;i < columns.length;i++){
				map.put(columns[i], "");
			}
		}	
		return map;
	}
	
	/****************************************************************************************************
	*문자치환
	* @param word 치환할 단어
	* @param str1 찾을 문자
	* @param str2 치환할 문자
	* @return rs 치환한문자
	****************************************************************************************************/
		public static String replceStr(String word, String str1, String str2){
			String txt = getEmptyResult2(word);
			String rs = txt.replaceAll(str1,str2); 
			return rs;
		}
	
	/******************************************************************
	 * 널데이터 체크(String 형식)
	 * @param getVal 데이터값  
	 * @param chgdata 널일때 교체할 값
	 ******************************************************************/
	public static String getEmptyResult2(String getVal, String chgdata) {
		String rVal = getVal;
		if(getVal == null || getVal.equals("") || getVal.equals("null")){
			rVal = chgdata;
		}else{
			rVal = rVal.replaceAll("<", "&lt;");
			rVal = rVal.replaceAll(">", "&gt;");
			//rVal = rVal.replaceAll("&", "&amp;");
		}
		
		return rVal;
	}
	
	/******************************************************************
	 * 널데이터 체크(String 형식)
	 * @param getVal 데이터값  
	 * @param chgdata 널일때 교체할 값
	 * 데이터 값에 대한 기본적인 태그 변경 및 제거 처리
	 ******************************************************************/
	public static String getEmptyResult2(String getVal) {
		String rVal = getVal;
		
		if(getVal == null || getVal.equals("") || getVal.equals("null")){
			rVal = "";
		}else{
			rVal = rVal.replaceAll("<", "&lt;");
			rVal = rVal.replaceAll(">", "&gt;");
			//rVal = rVal.replaceAll("&", "&amp;");
		}
		
		return rVal;
	}
	
	public static String getEmpty(Object getVal) {
		if(getVal == null || getVal.toString().equals("null")){
			return "";
		}
		return getVal.toString();
	}
	/******************************************************************
	 * 변경된 태그를 출력시 HTML 형태로 다시 복구
	 ******************************************************************/
	public static String getRestoreHtml(String getVal) {
		String rVal = getVal;
		if(getVal == null){
			rVal = "";
		}
		//rVal = rVal.replaceAll("eval\\((.*)\\)", "");
		
		rVal = rVal.replaceAll("&lt;","<");
		rVal = rVal.replaceAll("&gt;",">");
		rVal = rVal.replaceAll("&amp;lt;","&lt;");
		rVal = rVal.replaceAll("&amp;gt;","&gt");
		rVal = rVal.replaceAll("&#40;","(");
		rVal = rVal.replaceAll("&#41;",")");
		rVal = rVal.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		rVal = rVal.replaceAll("script", "");		
		

		//rVal = rVal.replaceAll("&amp;","&");
		if(getVal == null || getVal.equals("") || getVal.equals("null")){
			rVal = "";
		}
		
		return rVal;
	}
	
	
	/****************************************************************************************************
	*텍스트문자의 공백과 줄내림표시를 html형식으로 변경
	* @param text 텍스트문자
	* @return String html문자
	****************************************************************************************************/
		public String textToHtml(String text){
			String txt = getEmptyResult2(text);
			return txt.replaceAll(" ","&nbsp;").replaceAll("\n","<br>");
		}

	/****************************************************************************************************
	*html문자 제거
	* @param strVal html문자
	* @return String 텍스트문자
	****************************************************************************************************/
		public static String htmlToText(String strVal) {
	        return strVal.replaceAll("(?:<!.*?(?:--.*?--\\s*)*.*?>)|(?:<(?:[^>'\"]*|\".*?\"|'.*?')+>)","");
	    }
	
	
	/******************************************************************
	 * 원하는 길이만큼 자르기.(String 형식)
	 * @param str 데이터값  
	 * @param num 자를 길이
	 ******************************************************************/
	public static String cutStr(String str, int num) {
		String tmp = ""; 
		if ( str.length() > num ){
			tmp = str.substring( 0, num ) + "...";
		}else{
			tmp = str;
		}
		return tmp;
	}
	
	/******************************************************************
	 * 원하는 길이만큼 자르기. 길이가 초과되면 Span 태그의 Title 속성으로 전체 문자열 보여주기(String 형식)
	 * @param str 데이터값  
	 * @param num 자를 길이
	 ******************************************************************/
	public static String cutStrSpanTitle(String str, int num) {
		String tmp = ""; 
		if ( str.length() > num ){
			tmp = "<span title='" + str + "'>" + str.substring( 0, num ) + "...</span>";
		}else{
			tmp = str;
		}
		return tmp;
	}
		
	/******************************************************************
	 * 널데이터 체크(Map 형식)
	 * @param map 데이터  
	 ******************************************************************/
	public static Map getResultNullChk(Map map) {
		Map rMap = new HashMap();
		if(map == null)
			return null;
		Iterator iter = map.keySet().iterator();
        String key = null;
        String value = null;
        try{
	        while(iter.hasNext()) {
	            key = (String)iter.next();
	            value = ""+map.get(key);
	            
	            if(value!=null&&!"null".equals(value)){	            	
	            	rMap.put(key, value);
	            }else{	 	            	
	            	rMap.put(key, "");	            	
	            }
	        }
        
        }catch(RuntimeException e){
  
        	Logger.getLogger(ReqUtils.class).debug("err getResultNullChk");
        }
			
		return rMap;
	}
	

	/******************************************************************
	 * 현재년도구하기
	 ******************************************************************/
	 public String getCurrYear()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String currYear = formatter.format(new java.util.Date());
		return currYear;
	}


	/******************************************************************
	 * 현재날짜 'yyyy-MM-dd'
	 ******************************************************************/
	public String getToDate() 
	{
		String thisdate = "";
		try
		{
			java.util.Date todate = new java.util.Date();
			java.text.SimpleDateFormat sysdate = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
			thisdate = sysdate.format(todate);
		} catch (RuntimeException e) {
			Logger.getLogger(ReqUtils.class).debug("IGNORED: getToDate");
		}
		return thisdate;
	}
	
	/******************************************************************
	 * 현재날짜 'yyyyMMdd'
	 ******************************************************************/
	public String getToDate2() 
	{
		String thisdate = "";
		try
		{
			java.util.Date todate = new java.util.Date();
			java.text.SimpleDateFormat sysdate = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
			thisdate = sysdate.format(todate);
		} catch (RuntimeException e) {
			Logger.getLogger(ReqUtils.class).debug("IGNORED: getToDate2");
		}
		return thisdate;
	}
	
	/******************************************************************
	 * 현재날짜 'yy/MM/dd'
	 ******************************************************************/
	public String getToDate3() 
	{
		String thisdate = "";
		try
		{
			java.util.Date todate = new java.util.Date();
			java.text.SimpleDateFormat sysdate = new java.text.SimpleDateFormat("yy/MM/dd", java.util.Locale.KOREA);
			thisdate = sysdate.format(todate);
		} catch (RuntimeException e) {
			Logger.getLogger(ReqUtils.class).debug("IGNORED: getToDate3");
		}
		return thisdate;
	}
	
	/******************************************************************
	 * 현재날짜 'yy.MM.dd'
	 ******************************************************************/
	public String getToDate4() 
	{
		String thisdate = "";
		try
		{
			java.util.Date todate = new java.util.Date();
			java.text.SimpleDateFormat sysdate = new java.text.SimpleDateFormat("yyyy.MM.dd", java.util.Locale.KOREA);
			thisdate = sysdate.format(todate);
		} catch (RuntimeException e) {
			Logger.getLogger(ReqUtils.class).debug("IGNORED: getToDate4");
		}
		return thisdate;
	}
	
	/******************************************************************
	 * 현재날짜 'yy년 MM월 dd일'
	 ******************************************************************/
	public String getToDate5() 
	{
		String thisdate = "";
		try
		{
			java.util.Date todate = new java.util.Date();
			java.text.SimpleDateFormat sysdate = new java.text.SimpleDateFormat("yyyy.MM.dd", java.util.Locale.KOREA);
			thisdate = sysdate.format(todate);
			
			thisdate = thisdate.substring(0,4) + "년 " + thisdate.substring(5,7) + "월 " + thisdate.substring(8,10) + "일";
			
		} catch (RuntimeException e) {
			Logger.getLogger(ReqUtils.class).debug("IGNORED: getToDate5");
		}
		return thisdate;
	}
	
	/******************************************************************
	 * 현재날짜에서 iDay이후의 날짜가져오기 'yy년 MM월 dd일'
	 ******************************************************************/
	public String getWantDate(int iDay )
	{
		String retVal = "";
		Calendar temp=Calendar.getInstance();
		StringBuffer sbDate=new StringBuffer();

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		int nYear = temp.get(Calendar.YEAR);
		int nMonth = temp.get(Calendar.MONTH) + 1;
		int nDay = temp.get(Calendar.DAY_OF_MONTH);

		sbDate.append(nYear);
		if(nMonth < 10 ){
			sbDate.append("0");
		}
		sbDate.append(nMonth);
		if(nDay < 10 ){
			sbDate.append("0");
		}
		sbDate.append(nDay);		
		
		retVal = sbDate.toString();
		retVal = retVal.substring(0,4) + "년 " + retVal.substring(4,6) + "월 " + retVal.substring(6,8) + "일";

		return retVal;
	}

	/******************************************************************
	 * 원하는 날짜가 종료된날짜인지 확인
	 ******************************************************************/
	public String getToCheckDate(String sdate, String edate) 
	{
		String retVal = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String ndate = formatter.format(new java.util.Date());
		
		if (sdate == null){ sdate = ""; }
		if (edate == null){ edate = ""; }
		
		if(sdate.length() >= 8 && edate.length() >= 8)
		{
			sdate = sdate.substring(0, 8);
			edate = edate.substring(0, 8);
			ndate = ndate.substring(0, 8);
			
			if (Integer.parseInt(ndate) > Integer.parseInt(edate)){
				retVal = "완료"; 
			}else{
				if(Integer.parseInt(ndate) < Integer.parseInt(sdate)){
					retVal = "진행예정";
				
				}else if(Integer.parseInt(ndate) >= Integer.parseInt(sdate) && Integer.parseInt(ndate) <= Integer.parseInt(edate)){
					retVal = "진행중";					
					
				}else{
					retVal = "";
					
				}
			}
		
		}else{
			retVal = "";
			
		}
		return retVal;
	}
	
	/******************************************************************
	 * 8숫자 날짜형태로 변환
	 * val = 8자리 숫자로 된 문자열
	 * type = 년, 월, 일 구분 기호
	 ******************************************************************/
	public static String getStrToDate(String val, String type) 
	{
		String thisdate = "";
		
		try
		{
			if(val.length() == 8){
				
				thisdate = val.substring(0,4) + type + val.substring(4,6) + type + val.substring(6,8) ;

			}else{
				
				thisdate = val;
			}
		} catch (RuntimeException e) {
			Logger.getLogger(ReqUtils.class).debug("IGNORED: getStrToDate");
		}
		return thisdate;
	}
	
	
	/******************************************************************************************************
	* 해당페이지 네비게이션 바를 만든다.
	* @param total 총갯수
	* @param list_per_limit 한페이당 보여주눈 줄수
	* @param page_per_limit 페이지 묶음 단위
	* @param page 현재페이지
	* @return String 형으로 페이지 네비게이션 바를 그린다.
	*******************************************************************************************************/
		public String paging(int total,int list_per_limit,int page_per_limit,int page){
			
			int total_page_temp=total%list_per_limit==0 ? 0:1;
			int total_page = (total/list_per_limit)+total_page_temp;

			if (page==0){ page = 1;}
			int page_list_temp=page%page_per_limit==0 ? 0:1;
			int page_list =(page/page_per_limit)+page_list_temp-1 ;
			String  navigation="";
			int prev_page=0;
			// 페이지 리스트의 첫번째가 아닌 경우엔 [1]...[prev] 버튼을 생성한다.
			if (page_list>0){
				navigation = "<a href=\"javascript:goPage('1');\" class='urLnkFunction'>["+1+"]</a> ";
				prev_page = page_list * page_per_limit ;
				navigation += "<a href=\"javascript:goPage('"+prev_page+"');\">[◀]</a> ";
			}

			// 페이지 목록 가운데 부분 출력
			int page_end = (page_list + 1) * page_per_limit;
			if (page_end > total_page){ page_end = total_page;}

			for (int setpage = page_list * page_per_limit + 1; setpage <= page_end; setpage++){
				if(setpage==page_end){
					if (setpage == page){
						navigation += "<font color='#0066CC'><strong>"+setpage+"</strong></font> ";
					}else{
						navigation += "<a href=\"javascript:goPage('"+setpage+"')\" class='urLnkFunction'>"+setpage+"</a> ";
					}
				}else{
					if (setpage == page){
						navigation += "<font color='#0066CC'><strong>"+setpage+"</strong></font> ";
					}else{
						navigation += "<a href=\"javascript:goPage('"+setpage+"')\" class='urLnkFunction'>"+setpage+"</a> ";
					}
				}
			}

			// 페이지 목록 맨 끝이 $total_page 보다 작을 경우에만, [next]...[total_page] 버튼을 생성한다.
			if (page_end < total_page){
				int next_page = (page_list + 1) * page_per_limit + 1;
				navigation += "<a href=\"javascript:goPage('"+next_page+"')\">[▶]</a> ";
				navigation += "<a href=\"javascript:goPage('"+total_page+"');\" class='urLnkFunction'>["+total_page+"]</a>";
			}

			return navigation;
		}


	/******************************************************************
	 * 문자열을 UTF-8 변환
	 * @param str 문자열  
	 ******************************************************************/
	public static String getEncode(String str) {
		try{
			return java.net.URLEncoder.encode(str, "UTF-8");
		}catch (RuntimeException e){
			return "";
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	/******************************************************************
	 * 폴더 만들기
	 * @param  
	 ******************************************************************/	
	public static void upFolder(HttpServletRequest request, String path ) {
		String upFolder = request.getSession().getServletContext().getRealPath(path);	

		File upDir = new File(upFolder);
		if (!upDir.exists()) { 
			upDir.setExecutable(false);
			upDir.setReadable(true);
			upDir.setWritable(false);
			//파일디렉토리없으면 만들기
			if(upDir.mkdir()){
		
			
				Logger.getLogger(ReqUtils.class).debug("make ok");
			}else{
			
	
				Logger.getLogger(ReqUtils.class).debug("make fail");
			}
		}
	}
	
    public static void messageAndClose(HttpServletResponse res, 
			String message) throws IOException {
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			message = str2alert(message);
			out.println("<script language='javascript'>");
			out.println("alert('" + message + "');");
			out.println("self.close();");
			out.println("</script>");       
			out.flush();
    }  
    
    public static String str2alert(String s) {
        if (s == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
            if (c[i] == '\n') {
                buf.append("\\n");
            } else if (c[i] == '\t') {
                buf.append("\\t");
            } else if (c[i] == '"') {
                buf.append("'");
            } else {
                buf.append(c[i]);
            }
        }
        return buf.toString();
    }
	
	public static Map getParameterEmptyRequest2Map(HttpServletRequest request) {
		Map map = new HashMap();
        try {
            Map paramerterMap = request.getParameterMap();
            Iterator iter = paramerterMap.keySet().iterator();
            String key = null;
            String[] value = null;
            while(iter.hasNext()) {
                key = (String)iter.next();
                value = (String []) paramerterMap.get(key);
                if(value.length > 1){
                	String[] reValue = new String[value.length];
                	for(int i=0; i<value.length; i++){
                		String rVal = value[i];
                		if(value[i] == null || value[i].equals("") || value[i].equals("null")){
                    		rVal = "";
                    	}else{
                    		rVal = filter(rVal);
                    	}
                		reValue[i] = rVal;
                	}
                	map.put(key, reValue);
                }else{
                	String rVal = value[0];
                	if(value[0] == null || value[0].equals("") || value[0].equals("null")){
                		rVal = "";
                	}else{
                		rVal = filter(rVal);
                		
                	}
                	map.put(key, rVal);
                }
            }
        } catch(RuntimeException e) {

    		
    		Logger.getLogger(ReqUtils.class).debug("err getParameterEmptyRequest2Map");
        }
        return map;
	}
	
	public static Map getParameterRestoreHtmlMap(HttpServletRequest request) {
		Map map = new HashMap();
        try {
            Map paramerterMap = request.getParameterMap();
            Iterator iter = paramerterMap.keySet().iterator();
            String key = null;
            String[] value = null;
            while(iter.hasNext()) {
                key = (String)iter.next();
                value = (String []) paramerterMap.get(key);                
                if(value.length > 1){
                	String[] reValue = new String[value.length];
                	for(int i=0; i<value.length; i++){
                		String rVal = value[i];
                		if(value[i] == null || value[i].equals("") || value[i].equals("null")){
                    		rVal = "";
                    	}else{
                    		rVal = rVal.replaceAll("&lt;","<");
                			rVal = rVal.replaceAll("&gt;",">");
//                			rVal = rVal.replaceAll("&amp;lt;","&lt;");
//                			rVal = rVal.replaceAll("&amp;gt;","&gt");
//                			rVal = rVal.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//                			rVal = rVal.replaceAll("script", "");
                    	}
                		reValue[i] = rVal;
                	}
                	map.put(key, reValue);
                }else{
                	String rVal = value[0];
                	if(value[0] == null || value[0].equals("") || value[0].equals("null")){
                		rVal = "";
                	}else{
                		rVal = rVal.replaceAll("&lt;","<");
            			rVal = rVal.replaceAll("&gt;",">");
//            			rVal = rVal.replaceAll("&amp;lt;","&lt;");
//            			rVal = rVal.replaceAll("&amp;gt;","&gt");
//            			rVal = rVal.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//            			rVal = rVal.replaceAll("script", "");
                	}
                	map.put(key, rVal);
                }               
            }
        } catch(RuntimeException e) {
    		Logger.getLogger(ReqUtils.class).debug("err getParameterRestoreHtmlMap");
        }
        return map;
	}
	
	/******************************************************************
	 * 널데이터 체크(String 형식)
	 * @param getVal 데이터값  
	 * @param chgdata 널일때 교체할 값
	 ******************************************************************/
	public static String getEmptyResult3(Object getVal) {
		if(getVal==null) return "";
		return ""+getVal;
	}

	 /******************************************************************
	  * 'YYYYMMDD' 형태의 String형을 Date형으로 만들어 리턴
	  * @param  
	  ******************************************************************/ 
   public static Date stringToDate( String d ) {

       int year = Integer.parseInt(d.substring(0, 4)); 
       int month = Integer.parseInt(d.substring(4, 6)); 
       int day = Integer.parseInt(d.substring(6));

       Calendar cdate = java.util.Calendar.getInstance(); 
       cdate.set(Calendar.YEAR, year); 
       cdate.set(Calendar.MONTH, month); 
       cdate.set(Calendar.DATE, day); 

       Date ddate = cdate.getTime(); // java.sql.Date 가 아님..  
       return ddate;
   }
   
   /******************************************************************
	  * System의 현재 날짜를 yyyyMMdd형식으로 반환하는 method 
	  * @param  
	  ******************************************************************/ 
  public static String getCurrentDate()
  {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String currDate = formatter.format(new java.util.Date());
		return currDate;        
  }
  
  /****************************************************************************************************
	* 파라미터 맵을 String 배열로 돌려준다.  입력받은 문자는 HTML Encoding 으로 바꿔준다.
	****************************************************************************************************/
  public static String[] retrieveParams(Object[] keys, Map paramMap, char escape){
  	String[] ret = new String[keys.length];
  	int index = 0;
  	String replaceSeq = StringEscapeUtils.escapeHtml4(""+escape);
  	for(Object key : keys){
  		Object value = paramMap.get(key);
  		String safeValue = "";
  		if(value!=null){
  			safeValue = ""+value;
  			safeValue = safeValue.replace(""+escape, replaceSeq);
  		}
  		ret[index++] = safeValue;
  	}
  	return ret;
  }
  
  /****************************************************************************************************
	*숫자형 데이터를 회계단위로 표현(10000=>10,000)
	* @param numData 텍스트형 숫자 데이타
	* @return String 변형된 데이타
	****************************************************************************************************/
	public String numFormat(String numData){
		NumberFormat nf=NumberFormat.getInstance();
		String chgDataType="0";
		try{
			chgDataType=nf.format(Long.parseLong(numData));
		}catch(RuntimeException e){
			Logger.getLogger(ReqUtils.class).debug("err numFormat");
		}
		return chgDataType;
	}
	
	/****************************************************************************************************
	* Object를 String 으로 바꿔서 첫 X 캐릭터를 돌려준다.  입력이 null 이면 "" 리턴.
	****************************************************************************************************/
    public static String prefix(Object o, int length){
    	if(o==null) return "";
    	String s = ""+o;
    	return s.substring(0, Math.min(s.length(), length));
    }
    
    private static String filter(String value) {
    	if(value==null) {
            return null;
        }
    	
    	//You'll need to remove the spaces from the html entities below    	
    	value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    	//value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
    	//value = value.replaceAll("'", "&#39;");
    	value = value.replaceAll("eval\\((.*)\\)", "");
    	value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
    	value = value.replaceAll("script", "");
    	/*value = value.replaceAll("onabort","").
				        replaceAll("onactivate","").
				        replaceAll("afterprint",""). 
				        replaceAll("afterupdate","").
				        replaceAll("beforeactivate","").
				        replaceAll("beforecopy","").
				        replaceAll("beforecut","").
				        replaceAll("beforedeactivate","").
				        replaceAll("beforeeditfocus","").
				        replaceAll("beforepaste","").
				        replaceAll("beforeprint","").
				        replaceAll("beforeunload","").
				        replaceAll("beforeupdate","").
				        replaceAll("blur","").
				        replaceAll("bounce","").
				        replaceAll("cellchange","").
				        replaceAll("change","").
				        replaceAll("click","").
				        replaceAll("contextmenu","").
				        replaceAll("controlselect","").
				        replaceAll("copy","").
				        replaceAll("cut","").
				        replaceAll("dataavailable","").
				        replaceAll("datasetchanged","").
				        replaceAll("datasetcomplete","").
				        replaceAll("dblclick","").
				        replaceAll("deactivate","").
				        replaceAll("drag","").
				        replaceAll("dragend","").
				        replaceAll("dragenter","").
				        replaceAll("dragleave","").
				        replaceAll("dragover","").
				        replaceAll("dragstart","").
				        replaceAll("drop","").
				        replaceAll("error","").
				        replaceAll("errorupdate","").
				        replaceAll("filterchange","").
				        replaceAll("finish","").
				        replaceAll("focus","").
				        replaceAll("focusin","").
				        replaceAll("focusout","").
				        replaceAll("help","").
				        replaceAll("keydown","").
				        replaceAll("keypress","").
				        replaceAll("keyup","").
				        replaceAll("layoutcomplete","").
				        replaceAll("load","").
				        replaceAll("losecapture","").
				        replaceAll("mousedown","").
				        replaceAll("mouseenter","").
				        replaceAll("mouseleave","").
				        replaceAll("mousemove","").
				        replaceAll("mouseout","").
				        replaceAll("mouseover","").
				        replaceAll("mouseup","").
				        replaceAll("mousewheel","").
				        replaceAll("move","").
				        replaceAll("moveend","").
				        replaceAll("movestart","").
				        replaceAll("paste","").
				        replaceAll("propertychange","").
				        replaceAll("readystatechange","").
				        replaceAll("reset","").
				        replaceAll("resize","").
				        replaceAll("resizeend","").
				        replaceAll("resizestart","").
				        replaceAll("rowenter","").
				        replaceAll("rowexit","").
				        replaceAll("rowsdelete","").
				        replaceAll("rowsinserted","").
				        replaceAll("scroll","").
				        replaceAll("select","").
				        replaceAll("selectionchange","").
				        replaceAll("selectstart","").
				        replaceAll("start","").
				        replaceAll("stop","").
				        replaceAll("submit","").
				        replaceAll("eval\\((.*)\\)", "");*/
    	return value;
    }
    

}
