package kr.go.rastech.commons.atchFile.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.go.rastech.commons.atchFile.vo.AtchFileVO;


/**
 * 첨부파일 처리를 위한 DAO
 * @author user
 *
 */
@Repository
public interface AtchFileDao {

	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부파일 insert를 위한 file_id 채번
	 * @param vo
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public int getFileId(AtchFileVO atchFileVo) throws IOException, SQLException , NullPointerException;
	

	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부 파일 저장
	 * @param atchFileVO
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void insetAttFile(AtchFileVO atchFileVO) throws IOException, SQLException, NullPointerException;


	/**
	 * 2023. 4. 28
	 * ljk
	 * 전체첨부파일 조회
	 * @param atchFileVO
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public List<Map<String, Object>> selectAtchFileDetail(AtchFileVO atchFileVo) throws IOException, SQLException , NullPointerException;
	

	/**
	 * 2023. 4. 28
	 * ljk
	 * 특정 첨부파일 조회
	 * @param atchFileVO
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public Map<String, Object> getFileDetail(AtchFileVO atchFileVO) throws IOException, SQLException , NullPointerException;


	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부파일 수정시 특정 첨부파일 삭제
	 * @param atchFileVO
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void deleteAttFile(AtchFileVO atchFileVO) throws IOException, SQLException, NullPointerException;


}
