package kr.go.rastech.ptl.mng.sys.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.ptl.mng.sys.dao.SysMntrDao;
import kr.go.rastech.ptl.mng.sys.vo.SysMntrVo;




/**
 * <pre>
 * FileName: sysServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.sys.service
 * 
 * 시스템관리 서비스 구현
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 22.
 */
@Service
public class SysMntrServiceImpl extends BaseServiceImpl implements SysMntrService {

	@Resource
	SysMntrDao sysMntrDao;
	
	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.sys.service.SysService#insertSysMntr(kr.go.ncmiklib.ptl.mng.sys.vo.SysVo)
	 */
	@Override
	public void insertSysMntr(Map<String, Object> map) throws IOException, SQLException , NullPointerException {
		sysMntrDao.insertSysMntr(map);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.sys.service.SysService#selectSysMntrList(kr.go.ncmiklib.ptl.mng.sys.vo.SysVo)
	 */
	@Override
	public List<Map<String,Object>> selectSysMntrList(SysMntrVo sysVo) throws IOException, SQLException , NullPointerException {
		return sysMntrDao.selectSysMntrList(sysVo);
	}

	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.sys.service.SysMntrService#updateSysMntr(kr.go.ncmiklib.ptl.mng.sys.vo.SysMntrVo)
	 */
	@Override
	public void updateSysMntr(SysMntrVo sysMntrVo) throws IOException, SQLException , NullPointerException {
		sysMntrDao.updateSysMntr(sysMntrVo);
	}

	@Override
	public List<SysMntrVo> selectListSch(SysMntrVo sysVo) throws IOException, SQLException , NullPointerException {
		return sysMntrDao.selectListSch(sysVo);
	}

	@Override
	public void saveSch(SysMntrVo sysMntrVo) throws IOException, SQLException , NullPointerException {
		 sysMntrDao.saveSch(sysMntrVo);
	}

	
}
