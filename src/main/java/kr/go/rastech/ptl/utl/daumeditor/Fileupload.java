package kr.go.rastech.ptl.utl.daumeditor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;








import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class Fileupload {
	 private static final Logger LOG = Logger.getLogger(Fileupload.class.getName());
	
	   @RequestMapping("/utl/daumeditor/daumFileUpLoad.do")
	   public String atchDirectImage(HttpServletRequest request, Model model) throws IOException, SQLException {
	            
		   boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	      
	      // 썸네일 생성 여부 [GNR : 일반, THM : 썸네일 생성]
	      ArrayList linklist = new ArrayList();
	      
	      if(isMultipart) 
	      {
	         try{
	            
	            int sizeThreshold = 1024 * 1024;   // 메모리에 읽어들일 버퍼의 크기이다. 전체 파일 크기가 아니다.
	            
	            MultipartHttpServletRequest multipartRequest =  (MultipartHttpServletRequest)request;  //다중파일 업로드
	            List<MultipartFile> files = multipartRequest.getFiles("file_upload");
	            
	            for(int i = 0; i < files.size(); i++)
	            {
	               MultipartFile mulfile = files.get(i);
	               if(mulfile.getSize() == 0)
	               {
	                  continue;
	               }
	               
	               String orgFileName = mulfile.getOriginalFilename();  
	               long sizeInBytes = mulfile.getSize();
	                           
	               // 파일 확장자 구하기
	               String file_ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1, orgFileName.length());
	               file_ext = file_ext.toLowerCase();
	               
	                  // 허용된 파일 이외에는 첨부할 수 없음
	                  if("gif|jpg|png|bmp|zip|xls|xlsx|ppt|pptx|doc|docx|txt|hwp|pdf|sav|dta|sd2".indexOf(file_ext) < 0)
	                  {
	                  throw new RuntimeException("첨부할 수 없는 파일 형식입니다.");
	               }
	               
	                 String dftFilePath = request.getSession().getServletContext().getRealPath("/data/upload/");
	                
	                //파일 기본경로 _ 상세경로
	                String filePath = dftFilePath + File.separator + "rasupload" + File.separator;
	             
		                if(!filePath.endsWith("what")){			                
		                	File file = new File(filePath);
			                if (!file.exists()) {
			                	file.setExecutable(false);
			                	file.setReadable(true);
			                	file.setWritable(false);
			                	file.mkdirs();
			                }
		                }    
		                
		                String realFileNm = "";
		                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		                String today = formatter.format(new java.util.Date());
		                realFileNm = today + UUID.randomUUID().toString() + orgFileName.substring(orgFileName.lastIndexOf("."));
		                
		                String file_real = filePath + realFileNm;
		                
		                ///////////////// 서버에 파일쓰기 ///////////////// 
		                if(!file_real.endsWith("what")){
			                File uploadFile = new File(file_real);
			                mulfile.transferTo(uploadFile);  
		                }
		                ///////////////// 서버에 파일쓰기 /////////////////
		                
		                //String fullpath = request.getRequestURL().indexOf("https://") < 0 ? "http://" : "https://";
		                String fullpath = request.getRequestURL().indexOf("https://") < 0 ? "http://" : "https://";
		               // fullpath = request.getRequestURL().indexOf("http://") < 0 ? "https://" : "https://";		            
		                fullpath += request.getServerName();
		                fullpath += request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" + request.getServerPort();
		                fullpath += request.getContextPath();
		                fullpath += "/data/upload/rasupload/";
		                model.addAttribute("filePath", fullpath + realFileNm);
		                model.addAttribute("fileName", realFileNm);
		                model.addAttribute("fileSize", sizeInBytes);
	                
	            }
	             
	         }catch (IOException e){
	        	 LOG.debug("error");
	         }
	      

	      }
	      
	      return "/utl/daumeditor/fileupload";
	   }

	
}



