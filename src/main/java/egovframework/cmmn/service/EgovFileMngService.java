package egovframework.cmmn.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 파일정보의 관리를 위한 Service interface
 * 
 * </pre>
 * @FileName : EgovFileMngService.java
 * @package  : egovframework.cmmn.service
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
public interface EgovFileMngService {

    /**
     * 파일에 대한 목록을 조회한다.
     *
     * @param fvo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public List<FileVO> selectFileInfs(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @param fvo
     * @throws IOException, SQLException , NullPointerException
     */
    public String insertFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @param fvoList
     * @throws IOException, SQLException , NullPointerException
     */
    public String insertFileInfs(List<?> fvoList) throws IOException, SQLException , NullPointerException;

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     *
     * @param fvoList
     * @throws IOException, SQLException , NullPointerException
     */
    public void updateFileInfs(List<?> fvoList) throws IOException, SQLException , NullPointerException;

    /**
     * 여러 개의 파일을 삭제한다.
     *
     * @param fvoList
     * @throws IOException, SQLException , NullPointerException
     */
    public void deleteFileInfs(List<?> fvoList) throws IOException, SQLException , NullPointerException;

    /**
     * 하나의 파일을 삭제한다.
     *
     * @param fvo
     * @throws IOException, SQLException , NullPointerException
     */
    public void deleteFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @param fvo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public FileVO selectFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     *
     * @param fvo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public int getMaxFileSN(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 전체 파일을 삭제한다.
     *
     * @param fvo
     * @throws IOException, SQLException , NullPointerException
     */
    public void deleteAllFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     *
     * @param fvo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws IOException, SQLException , NullPointerException;

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     *
     * @param vo
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public List<FileVO> selectImageFileList(FileVO vo) throws IOException, SQLException , NullPointerException;
}
