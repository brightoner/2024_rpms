package kr.go.rastech.ptl.mng.lang.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.go.rastech.ptl.mng.lang.vo.MngLangVo;


/**
 * <pre>
 * FileName: MngAuthDao.java
 * Package : kr.go.ncmiklib.ptl.mng.auth.dao
 * 
 * 다국어 관리 Dao
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 27.
 */
@Repository
public interface MngLangDao {

	public void insertLang(MngLangVo mngLangVo) throws SQLException;

	public void updateLang(MngLangVo mngLangVo) throws SQLException;

	public void deleteLang(MngLangVo mngLangVo) throws SQLException;

	public List<MngLangVo> selectLang(MngLangVo mngLangVo) throws SQLException;


}
