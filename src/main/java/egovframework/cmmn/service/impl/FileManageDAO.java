package egovframework.cmmn.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.cmmn.service.FileVO;

/**
 * <pre>
 * FileManageDAO interface 구현
 * 
 * </pre>
 * @FileName : FileManageDAO.java
 * @package  : egovframework.cmmn.service.impl
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
@Repository("FileManageDAO")
public class FileManageDAO extends EgovComAbstractDAO {

	/**
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 *
	 * @param fileList
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public String insertFileInfs(List<?> fileList) throws IOException, SQLException , NullPointerException {
		FileVO vo = (FileVO) fileList.get(0);
		String atchFileId = vo.getAtchFileId();

		insert("FileManageDAO.insertFileMaster", vo);

		Iterator<?> iter = fileList.iterator();
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();

			insert("FileManageDAO.insertFileDetail", vo);
		}

		return atchFileId;
	}

	/**
	 * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 *
	 * @param vo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void insertFileInf(FileVO vo) throws IOException, SQLException , NullPointerException {
		insert("FileManageDAO.insertFileMaster", vo);
		insert("FileManageDAO.insertFileDetail", vo);
	}

	/**
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
	 *
	 * @param fileList
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void updateFileInfs(List<?> fileList) throws IOException, SQLException , NullPointerException {
		FileVO vo;
		Iterator<?> iter = fileList.iterator();
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();
			insert("FileManageDAO.insertFileDetail", vo);
		}
	}

	/**
	 * 여러 개의 파일을 삭제한다.
	 *
	 * @param fileList
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteFileInfs(List<?> fileList) throws IOException, SQLException , NullPointerException {
		Iterator<?> iter = fileList.iterator();
		FileVO vo;
		while (iter.hasNext()) {
			vo = (FileVO) iter.next();

			delete("FileManageDAO.deleteFileDetail", vo);
		}
	}

	/**
	 * 하나의 파일을 삭제한다.
	 *
	 * @param fvo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		delete("FileManageDAO.deleteFileDetail", fvo);
	}

	/**
	 * 파일에 대한 목록을 조회한다.
	 *
	 * @param vo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public List<FileVO> selectFileInfs(FileVO vo) throws IOException, SQLException , NullPointerException {
		return (List<FileVO>) list("FileManageDAO.selectFileList", vo);
	}

	/**
	 * 파일 구분자에 대한 최대값을 구한다.
	 *
	 * @param fvo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public int getMaxFileSN(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return (Integer) selectByPk("FileManageDAO.getMaxFileSN", fvo);
	}

	/**
	 * 파일에 대한 상세정보를 조회한다.
	 *
	 * @param fvo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public FileVO selectFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return (FileVO) selectByPk("FileManageDAO.selectFileInf", fvo);
	}

	/**
	 * 전체 파일을 삭제한다.
	 *
	 * @param fvo
	 * @throws IOException, SQLException , NullPointerException
	 */
	public void deleteAllFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		update("FileManageDAO.deleteCOMTNFILE", fvo);
	}

	/**
	 * 파일명 검색에 대한 목록을 조회한다.
	 *
	 * @param vo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public List<FileVO> selectFileListByFileNm(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return (List<FileVO>) list("FileManageDAO.selectFileListByFileNm", fvo);
	}

	/**
	 * 파일명 검색에 대한 목록 전체 건수를 조회한다.
	 *
	 * @param fvo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public int selectFileListCntByFileNm(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return (Integer) selectByPk("FileManageDAO.selectFileListCntByFileNm", fvo);
	}

	/**
	 * 이미지 파일에 대한 목록을 조회한다.
	 *
	 * @param vo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public List<FileVO> selectImageFileList(FileVO vo) throws IOException, SQLException , NullPointerException {
		return (List<FileVO>) list("FileManageDAO.selectImageFileList", vo);
	}

}
