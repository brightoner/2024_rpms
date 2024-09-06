package kr.go.rastech.ptl.content.banner.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.imgFile.service.ImgFileService;
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;
import kr.go.rastech.ptl.content.banner.dao.BannerMngDao;



/**
 * <pre>
 * FileName: BannerMngServiceImpl.java
 * Package :  kr.go.rastech.ptl.mng.banner.service
 * 
 * 
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 12. 11.
 */
@Service
public class BannerMngServiceImpl extends BaseServiceImpl implements BannerMngService {

	@Resource
	private BannerMngDao bannerDao;
	private HashMap<String, Object> hashMap = new HashMap<String, Object>();
	
	  /** ImgFileService */
    @Resource
    private ImgFileService imgFileService;

	/**
	 * <pre>
	 * 게시물 전체 count
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public int selectBannerTotalCount(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {
		
		return bannerDao.selectBannerTotalCount(param);
	}

	/**
	 * <pre>
	 * 게시물 전체 조회
	 *
	 * </pre>   
	 * @author : lwk
	 * @date   : 2023. 3. 28.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectBannerList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return bannerDao.selectBannerList(param);
	}
	
	/**
	 * <pre>
	 * 메인용 팝업 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2019. 12. 11.
	 * @param param
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@Override
	public List<Map<String,Object>> selectMainBannerList(Map<String, Object> param) throws IOException, SQLException , NullPointerException  {

		return bannerDao.selectMainBannerList(param);
	}


	@Override
	public Map<String, Object> selectBannerDtl(Map<String, Object> param) throws NullPointerException, IOException, SQLException {
		return bannerDao.selectBannerDtl(param);
	}

	@Override
	public int insertBannerMng(Map<String, Object> param) throws IOException,SQLException, NullPointerException {
		int seq = bannerDao.selectBannerSeq();
		param.put("banner_seq",seq);
		bannerDao.insertBannerMng(param);
		return seq;
	}

	@Override
	public void updateBannerMng(Map<String, Object> param) throws IOException, SQLException, NullPointerException {
		bannerDao.updateBannerMng(param);
	}
	
	@Override
    public void updateBannerImgFile(Map<String, Object> param ,ImgFileVo imgFileVo) throws SQLException , IOException, NullPointerException {
		
		// 먼저 베너의 이미지키를 update 해주고 해당 첨부파일의 USE_YN을 N으로 셋팅하여 준다.
		bannerDao.updateBannerImgFile(param);    	
		
		imgFileService.deleteAllImgFileInf(imgFileVo);
		
    }

	@Override
	public void deleteBanner(Map<String, Object> param,ImgFileVo imgFileVo) throws IOException, 	SQLException, NullPointerException {
		
		// 먼저 베너를 DELETE 한후   해당 첨부파일의 USE_YN을 N으로 셋팅하여 준다.
		bannerDao.deleteBanner(param);    	
		
		imgFileService.deleteAllImgFileInf(imgFileVo);
		
	}
}
