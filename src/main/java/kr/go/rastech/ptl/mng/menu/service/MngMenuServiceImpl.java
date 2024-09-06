package kr.go.rastech.ptl.mng.menu.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.menu.dao.MenuDao;
import kr.go.rastech.ptl.mng.menu.dao.MngMenuDao;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuRolVo;
import kr.go.rastech.ptl.mng.menu.vo.MngMenuVo;


/**
 * <pre>
 * FileName: MngMenuServiceImpl.java
 * Package : kr.go.ncmiklib.ptl.mng.menu.service
 * 
 * mng Menu 관리 ServiceImpl
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 2. 16.
 */
@Repository
public class MngMenuServiceImpl extends BaseServiceImpl implements MngMenuService {

	@Resource
	private MenuDao menuDao;

	@Resource
	private MngMenuDao mngMenuDao;
	private HashMap<String, Object> valueMap = new HashMap<String, Object>();


	/**
	 * <pre>
	 * 메뉴관리 - 메뉴조회
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 11.
	 * @param mngMenuVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	public List<MngMenuVo> readMenuList(MngMenuVo mngMenuVo)  throws IOException, SQLException , NullPointerException{
		return mngMenuDao.readMenuList(mngMenuVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#insertMenu(kr.go.ncmiklib.ptl.mng.menu.vo.MngMenuVo)
	 */
	@Override
	public void insertMenu(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException {
		mngMenuDao.insertMenu(mngMenuVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#updateMenu(kr.go.ncmiklib.ptl.mng.menu.vo.MngMenuVo)
	 */
	@Override
	public void updateMenu(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException {
		mngMenuDao.updateMenu(mngMenuVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#deleteMenu(kr.go.ncmiklib.ptl.mng.menu.vo.MngMenuVo)
	 */
	@Override
	public void deleteMenu(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException {
		//하위메뉴삭제여부
		if(!StringUtils.isBlank(mngMenuVo.getMenu_id()) && StringUtils.isBlank(mngMenuVo.getMenu_prts_id()) ){
			mngMenuVo.setDel_yn("Y");
		}else{
			mngMenuVo.setDel_yn("N");
		}
		mngMenuDao.deleteMenu(mngMenuVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#deleteMenuRol(kr.go.ncmiklib.ptl.mng.menu.vo.MngMenuVo)
	 */
	@Override
	public int deleteMenuRol(MngMenuRolVo mngMenuRolVo) throws IOException, SQLException , NullPointerException {
		return mngMenuDao.deleteMenuRol(mngMenuRolVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#insertMenuRol(kr.go.ncmiklib.ptl.mng.menu.vo.MngMenuVo)
	 */
	@Override
	public void insertMenuRol(MngMenuRolVo mngMenuRolVo) throws IOException, SQLException , NullPointerException {
		mngMenuDao.insertMenuRol(mngMenuRolVo);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#readMenuRolList(java.lang.String)
	 */
	@Override
	public List<MngMenuVo> readMenuRolList(String menu_id) throws IOException, SQLException , NullPointerException {
		return mngMenuDao.readMenuRolList(menu_id);
	}


	/* (비Javadoc)
	 * @see kr.go.ncmiklib.ptl.mng.menu.service.MngMenuService#mergeMenuRol(kr.go.ncmiklib.ptl.mng.menu.vo.MngMenuRolVo)
	 */
	@Override
	public void mergeMenuRol(MngMenuRolVo mngMenuRolVo) throws IOException, SQLException , NullPointerException {
		mngMenuDao.mergeMenuRol(mngMenuRolVo);
	}


	@Override
	public void updateMenuDtl(MngMenuVo mngMenuVo) throws IOException, SQLException , NullPointerException {
		mngMenuDao.updateMenuDtl(mngMenuVo);
	}
}
