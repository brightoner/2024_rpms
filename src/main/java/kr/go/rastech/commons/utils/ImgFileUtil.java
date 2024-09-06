package kr.go.rastech.commons.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;


/**
 * @Class Name  : EgovFileMngUtil.java
 * @Description : 메시지 처리 관련 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.02.13       이삼섭                  최초 생성
 *   2011.08.09       서준식                  utl.fcc패키지와 Dependency제거를 위해 getTimeStamp()메서드 추가
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 02. 13
 * @version 1.0
 * @see
 *
 */
@Component("ImgFileUtil")
public class ImgFileUtil {

    public static final int BUFF_SIZE = 2048;



    private static final Logger LOG = Logger.getLogger(ImgFileUtil.class.getName());

    
    /**
     * 2011.08.09
     * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    private static String getTimeStamp() {

	String rtnStr = null;

	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
	String pattern = "yyyyMMddhhmmssSSS";

	try {
	    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
	    Timestamp ts = new Timestamp(System.currentTimeMillis());

	    rtnStr = sdfCurrent.format(ts.getTime());
	}catch(IllegalArgumentException e){
		LOG.debug("IGNORE : getTimeStamp IllegalArgumentException ERROR!!!!!!!!!!!!!!!!");
	}

	return rtnStr;
    }
    
    /**
     * 첨부파일을 디렉토리에 저장하지않고 DB의 BLOB 로 저장하도록 한다.
     *
     * @param files
     * @return
     * @throws SQLException , IOException, NullPointerException
     */
    public List<ImgFileVo> parseBLOBFileInf(Map<String, MultipartFile> files, String keyStr, int fileKeyParam, String atchFileId, String storePath) throws SQLException, IOException , NullPointerException,FdlException {
		int fileKey = fileKeyParam;
	
		
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<ImgFileVo> result  = new ArrayList<ImgFileVo>();
		ImgFileVo fvo;
	
		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();
	
		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
	
		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) {
			continue;
		    }
		    ////------------------------------------
	
		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileExt = orginFileName.substring(index + 1);
		    String newName = keyStr + getTimeStamp() + fileKey;
		    long s_size = file.getSize();
	
		    fvo = new ImgFileVo();
		    fvo.setFile_extsn(fileExt);
		 //   fvo.setFileStreCours(storePathString);
		    fvo.setFile_size(Long.toString(s_size));
		    fvo.setOrignl_file_nm(orginFileName);
		    
		//    fvo.setAtchFileId(atchFileIdString);
		    fvo.setFile_sn (String.valueOf(fileKey));
		    fvo.setFile_byte(file.getBytes());
	
		    //writeFile(file, newName, storePathString);
		    result.add(fvo);
	
		    fileKey++;
		}
	
		return result;
    }
}
