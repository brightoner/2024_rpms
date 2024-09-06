package kr.go.rastech.commons.imgFile.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.ibatis.type.Alias;



/**
 * <pre>
 * 
 * 파일정보 처리를 위한 VO 
 * </pre>
 * @FileName : FileVO.java
 * @package  : egovframework.cmmn.service
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
@Alias("imgFileVo")
public class ImgFileVo implements Serializable {

   
	private static final long serialVersionUID = 5036689214592440633L;
	/**
     * 첨부파일 아이디
     */
    public String atch_img_id = "";
    /**
     * 생성일자
     */
    public String reg_date = "";
    
    /**
     * 파일확장자
     */
    public String file_extsn = "";
    /**
     * 파일크기
     */
    public String file_size = "";
    /**
     * 파일연번
     */
    public String file_sn = "";
    
    /**
     * 원파일명
     */
    public String orignl_file_nm = "";
 
    

    public String use_yn = "";
    /**
     * 파일 blob
     */
    public byte[] file_byte = null;

    
    
    
    
	public String getAtch_img_id() {
		return atch_img_id;
	}

	public void setAtch_img_id(String atch_img_id) {
		this.atch_img_id = atch_img_id;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getFile_extsn() {
		return file_extsn;
	}

	public void setFile_extsn(String file_extsn) {
		this.file_extsn = file_extsn;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getFile_sn() {
		return file_sn;
	}

	public void setFile_sn(String file_sn) {
		this.file_sn = file_sn;
	}

	public String getOrignl_file_nm() {
		return orignl_file_nm;
	}

	public void setOrignl_file_nm(String orignl_file_nm) {
		this.orignl_file_nm = orignl_file_nm;
	}


	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public byte[] getFile_byte() {
		return file_byte;
	}

	public void setFile_byte(byte[] file_byte) {
		this.file_byte = file_byte;
	}
   
    
    
}
