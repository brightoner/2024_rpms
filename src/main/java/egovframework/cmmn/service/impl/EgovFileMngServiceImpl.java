package egovframework.cmmn.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.cmmn.service.EgovFileMngService;
import egovframework.cmmn.service.FileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * <pre>
 * 파일정보 관리 ServiceImpl 로직 구현
 * 
 * </pre>
 * @FileName : EgovFileMngServiceImpl.java
 * @package  : egovframework.cmmn.service.impl
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
@Service("EgovFileMngService")
public class EgovFileMngServiceImpl extends EgovAbstractServiceImpl implements EgovFileMngService {

	@Resource(name = "FileManageDAO")
	private FileManageDAO fileMngDAO;

	/**
	 * <pre>
	 * 여러 개의 파일을 삭제한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvoList
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void deleteFileInfs(List<?> fvoList) throws IOException, SQLException , NullPointerException {
		fileMngDAO.deleteFileInfs(fvoList);
	}

	/**
	 * <pre>
	 * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public String insertFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		String atchFileId = fvo.getAtchFileId();

		fileMngDAO.insertFileInf(fvo);

		return atchFileId;
	}

	/**
	 * <pre>
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvoList
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public String insertFileInfs(List<?> fvoList) throws IOException, SQLException , NullPointerException {
		String atchFileId = "";

		if (fvoList.size() != 0) {
			atchFileId = fileMngDAO.insertFileInfs(fvoList);
		}
		if (atchFileId.equals("")) {
			atchFileId = null;
		}
		return atchFileId;
	}

	/**
	 * <pre>
	 * 파일에 대한 목록을 조회한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public List<FileVO> selectFileInfs(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return fileMngDAO.selectFileInfs(fvo);
	}

	/**
	 * <pre>
	 * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvoList
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void updateFileInfs(List<?> fvoList) throws IOException, SQLException , NullPointerException {
		//Delete & Insert
		fileMngDAO.updateFileInfs(fvoList);
	}

	/**
	 * <pre>
	 * 하나의 파일을 삭제한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void deleteFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		fileMngDAO.deleteFileInf(fvo);
	}

	/**
	 * <pre>
	 * 파일에 대한 상세정보를 조회한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public FileVO selectFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return fileMngDAO.selectFileInf(fvo);
	}

	/**
	 * <pre>
	 * 파일 구분자에 대한 최대값을 구한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public int getMaxFileSN(FileVO fvo) throws IOException, SQLException , NullPointerException {
		return fileMngDAO.getMaxFileSN(fvo);
	}

	/**
	 * <pre>
	 * 전체 파일을 삭제한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void deleteAllFileInf(FileVO fvo) throws IOException, SQLException , NullPointerException {
		fileMngDAO.deleteAllFileInf(fvo);
	}

	/**
	 * <pre>
	 * 파일명 검색에 대한 목록을 조회한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param fvo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws IOException, SQLException , NullPointerException {
		List<FileVO> result = fileMngDAO.selectFileListByFileNm(fvo);
		int cnt = fileMngDAO.selectFileListCntByFileNm(fvo);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}
	
	/**
	 * <pre>
	 * 이미지 파일에 대한 목록을 조회한다.
	 *
	 * </pre>
	 * @author : user
	 * @date   : 2018. 7. 10. 
	 * @param vo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public List<FileVO> selectImageFileList(FileVO vo) throws IOException, SQLException , NullPointerException {
		return fileMngDAO.selectImageFileList(vo);
	}

}
