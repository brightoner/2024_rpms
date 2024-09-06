package kr.go.rastech.commons.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import com.hp.hpl.jena.graph.query.Element;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.vo.AlertVO;

/**
 * <pre>
 * FileName: SmsUtil.java
 * Package : kr.go.rastech.commons.utils
 * 
 * 메일 / 문자 - Controller
 *
 * </pre>
 * @author : lwk
 * @date   : 2023. 6. 17.
 */
public class SmsUtil  extends BaseController {
    
	private static final String API_URL = "https://api.tason.com/tas-api/send";
    private static final int CONNECTION_TIMEOUT = 20 * 1000; // 연결 시간 초과 (20초)
    private static final int SOCKET_TIMEOUT = 20 * 1000; // 소켓 시간 초과 (20초)
	    
	public String sendMail(String user_name , String email , String subject , String content)  throws   IOException, URISyntaxException {
		/* System.out.println("####################");
         System.out.println("user_name===========================" + user_name);
         System.out.println("email===========================" + email);
         System.out.println("subject===========================" + subject);
         System.out.println("content===========================" + content);
		 System.out.println("####################");*/
		String successYn = "F"; 
		CloseableHttpClient httpClient =  HttpClientBuilder.create().build();
	        CloseableHttpResponse response = null;

	        try {
	            URIBuilder uriBuilder = new URIBuilder(API_URL);

	            HttpPost httpPost = new HttpPost(uriBuilder.build());
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");

	            // 메일 발송에 필요한 정보 설정
	            // ...

	            // AlertVO 객체 생성 및 설정
	            AlertVO vo = new AlertVO();	          
	            vo.setUser_name(user_name);
	            vo.setUser_email(email);
	            vo.setSender("donginjjang@gmail.com"); 
	            vo.setMap_content(StringEscapeUtils.unescapeHtml3(content));
	            vo.setSender_name("머니버니");
	            vo.setSubject(subject);
	            
	            // List에 AlertVO 추가
	            List<AlertVO> list = new ArrayList<>();
	            list.add(vo);	   
	            String result = JsonUtil.mailToJsonString(list);
	            StringEntity reqEntity = new StringEntity(result, "UTF-8");
	            httpPost.setEntity(reqEntity);

	            // 요청 및 응답 처리
	            response = httpClient.execute(httpPost);

	            int statusCode = response.getStatusLine().getStatusCode();
	      
	            if (statusCode == 200) {
	                String responseBody = EntityUtils.toString(response.getEntity());
	                logger.debug("##########################################################################");
	                logger.debug("sendEmail responseBody= " + responseBody);
	                logger.debug("##########################################################################");
	                successYn = "Y";
	                // 응답 데이터 처리
	                // ...
	            } else {
	                System.err.println("Mail sending failed. Status code: " + statusCode);
	                successYn = "F";
	            }
	        } catch (IOException | URISyntaxException e) {
	            System.err.println("Error occurred while sending mail: " + e.getMessage());
	            successYn = "F";
	        } finally {
	            if (response != null) {
	                response.close();
	            }
	            httpClient.close();
	        }
	        
			return successYn;
		
	}
	
	
	public String sendSms(String user_name , String email , String subject , String content)  throws   IOException, URISyntaxException {
		/* System.out.println("####################");
         System.out.println("user_name===========================" + user_name);
         System.out.println("email===========================" + email);
         System.out.println("subject===========================" + subject);
         System.out.println("content===========================" + content);
		 System.out.println("####################");*/
		 String successYn = "F"; 
		 CloseableHttpClient httpClient =  HttpClientBuilder.create().build();
         CloseableHttpResponse response = null;

	        try {
	            URIBuilder uriBuilder = new URIBuilder(API_URL);

	            HttpPost httpPost = new HttpPost(uriBuilder.build());
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");

	            // 메일 발송에 필요한 정보 설정
	            // ...

	            // AlertVO 객체 생성 및 설정
	            AlertVO vo = new AlertVO();
	            email = email.replaceAll("-", "");
	            
	            vo.setUser_name(user_name);
	            vo.setUser_email(email);
	            vo.setSender("01026222245");	          	         	            
	            vo.setMap_content(content);
	            vo.setSender_name("머니버니");
	            vo.setSubject(subject);
	            
	            // List에 AlertVO 추가
	            List<AlertVO> list = new ArrayList<>();
	            list.add(vo);

	            String result = JsonUtil.smsToJsonString(list);
	            StringEntity reqEntity = new StringEntity(result, "UTF-8");
	            httpPost.setEntity(reqEntity);

	            // 요청 및 응답 처리
	            response = httpClient.execute(httpPost);

	            int statusCode = response.getStatusLine().getStatusCode();
	      
	            if (statusCode == 200) {
	                String responseBody = EntityUtils.toString(response.getEntity());
	                logger.debug("##########################################################################");
	                logger.debug("sendSms responseBody= " + responseBody);
	                logger.debug("##########################################################################");
	                successYn = "Y";
	                // 응답 데이터 처리
	                // ...
	            } else {
	                System.err.println("sendSms sending failed. Status code: " + statusCode);
	                successYn = "F";
	            }
	        } catch (IOException | URISyntaxException e) {
	            System.err.println("Error occurred while sending sendSms: " + e.getMessage());
	            successYn = "F";
	        } finally {
	            if (response != null) {
	                response.close();
	            }
	            httpClient.close();
	        }
	        
			return successYn;
		
	}
/*
	public void sendMsg(HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		String body = "";
	    HttpClient httpClient = new DefaultHttpClient();
	    
	    String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
		String content = request.getParameter("content");
		String template_code = request.getParameter("template_code");
	    
		try {
			String url ="https://api.tason.com/tas-api/kakaosend"; 
		    HttpPost httpPost = new HttpPost(url);
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
		    List<AlertVO> list = new ArrayList<AlertVO>();
		    AlertVO vo = new AlertVO();
		    vo.setUser_name(user_name);
		    vo.setUser_email(email);
		    vo.setSender("01030786573");
		    vo.setMap_content(content);
		    vo.setSender_name("국립의과학지식센터");
		    vo.setTemplate_code(template_code);
		    list.add(vo);
		    
		    String result = JsonUtil.msgToJsonString(list);
		    //Post 방식인 경우 데이터를 Request body message에 전송
 	    	StringEntity reqEntity = new StringEntity(result, "UTF-8");
		    httpPost.setEntity(reqEntity);
		    int useTimeout = 20;
		      if(useTimeout>0){ 
		    	  httpClient.getParams().setParameter("http.protocol.expect-continue", false);//HttpClient POST 요청시 Expect 헤더정보 사용 x
		    	  httpClient.getParams().setParameter("http.connection.timeout", useTimeout * 1000);// 원격 호스트와 연결을 설정하는 시간
		    	  httpClient.getParams().setParameter("http.socket.timeout",  useTimeout * 1000);//데이터를 기다리는 시간
		    	  httpClient.getParams().setParameter("http.connection-manager.timeout",  useTimeout * 1000);// 연결 및 소켓 시간 초과 
		    	  httpClient.getParams().setParameter("http.protocol.head-body-timeout",  useTimeout * 1000);
		        }
		    HttpResponse response = httpClient.execute(httpPost);
		    if (response.getStatusLine().getStatusCode() == 200) {
		    	ResponseHandler<String> handler = new BasicResponseHandler();
				 body = handler.handleResponse(response);
     
		    }
		    
		} catch(IOException e) {
			System.err.print("error error error error IOException = advancedSearch");
		}catch(NullPointerException e) {
			System.err.print("error error error error NullPointerException = advancedSearch");
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	
	}*/
}
