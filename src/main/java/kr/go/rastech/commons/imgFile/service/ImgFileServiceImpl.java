package kr.go.rastech.commons.imgFile.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.imgFile.dao.ImgFileDao;
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;

/**
 * @Class Name : EgovFileMngServiceImpl.java
 * @Description : 파일정보의 관리를 위한 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 25.     이삼섭    최초생성
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 25.
 * @version
 * @see
 *
 */
@Service
public class ImgFileServiceImpl extends BaseServiceImpl implements ImgFileService {

    @Resource
    private ImgFileDao imgFileDao;

    public static final Logger LOGGER = Logger.getLogger(ImgFileServiceImpl.class.getName());

  
    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @see ncmik.ptl.cmm.service.EgovFileMngService#insertFileInfs(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs(ImgFileVo vo) throws SQLException, IOException , NullPointerException {
		String atchFileId = "";
		
						
		int mCnt = imgFileDao.insertFileMaster(vo);
		int dCnt = imgFileDao.insertFileDetail(vo);
		
		atchFileId = vo.getAtch_img_id();
		
		
		return atchFileId;
    }


    /**
     * 이미지 파일에 대한 목록을 조회한다.
     * 
     * @see ncmik.ptl.cmm.service.EgovFileMngService#selectImageFileList(ncmik.ptl.cmm.service.FileVO)
     */
    public List<ImgFileVo> selectImageFileList(ImgFileVo vo) throws SQLException , IOException, NullPointerException {
    	return imgFileDao.selectImageFileList(vo);
    }
   
    
    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @see ncmik.ptl.cmm.service.EgovFileMngService#selectFileInf(ncmik.ptl.cmm.service.FileVO)
     */
    public ImgFileVo selectImgFileInf(ImgFileVo vo) throws SQLException , IOException, NullPointerException {
    	return imgFileDao.selectImgFileInf(vo);
    }
    
    /**
     * 전체 파일을 삭제한다.
     * 
     * @see ncmik.ptl.cmm.service.EgovFileMngService#deleteAllFileInf(ncmik.ptl.cmm.service.FileVO)
     */
    public void deleteAllImgFileInf(ImgFileVo fvo) throws IOException, SQLException {
    	imgFileDao.deleteAllImgFileInf(fvo);
    }
}
