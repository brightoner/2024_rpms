package kr.go.rastech.commons.atchFile.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.go.rastech.commons.atchFile.vo.AtchFileVO;



/**
 * 첨부파일 처리를 위한 SERVICE
 * @author user
 *
 */
public interface AtchFileService {


	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부파일 저장
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param type
	 * @param atchFileVo
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void goInsertAtchFile(@RequestParam("uploadFile") List<MultipartFile> uploadFile, HttpServletRequest request, Model model, String type, AtchFileVO atchFileVo) throws IOException, SQLException, NullPointerException;

	

	/**
	 * 2023. 4. 28
	 * ljk
	 *  vod 용 첨부파일 저장
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param type
	 * @param atchFileVo
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void goInsertVodAtchFile(@RequestParam("uploadFile") List<MultipartFile> uploadFile, HttpServletRequest request, Model model, String type, AtchFileVO atchFileVo) throws IOException, SQLException, NullPointerException;

	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부파일 조회
	 * @param atchFileVo
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public List<Map<String, Object>> selectAtchFileDetail(AtchFileVO atchFileVo) throws SQLException, IOException , NullPointerException;


	/**
	 * 2023. 4. 28
	 * ljk
	 * 특정첨부파일 조회
	 * @param atchFileVo
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public Map<String, Object> getFileDetail(AtchFileVO atchFileVO) throws SQLException, IOException , NullPointerException;


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
