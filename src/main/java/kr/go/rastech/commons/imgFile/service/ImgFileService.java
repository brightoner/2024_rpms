package kr.go.rastech.commons.imgFile.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.go.rastech.commons.imgFile.vo.ImgFileVo;

/**
 * @Class Name : EgovFileMngService.java
 * @Description : 파일정보의 관리를 위한 서비스 인터페이스
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
public interface ImgFileService {

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @param fvoList
     * @throws SQLException , IOException, NullPointerException
     */  
    public String insertFileInfs(ImgFileVo vo) throws SQLException, IOException , NullPointerException;

    

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     * 
     * @param vo
     * @return
     * @throws SQLException , IOException, NullPointerException
     */
    public List<ImgFileVo> selectImageFileList(ImgFileVo vo) throws SQLException , IOException, NullPointerException;
    
    

    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @param fvo
     * @return
     * @throws SQLException , IOException, NullPointerException
     */
    public ImgFileVo selectImgFileInf(ImgFileVo fvo) throws SQLException , IOException, NullPointerException;
    

    /**
     * 전체 파일을 삭제한다.
     * 
     * @param fvo
     * @throws SQLException , IOException, NullPointerException
     */
    public void deleteAllImgFileInf(ImgFileVo fvo) throws IOException, SQLException;

}
