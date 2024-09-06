package kr.go.rastech.commons.vo;

import kr.go.rastech.base.vo.BaseVo;
import kr.go.rastech.commons.page.Navigator;
import kr.go.rastech.commons.page.PagingSupport;

/**
 *
 * <pre>
 * FileName: CommonsVo.java
 * Package : kr.go.ncmiklib.commons.vo
 * 
 * 검색용 Value Object.
 *
 * </pre>
 * 
 * @author : rastech
 * @date : 2023. 3. 9.
 */

public class CommonsVo extends BaseVo implements PagingSupport {

	public static final String ORDER_ASC = "ASC";
	public static final String ORDER_DESC = "DESC";

	private String save_type;
	private int data_seq;
	private String order_sn;
	private int data_num;
	private String data_num_2;

	private String save_dtl_type;

	private String date; // 일자
	private String fileNm; // 파일명
	private String admnId; // 로그인ID
	private String operAuth; // 권한

	private String[] items; // 배열목록

	private String modify_dttm; // 수정일시
	private String modify_id; // 수정자
	private String create_dttm; // 등록일시
	private String create_id; // 등록자
	private String all_search_yn; // 등록자

	/** 검색 PARAM(combo) */
	private String searchCondition;

	/** 검색 PARAM(keyword) */
	private String searchKeyword;

	/** 검색SubFlg */
	private String searchFlg = "";

	/** 정렬 검색 */
	private String orderBy;

	/** 정렬 page */
	private int pageSize = 10;

	  /** TOTAL  */
    private int total_count; 

    /** view  조회수 증가 param  */
    private String upd_yn;

    
  
	
	public String getData_num_2() {
		return data_num_2;
	}

	public void setData_num_2(String data_num_2) {
		this.data_num_2 = data_num_2;
	}

	public int getData_num() {
		return data_num;
	}

	public void setData_num(int data_num) {
		this.data_num = data_num;
	}

	public int getData_seq() {
		return data_seq;
	}

	public void setData_seq(int data_seq) {
		this.data_seq = data_seq;
	}

	/* 페이징관련 맴버변수 */
	private int itemsPerPage = 10;
	private String page = "1";
	private int totalCount = 0;
	private Navigator navi;

	public String getSave_type() {
		return save_type;
	}

	public void setSave_type(String save_type) {
		this.save_type = save_type;
	}

	public String getSave_dtl_type() {
		return save_dtl_type;
	}

	public void setSave_dtl_type(String save_dtl_type) {
		this.save_dtl_type = save_dtl_type;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (totalCount > 0) {
			this.navi = new Navigator(totalCount, this);
		}
	}

	public Navigator getNavi() {
		return navi;
	}

	public void setNavi(Navigator navi) {
		this.navi = navi;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getItemsPerPage() {
		return this.itemsPerPage;
	}

	public String getPage() {
		return this.page;
	}

	public String getPagesPerNav() {
		return "10";
	}

	public static String getOrderAsc() {
		return ORDER_ASC;
	}

	public static String getOrderDesc() {
		return ORDER_DESC;
	}

	public String getAdmnId() {
		return admnId;
	}

	public void setAdmnId(String admnId) {
		this.admnId = admnId;
	}

	public String getOperAuth() {
		return operAuth;
	}

	public void setOperAuth(String operAuth) {
		this.operAuth = operAuth;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return fileNm;
	}

	/**
	 * @param fileNm
	 *            the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String[] getItems() {
		String[] newItem = new String[items.length];
	    System.arraycopy(items, 0, newItem, 0, items.length);
		return newItem;
	}
	

	public void setItems(String[] items) {
	    String[] newItem = new String[items.length];
	    if(newItem.length > 0){
		    System.arraycopy(items, 0, newItem, 0, items.length);
		    this.items = newItem;	
	    }
	}

	public String getModify_dttm() {
		return modify_dttm;
	}

	public void setModify_dttm(String modify_dttm) {
		this.modify_dttm = modify_dttm;
	}

	public String getModify_id() {
		return modify_id;
	}

	public void setModify_id() {
		this.modify_id = getUserId();
	}
	
	public void setModify_id(String modify_id) {
		this.modify_id = modify_id;
	}

	public String getCreate_dttm() {
		return create_dttm;
	}

	public void setCreate_dttm(String create_dttm) {
		this.create_dttm = create_dttm;
	}

	public String getCreate_id() {
		return create_id;
	}

	public void setCreate_id() {
		this.create_id = getUserId();
	}

	public void setCreate_id(String create_id) {
		this.create_id = create_id;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchFlg() {
		return searchFlg;
	}

	public void setSearchFlg(String searchFlg) {
		this.searchFlg = searchFlg;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public String getUpd_yn() {
		return upd_yn;
	}

	public void setUpd_yn(String upd_yn) {
		this.upd_yn = upd_yn;
	}

	public String getAll_search_yn() {
		return all_search_yn;
	}

	public void setAll_search_yn(String all_search_yn) {
		this.all_search_yn = all_search_yn;
	}

	
}
