package kr.go.rastech.commons.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * zip 파일 처리를 수행한다.
 * 
 * </pre>
 * @FileName : ZipUtil.java
 * @package  : kr.go.ncmiklib.commons.utils
 * @author   : wonki0138
 * @date     : 2018. 4. 3.
 * 
 */
public class FileConvUtil {

	
	/**
	 * <pre>
	 * Multipart  - > File
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 4. 
	 * @param multipart
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException   
	 */
	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File( multipart.getOriginalFilename()); 
		multipart.transferTo(convFile); 
		return convFile;
	}    

/*	public MultipartFile fileToMultipartFile(File file) {
	    FileItem fileItem = null;
	    try {
	        fileItem = new DiskFileItem(null, Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
	    } catch (IOException e1) {
	    	Logger.getLogger(CryptoUtil.class).debug("err fileToMultipartFile");
	    }
	    
	    InputStream inputStream = null;
	    OutputStream outputStream = null;
	    try {
	        inputStream = new FileInputStream(file);
	        outputStream = fileItem.getOutputStream();
	        IOUtils.copy(inputStream, outputStream);
	    } catch (FileNotFoundException e) {
	    	Logger.getLogger(CryptoUtil.class).debug("err fileToMultipartFile");
	    } catch (IOException e) {
	    	Logger.getLogger(CryptoUtil.class).debug("err fileToMultipartFile");
	    }finally{
	    	try {
				inputStream.close();
			  	outputStream.close();
			} catch (IOException e) {
				Logger.getLogger(CryptoUtil.class).debug("err fileToMultipartFile close()");
			}
	  
	    }
	    
	    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
	    
	    return multipartFile;
	}
*/
	
}
