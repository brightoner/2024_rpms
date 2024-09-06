package kr.go.rastech.commons.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileUploadUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

	String path = "";
	String fileName = "";

	//  프로젝트 내 지정된 경로에 파일을 저장하는 메소드
	//  DB에는 업로드된 전체 경로명으로만 지정되기 때문에(업로드한 파일 자체는 경로에 저장됨)
	//  fileUpload() 메소드에서 전체 경로를 리턴받아 DB에 경로 그대로 저장
    	public String fileUpload(HttpServletRequest request, MultipartFile upFile, String saveRealPath, String file_nm) {

    	OutputStream out = null;
        PrintWriter printWriter = null;
        
        File parentFolder = null;	// 년도 폴더
        
        if(upFile != null) {
        	try {
        		fileName = upFile.getOriginalFilename();
	            byte[] bytes = upFile.getBytes();
	            String path = String.format("%s/%s", saveRealPath, file_nm);
	            
	            File file = new File(path);
	            //파일명이 중복으로 존재할 경우
                if (!file.exists()) {
                	
                	parentFolder = file.getParentFile();
                	
                	if(!parentFolder.exists()) {	// 해당년도 폴더가 없으면 (업무구분까지는 폴더가 있어야 한다.)
                		parentFolder.mkdirs();		// 년도 폴더 생성
                	}
                	out = new FileOutputStream(file);
                	out.write(bytes);
                }
	        } catch (IOException eio) {
	        	logger.error("{}", eio);
	        } finally {
	            try {
	                if (out != null) {
	                    out.close();
	                }
	                if (printWriter != null) {
	                    printWriter.close();
	                }
	            } catch (IOException eclose) {
	            	logger.error("{}", eclose);
	            }
	        }
        }

        return saveRealPath;
    }

}
