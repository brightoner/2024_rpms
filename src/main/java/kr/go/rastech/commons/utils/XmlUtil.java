/******************************************************************
 * Copyright RASTECH 2018
 ******************************************************************/
package kr.go.rastech.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import kr.go.rastech.commons.constants.Constants;

/**
 * <pre>
 * FileName: XmlUtil.java
 * Package : kr.go.ncmiklib.commons.utils
 *
 * XML 변환 관련 유틸리티 객체.
 *
 * </pre>
 * @author : rastech
 * @date   : 2018. 2. 20.
 */
public class XmlUtil {
 
    /** 
     *
     * <pre>
     * 리스트를 XML로 변환한다. 
     * 리스트 객체는 <items>로 DTO는 <item>으로 변환 한다.
     * XStream을 사용하고 있다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String listToXml(List list) {
        if (list == null) return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<items/>";

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
        XStream xStream = new XStream(new DomDriver(Constants.ENCODING_TYPE));
        xStream.alias("items", List.class);

        if (!list.isEmpty()) {
            xStream.alias("item", list.get(0).getClass());
            xStream.processAnnotations(list.get(0).getClass());
        }

        xml += xStream.toXML(list);
        
        xml = xml.replace("__", "_");

        return xml;
    }

    /**
     *
     * <pre>
     * 리스트를 한줄짜리 XML로 변환한다.
     * 작은따움표(')는 \' 로 변환한다.
     * 리스트 객체는 <items>로 DTO는 <item>으로 변환 한다.
     * XStream을 사용하고 있다.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String listToXmlLine(List list) {
        String xml = listToXml(list);
        return xml.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " " ).replaceAll("\\'", "\\\\'");
    }

    /**
     *
     * <pre>
     * 객체를 XML로 변환.
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 9.
     * @param obj
     * @return
     */
    public static String objToXml(Object obj) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
        XStream xStream = new XStream(new DomDriver(Constants.ENCODING_TYPE));
        xStream.alias("item", obj.getClass());
        xStream.processAnnotations(obj.getClass());
        xml += xStream.toXML(obj);
        xml = xml.replace("__", "_");
        return xml;
    }

    /**
     *
     * <pre>
     * Map을 XML로 변환
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 9. 14.
     * @param map
     * @return
     */
    public static String mapToXml(Map map) {
    	StringBuffer xml = new StringBuffer();
    	xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
    	xml.append("<item>");
    	for (Object key: map.keySet()) {
    		xml.append("<");
    		xml.append(key);
    		xml.append(">");
    		//xml.append(map.get(key).toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		if(map.get(key) != null){
       			xml.append(map.get(key).toString());
       		}else{
       			xml.append("");
       		}
    		xml.append("</");
    		xml.append(key);
    		xml.append(">\r\n");
    	} 
    	xml.append("</item>");

    	return xml.toString();
    }
    
    /**
    *
    * <pre>
    * Map을 XML로 변환
    *
    * </pre>
    * @author : rastech
    * @date   : 2023. 9. 14.
    * @param map
    * @return
    */
   public static String listMapToXml(List<Map<String,Object>> list) {
   	StringBuffer xml = new StringBuffer();
   	xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
   	xml.append("<items>\n");
   	for(Map<String, Object> map : list)
       {
   		if(map != null){
	       	xml.append("<item>");
	       	for (Object key: map.keySet()) {
	       		xml.append("<");
	       		xml.append(key);
	       		xml.append(">");
	       		if(map.get(key) != null){
	       			xml.append(map.get(key).toString());
	       		}else{
	       			xml.append("");
	       		}
	       		xml.append("</");
	       		xml.append(key);
	       		xml.append(">\r\n");
	       	}
	       	xml.append("</item>\n");
   		}
       }
   	xml.append("</items>");
   	return xml.toString();
   }
   
   /**
   *
   * <pre>
   * Map을 XML로 변환
   *
   * </pre>
   * @author : rastech
   * @date   : 2023. 9. 14.
   * @param map
   * @return
   */
  public static String listMapToXmlCnt(List<Map<String,Object>> list, int total) {
  	StringBuffer xml = new StringBuffer();
  	xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
  	xml.append("<items>\n");
  	xml.append("<total>");
	xml.append(total);
	xml.append("</total>\n");
  	for(Map<String, Object> map : list)
      {
  		if(map != null){
	       	xml.append("<item>");
	       	for (Object key: map.keySet()) {
	       		xml.append("<");
	       		xml.append(key);
	       		xml.append(">");
	       		if(map.get(key) != null){
	       			xml.append(map.get(key).toString());
	       		}else{
	       			xml.append("");
	       		}
	       		xml.append("</");
	       		xml.append(key);
	       		xml.append(">\r\n");
	       	}
	       	xml.append("</item>\n");
  		}
      }
  	xml.append("</items>");
  	return xml.toString();
  }
   
   /**
    *
    * <pre>
    * Map을 XML로 변환
    *
    * </pre>
    * @author : rastech
    * @date   : 2023. 9. 14.
    * @param map
    * @return
 * @throws SQLException 
 * @throws IOException 
    */
   public static String listColMapToXml(List<Map<String,Object>> list,String[] col_name) throws SQLException, IOException {
	   StringBuffer xml = new StringBuffer();
	   xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
	   xml.append("<items>\n");
	   for(Map<String, Object> map : list)
	   {
		   if(map != null){
			   xml.append("<item>");
			   for (String key:col_name) {
				   xml.append("<");
				   xml.append(key);
				   xml.append(">");
				   if(map.get(key) != null){
						if(map.get(key) instanceof java.sql.Clob){
							StringBuffer strOut = new StringBuffer();
							 String str = "";
							 Clob clob = (java.sql.Clob)map.get(key);
							 BufferedReader br = new BufferedReader(clob.getCharacterStream());
							 while ((str = br.readLine()) != null) {
								 strOut.append(str);
							 }
							 xml.append(strOut.toString());
						}else{
							xml.append(map.get(key).toString());
						}
				   }else{
					   xml.append("");
				   }
				   xml.append("</");
				   xml.append(key);
				   xml.append(">\r\n");
			   }
			   xml.append("</item>\n");
		   }
	   }
	   xml.append("</items>");
	   return xml.toString();
   }
   
   
   	public static String downloadMapToXml(Map map,String type){
   			
   		String dbid = "";
   		if(StringUtils.equals("KMBASE", type)){
   			dbid = "KMBASEID";
   		}else{
   			dbid = "KNLMID";
   		}
   		
   		StringBuffer xml = new StringBuffer();
    	xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
    	xml.append("<!DOCTYPE MedlineCitationSet PUBLIC \"-//NLM//DTD Medline Citation, 1st January, 2015//EN\" \"http://www.nlm.nih.gov/databases/dtd/nlmmedlinecitationset_150101.dtd\">\r\n");
    		
    		xml.append("<MedlineCitationSet>\r\n<MedlineCitation Owner=\"NLM\" Status=\"PubMed-not-MEDLINE\">\r\n");
    		xml.append("<"+dbid+" Version=\"1\">");
    		if(map.get("KNLMID") != null){
    			xml.append(map.get("KNLMID").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</"+dbid+">\r\n<Article PubModel=\"Print\">\r\n<Journal>\r\n");
    		xml.append("<ISSN IssnType=\"Print\">");
    		if(map.get("PISSN")!=null){
    			xml.append(map.get("PISSN").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</ISSN>\r\n");
    		xml.append("<ISSN IssnType=\"Print\">");
    		if(map.get("EISSN")!=null){
    			xml.append(map.get("EISSN").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</ISSN>\r\n");
    		xml.append("<JournalIssue CitedMedium=\"Print\">\r\n");
    		xml.append("<Volume>");
    		
    		if(map.get("VOLUMENAME")!=null){
    			String volArr[] = map.get("VOLUMENAME").toString().split(" ");;
    			xml.append(volArr[0]);
    		}else{
    			xml.append("");
    		}
    		xml.append("</Volume>\r\n");
    		xml.append("<Issue>");
    		if(map.get("VOLUMENAME")!=null){
    			String volArr[] = map.get("VOLUMENAME").toString().split(" ");;
    			xml.append(volArr[1]);
    		}else{
    			xml.append("");
    		}
    		xml.append("</Issue>\r\n<PubDate>\r\n");
    		if(map.get("DP")!=null){
   				String[] pubArr = map.get("DP").toString().split(" ");
   				if(pubArr.length == 1){
   					xml.append("<Year>");
   		    		xml.append(pubArr[0]);
   		    		xml.append("</Year>\r\n");
   				}else{
   					xml.append("<Year>");
   		    		xml.append(pubArr[0]);
   		    		xml.append("</Year>\r\n");
   		    		xml.append("<Month>");
   		    		xml.append(pubArr[1]);
   		    		xml.append("</Month>\r\n");
   		    		xml.append("<Day>");
   		    		xml.append(pubArr[2]);
   		    		xml.append("</Day>\r\n");
   				}
			
    		}else{
    			xml.append("<Year>");
	    		xml.append("</Year>\r\n");
	    		xml.append("<Month>");
	    		xml.append("</Month>\r\n");
	    		xml.append("<Day>");
	    		xml.append("</Day>\r\n");
    		}
    		xml.append("</PubDate>\r\n</JournalIssue>\r\n");
    		xml.append("<Title>");
    		if(map.get("JT")!=null){
    			xml.append(map.get("JT").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</Title>\r\n</Journal>\r\n");
    		xml.append("<ArticleTitle>");
    		if(map.get("TITLE")!=null){
    			xml.append(map.get("TITLE").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</ArticleTitle>\r\n");
    		xml.append("<AbstractText>");
    		if(map.get("AB")!=null){
    			xml.append(map.get("AB").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		if(map.get("AB2")!=null){
    			xml.append("\r\n"+map.get("AB2").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("\r\n");
    		}
    		xml.append("</AbstractText>\r\n");
    		xml.append("<Pagination>\r\n<MedlinePgn>");
    		if(map.get("PAGE")!=null){
    			xml.append(map.get("PAGE").toString().replaceAll("&", "&amp;").replace("<","&lt;").replace(">","&gt;"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</MedlinePgn>\r\n</Pagination>\r\n");
    		xml.append("<AuthorList CompleteYN=\"Y\">\r\n");
    		if(map.get("AU")!=null){
    			
    			if(StringUtils.equals((CharSequence) map.get("OWN") , "KMBASE")){
    				xml.append("<Author ValidYN=\"Y\">\r\n<CollectiveName>");
	    			xml.append(map.get("AU").toString().trim()+"</CollectiveName>\r\n");
	    			xml.append("</Author>\r\n");
    			}else{
    				String[] autArr = map.get("AU").toString().split(",");
        			
    	    		for (int i = 0; i < autArr.length; i++) {
    	    			xml.append("<Author ValidYN=\"Y\">\r\n<CollectiveName>");
    	    			xml.append(autArr[i].toString().trim()+"</CollectiveName>\r\n");
    	    			xml.append("</Author>\r\n");
    				}
    			}
    			
    		
    		}else{
    			xml.append("<Author ValidYN=\"Y\">\r\n<CollectiveName>");
    			xml.append("</Author>\r\n");
    		}
    		xml.append("</AuthorList>\r\n");
    		xml.append("<Language>");
    		if(map.get("LA")!=null){
    			xml.append(map.get("LA"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</Language>");
    		xml.append("<"+dbid+">");
    		if(map.get("ARTCTRLNO")!=null){
    			xml.append(map.get("ARTCTRLNO"));
    		}else{
    			xml.append("");
    		}
    		xml.append("</"+dbid+">");
    		xml.append("<PublicationTypeList>\r\n");
    		xml.append("<PublicationType UI=\"\">Journal Article</PublicationType>\r\n</PublicationTypeList>\r\n</Article>\r\n</MedlineCitation>\r\n</MedlineCitationSet>");
    		

    	return xml.toString();
   	}

	public static String listMapToXssXml(List<Map<String, Object>> list) {

	   	StringBuffer xml = new StringBuffer();
	   	xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
	   	xml.append("<items>\n");
	   	for(Map<String, Object> map : list)
	       {
	       	xml.append("<item>");
	       	for (Object key: map.keySet()) {
	       		xml.append("<");
	       		xml.append(String.valueOf(key).replaceAll("@#",""));
	       		xml.append(">");
	       		if(map.get(key) != null){
	       			xml.append(String.valueOf(map.get(key)).replaceAll("@#",""));
	       		}else{
	       			xml.append("");
	       		}
	       		xml.append("</");
	       		xml.append(String.valueOf(key).replaceAll("@#",""));
	       		xml.append(">\r\n");
	       	}
	       	xml.append("</item>\n");
	       }
	   	xml.append("</items>");
	   	return xml.toString();
	}
	
	
	 public static int getXmlData(HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException {
	    	
	    	MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request; 
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames(); 
			MultipartFile multipartFile = null; 
			NodeList n_list = null;
			while(iterator.hasNext()){
				multipartFile = multipartHttpServletRequest.getFile(iterator.next()); 
				if(multipartFile.isEmpty() == false){ 
					
			        //1.문서를 읽기위한 공장을 만들어야 한다.
			           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			            
			           //2.빌더 생성
			           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			            
			           //3.생성된 빌더를 통해서 xml문서를 Document객체로 파싱해서 가져온다
			       Document doc = dBuilder.parse(multipartFile.getInputStream());
			       doc.getDocumentElement().normalize();//문서 구조 안정화
			        
			       Element root = doc.getDocumentElement();
			        
			       n_list = root.getElementsByTagName("trial");
				} 
			}	
	       return n_list.getLength();
	  }

		public static String listLinkedHashMapToXssXml(List<LinkedHashMap<String, Object>> list) {

		   	StringBuffer xml = new StringBuffer();
		   	xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		   	xml.append("<items>\n");
		   	for(Map<String, Object> map : list)
		       {
		       	xml.append("<item>");
		       	for (Object key: map.keySet()) {
		       		xml.append("<");
		       		xml.append(String.valueOf(key).replaceAll("@#",""));
		       		xml.append(">");
		       		if(map.get(key) != null){
		       			xml.append(String.valueOf(map.get(key)).replaceAll("@#","").replaceAll("&", "&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;"));
		       		}else{
		       			xml.append("");
		       		}
		       		xml.append("</");
		       		xml.append(String.valueOf(key).replaceAll("@#",""));
		       		xml.append(">\r\n");
		       	}
		       	xml.append("</item>\n");
		       }
		   	xml.append("</items>");
		   	return xml.toString();
		}
}
