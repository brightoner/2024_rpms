package kr.go.rastech.ptl.mng.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.mng.user.dao.MngUserDao;
import kr.go.rastech.ptl.mng.user.vo.MngUserAuthVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserClsVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserLogVo;
import kr.go.rastech.ptl.mng.user.vo.PTLLoginVo;


/**
 * <pre>
 * FileName: MngUserServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.user.service
 * 사용자 관리 ServiceImpl
 * 
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 24.
 */
@Service
public class MngUserServiceImpl extends BaseServiceImpl implements MngUserService {

	@Resource
	private MngUserDao mngUserDao;

	
	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#selectUserCnt(kr.go.ncmiklib.ptl.mng.user.vo.PTLLoginVo)
	 */
	@Override
	public int selectUserCnt(Map<String, Object> param) throws IOException, SQLException , NullPointerException {
		return mngUserDao.selectUserCnt(param);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#updateUser(kr.go.ncmiklib.ptl.mng.user.vo.PTLLoginVo)
	 */
	@Override
	public void updateUser(PTLLoginVo pTLLoginVo) throws IOException, SQLException , NullPointerException {
		mngUserDao.updateUser(pTLLoginVo);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#insertUserCls(kr.go.ncmiklib.ptl.mng.user.vo.MngUserClsVo)
	 */
	@Override
	public void insertUserCls(MngUserClsVo mngUserClsVo) throws IOException, SQLException , NullPointerException {
		mngUserDao.insertUserCls(mngUserClsVo);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#selectUserCls(java.lang.String)
	 */
	@Override
	public List<MngUserClsVo> selectUserCls(String loginid) throws IOException, SQLException , NullPointerException {
		return mngUserDao.selectUserCls(loginid);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#deleteUserCls(java.lang.String)
	 */
	@Override
	public void deleteUserCls(String loginid) throws IOException, SQLException , NullPointerException {
		mngUserDao.deleteUserCls(loginid);
	}
	@Override
	public List<PTLLoginVo> selectUserList(Map<String, Object> param) throws IOException, SQLException , NullPointerException {
		return mngUserDao.selectUserList(param);
	}
	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#deleteUserAuth(kr.go.ncmiklib.ptl.mng.user.vo.MngUserAuthVo)
	 */
	@Override
	public void deleteUserAuth(String loginid) throws IOException, SQLException , NullPointerException {
		mngUserDao.deleteUserAuth(loginid);
	}
	
	/**
	 * <pre>
	 * 회원관리 - BJ 권한 삭제
	 *
	 * </pre>
	 * @author : ljk
	 * @date   : 2023. 6. 27.
	 * @param mngUserAuthVo
	 * @throws IOException, SQLException , NullPointerException
	 */
	@Override
	public void deleteBjAuth(MngUserAuthVo mngUserAuthVo) throws IOException, SQLException , NullPointerException {
		mngUserDao.deleteBjAuth(mngUserAuthVo);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#insertUserAuth(kr.go.ncmiklib.ptl.mng.user.vo.MngUserAuthVo)
	 */
	@Override
	public void insertUserAuth(MngUserAuthVo mngUserAuthVo) throws IOException, SQLException , NullPointerException {
		mngUserDao.insertUserAuth(mngUserAuthVo);
	}
	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#selectUserAuth(java.lang.String)
	 */
	@Override
	public List<MngUserAuthVo> selectUserAuth(String loginid) throws IOException, SQLException , NullPointerException {
		return mngUserDao.selectUserAuth(loginid);
	}

	/* (non-Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#selectUserLogList(kr.go.ncmiklib.ptl.mng.user.vo.MngUserLogVo)
	 */
	@Override
	public List<MngUserLogVo> selectUserLogList(MngUserLogVo mngUserLogVo) throws IOException, SQLException , NullPointerException {
		return mngUserDao.selectUserLogList(mngUserLogVo);
	}
	/* (non-Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.user.service.MngUserService#selectUserLogMap(kr.go.ncmiklib.ptl.mng.user.vo.MngUserLogVo)
	 */
	@Override
	public List<Map<String, String>> selectUserLogMap(MngUserLogVo mngUserLogVo) throws IOException, SQLException , NullPointerException {
		return mngUserDao.selectUserLogMap(mngUserLogVo);
	}
	@Override
	public void updateChgPwd(PTLLoginVo pTLLoginVo) throws IOException, SQLException , NullPointerException {
		mngUserDao.updateChgPwd(pTLLoginVo);
	}
	
	
}
