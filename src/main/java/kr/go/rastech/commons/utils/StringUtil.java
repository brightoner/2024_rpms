/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.nhncorp.lucy.security.xss.XssFilter;

/**
 * <pre>
 * FileName: StringUtil.java
 * Package : kr.go.ncmiklib.commons.utils
 *
 * String 값 변환 utilitiy 객체.
 *
 * </pre>
 * @author : rastech
 * @data   : 2023. 3. 6.
 */
public class StringUtil {

    //날짜 검증
    private static final DateFormat DF =  new SimpleDateFormat("yyyyMMdd");
    public static final DecimalFormat MONEYFORMAT = new DecimalFormat("###,###,###,###,##0");
    /**
     *
     * <pre>
     * \r\n을 <br/>로 변환한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 6.
     * @param str
     * @return
     */
    public static String convBr(String str) {
        if (str == null) return str;
        return str.replaceAll("\r\n", "<br/>").replaceAll("\r", "<br/>").replaceAll("\n", "<br/>");
    }

    /**
     *
     * <pre>
     * XSS 공격 방어 필터.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 9. 21.
     * @param str
     * @return
     */
    public static String filterXss(String str) {
        XssFilter filter = XssFilter.getInstance("lucy-xss.xml", true);
        String rtn = filter.doFilter(str);
        rtn = rtn.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        rtn = rtn.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        rtn = rtn.replaceAll("'", "& #39;");
        rtn = rtn.replaceAll("eval\\((.*)\\)", "");
        rtn = rtn.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        rtn = rtn.replaceAll("script", "");

        return rtn;
    }

    /**
     *
     * <pre>
     * String 객체를 deep copy하여 반환한다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 12. 7.
     * @param str
     * @return
     */
    public static String deepCopy(String str) {
    	if (str == null) return null;
    	return String.valueOf(str);
    }

    /**
    *
    * <pre>
   * 데이터 검증
   *
   * </pre>
   * @author : rastech
    * @date   : 2023. 10. 21
    * @param param[0] : 데이터값
    *             , param[1] : 컬럼 타입
    *             , param[2] : 제한길이 0인경우 체크안함
    *    컬럼 타입
    *    0 : 해당사항없이 길이만 체크
    *    1 : 숫자 체크
    *    2 : 영문자만 체크
    *    3 : 한글만 체크
    *    4 : 영어/숫자만 체크
    *    5 : 이메일 체크
    *    6 : 소수점 필수 체크
    *    7 : 소수점 자유
    *    8 : 날짜 검증
    * @return
    * @throws IOException, SQLException , NullPointerException
    */
    public static  boolean isValidate(String val, int gbn, int limit){

          boolean isVlidt =true;

          switch (gbn) {

          case 1://숫자
           isVlidt = Pattern.matches("^[0-9]*$", val);
           break;
          case 2://영문자만
           isVlidt = Pattern.matches("^[a-zA-Z]*$", val);
           break;
          case 3://한글만
           isVlidt = Pattern.matches("^[가-힣]*$", val);
           break;
          case 4://영어/숫자만
           isVlidt = Pattern.matches("^[a-zA-Z0-9]*$", val);
           break;
          case 5://이메일체크
           isVlidt = Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", val);
           break;
          case 6://소수점 필수 param : 제한 소수점
           isVlidt = Pattern.matches("[0-9]*\\.[0-9]*", val);
           break;
          case 7://소수점 자유
           isVlidt = Pattern.matches("[0-9]*\\.?[0-9]*", val);
           break;
          case 8://날짜 타입 검증
           try{
                  DF.setLenient(false);
                  java.util.Date df2 = DF.parse(val);
                  isVlidt = Pattern.matches("[1-2]{1}[0-9]{3}[0-9]{2}[0-9]{2}", val);
              
               } catch (ParseException e) {
            	   isVlidt=false;
               }
           break;
          case 9://한글/영어만
              isVlidt = Pattern.matches("^[가-힣a-zA-Z]*$", val);
              break;
          case 10://정수
              isVlidt = Pattern.matches("^-?[0-9]*$", val);
              break;
          default://정수
              
              break;
          }
          //제한길이가 0이상으로 넘어온 경우 길이 체크
          if(limit > 0){
           if(val.length() > limit ){
            isVlidt = false;
           }
          }
          return isVlidt;
    }
    
    public static String removeHan(String str)
    {
        String return_value = "";
        if(str == null) {return return_value;}
        for(int i = 0; i < str.length(); i++)
        {
            if(Character.getType(str.charAt(i)) != 5 && Character.getType(str.charAt(i)) != 21 && Character.getType(str.charAt(i)) != 22)
            {
                return_value = return_value + str.charAt(i);
            }
        }
        return return_value;
    }   
    
	/**
	 * NULL 값을 제거한다. 입력값(text)이 NULL이이면 공백("")을 리턴한다.
	 * @param text NULL을 제거할 입력값
	 * @return NULL을 제거한 값
	 */
	public static String nvl(Object text) {
		if(text == null) {
			return "";
		} else {
			try {
			if(text instanceof java.sql.Clob){
				StringBuffer strOut = new StringBuffer();
				 String str = "";
				 Clob clob = (java.sql.Clob)text;
				 BufferedReader br;
				
					br = new BufferedReader(clob.getCharacterStream());
				
				 while ((str = br.readLine()) != null) {
					 strOut.append(str);
				 }
				 return nvl(strOut.toString());
			}else{
				return nvl(text.toString(), "");
			}
			} catch (SQLException e) {
				return nvl(text.toString(), "");
			} catch(IOException e){
				return nvl(text.toString(), "");
			}
			
		}
	}
	/**
	 * NULL 값을 제거한다. 입력값(text)이 NULL이이면 공백("")을 리턴한다.
	 * @param text NULL을 제거할 입력값
	 * @return NULL을 제거한 값
	 */
	public static String nvl(String text) {
		return nvl(text, "");
	}
	/**
	 * NULL 값을 제거한다. 입력값(text)이 NULL이거나 공백("")이면 기본값(value)을 리턴한다.
	 * @param text NULL을 제거할 입력값
	 * @param value 기본값
	 * @return NULL을 제거한 값
	 */
	public static String nvl(Object text, String value) {
		if(text == null) {
			return value;
		} else {
			return nvl(text.toString(), value);
		}
	}
	/**
	 * NULL 값을 제거한다. 입력값(text)이 NULL이거나 공백("")이면 기본값(value)을 리턴한다.
	 * @param text NULL을 제거할 입력값
	 * @param value 기본값
	 * @return NULL을 제거한 값
	 */
	public static String nvl(String text,String value) {
		if(text == null) {
			return value;
		} else if(text.equals("")) {
			return value;
		} else {
			return text;
		}
	}
	/**
	 * NULL 값을 제거한다. 입력값(text)이 NULL이거나 공백(""), 혹은 int형으로 전환할수 없는 값이면 기본값(value)을 리턴한다.
	 * @param text NULL을 제거할 입력값
	 * @param value 기본값
	 * @return NULL을 제거하고 int형으로 변환된 값
	 */
	public static int nvl(Object text, int value) {
		if(text == null) {
			return value;
		} else {
			return nvl(text.toString(), value);
		}
	}
	/**
	 * NULL 값을 제거한다. 입력값(text)이 NULL이거나 공백(""), 혹은 int형으로 전환할수 없는 값이면 기본값(value)을 리턴한다.
	 * @param text NULL을 제거할 입력값
	 * @param value 기본값
	 * @return NULL을 제거하고 int형으로 변환된 값
	 */
	public static int nvl(String text,int value) {
		if(text == null) {
			return value;
		} else if(text.equals("")) {
			return value;
		} else {
			return toInt(text, value);
		}
	}


/*	public static boolean equals(String text,int value) {
		return equals(text, ""+value);
	}
	public static boolean equals(String text, String value) {
		if(text == null) text = "";
		if(value == null) {
			//return equals(text, null);
			return text.equals(value);
		} else {
			//return equals(text, value.toString());
			return text.equals(value);
		}
	}*/

	/**
	 * 문자열이 같은지 검사한다.
	 * @param text 첫번째 문자열
	 * @param value 두번째 문자열
	 * @return 두 문자열이 값으면 true, 그렇지 않으면 false를 리턴한다.
	 */
/*	public static boolean equals(String text,Object value) {
		if(value == null) {
			return equals(text, null);
		} else {
			return equals(text, value.toString());
		}
	}*/



/*	public static String equalsGet(String key, String value, String str1, String str2) {
		return (equals(key, value) ? str1 : str2);
	}*/


	/**
	 * DB의 스트링을 input box 로 보여 질때.
	 * @param str
	 * @return
	 */
	public static String toInputbox(String str){
		if (str == null){ return "" ; }
		StringBuffer buf = new StringBuffer(str.length());
		char ch;
		for (int i = 0, j = str.length(); i < j ; i++) {
			switch (ch = str.charAt(i)){
				case '\"' : buf.append("&quot;"); break ;
				case '<' : buf.append("&lt;"); break ;
				case '>' : buf.append("&gt;"); break ;
				default   : buf.append(ch); break;
			}
		}
		return buf.toString();
	}
	
	public static int toInt(String str) {
		int rtn = 0;
		try {
			if( str != null ) {rtn = Integer.parseInt(str);}
		} catch(NumberFormatException e){
	  		rtn = 0 ;
	  		System.err.println("StringUtil NF 오류 발생");
	  	}  catch(RuntimeException e){
	  		rtn = 0 ;
	  		System.err.println("StringUtil EX 오류 발생");
	    }
		return rtn;
	}
	
	/**
	 * String을 int 형으로 리턴한다. int 형으로 변환과정에서 에러가 발생한 경우 기본값(value)를 리턴한다.
	 * @param str 숫자로 변환할 문자열
	 * @param value 기본값
	 * @return 숫자로 변환된 값
	 */
	public static int toInt(String str, int value) {
		int rtn = 0;
		try {
			if( str != null ) {rtn = Integer.parseInt(str);}
		} catch(NumberFormatException e){
	  		rtn = value ;
	  		System.err.println("StringUtil NF 오류 발생");
	  	}  catch(RuntimeException e){
	  		rtn = value ;
	  		System.err.println("StringUtil EX 오류 발생");
	    }		
		return rtn;
	}
	
	public static String getMoney(double money) {
		try{
		return MONEYFORMAT.format(money);
		} catch(NumberFormatException e){
			return "0";
		} catch(RuntimeException e) {
			return "0";
		}
		
	}

	public static String getMoney(String money) {
		try{		
			
			if(money == null || money.equals("")) {return "";}
			return getMoney( Double.parseDouble( money ) );
		
		} catch(NumberFormatException e){
			return "0";
		} catch(RuntimeException e) {
			return "0";
		}
	}
	
	//숫자 판별
	public static boolean isNumber(String istr){
		try{
			Double.parseDouble(istr);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
}
