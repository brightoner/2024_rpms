package kr.go.rastech.commons.atchFile.vo;

import java.io.Serializable;


/**
 * 공통 첨부파일 처리를 위한 VO
 * NCMIK.PTL_ATCHFILE 테이블
 * @author user
 *
 */
public class AtchFileVO implements Serializable{

	private static final long serialVersionUID = 104675462421805318L;

	// 공통 첨부파일 관련 (NCMIK.PTL_ATCHFILE 테이블)
	private String fileGroup;	// 파일그룹번호
    private int fileId;			// 파일일련번호
    private int subFileId;		// 파일일련번호 내 파일순서
    private String fileNm;		// 파일명
    private String filePath;	// 파일저장경로
    private String orgnFileNm;	// 원본파일명
    private String fileExt;		// 확장자
    private int fileSize;		// 파일크기
    private String delYn;		// 삭제여부
    private String createDttm;		// 등록일시
    private String fileGb;		// 첨부파일 구분(1 : 공고, 2 : 연구과제신청, 3 : 선정결과처리, 4 : 수정사업계획서, 5 : 과제협약, 99: 기타)
	
    public String getFileGroup() {
		return fileGroup;
	}
	public void setFileGroup(String fileGroup) {
		this.fileGroup = fileGroup;
	}
	
	public int getSubFileId() {
		return subFileId;
	}
	public void setSubFileId(int subFileId) {
		this.subFileId = subFileId;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOrgnFileNm() {
		return orgnFileNm;
	}
	public void setOrgnFileNm(String orgnFileNm) {
		this.orgnFileNm = orgnFileNm;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getFileGb() {
		return fileGb;
	}
	public void setFileGb(String fileGb) {
		this.fileGb = fileGb;
	}
	public String getCreateDttm() {
		return createDttm;
	}
	public void setCreateDttm(String createDttm) {
		this.createDttm = createDttm;
	}
	
	
    
   



}
