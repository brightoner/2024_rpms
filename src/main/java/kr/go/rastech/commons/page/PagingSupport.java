package kr.go.rastech.commons.page;

public interface PagingSupport {
    /**
     * 페이지번호 조회.
     *
     * @return 페이지 번호
     */
    public String getPage();

    /**
     * 페이지당 목록 갯수 조회.
     *
     * @return 페이지당 목록 갯수
     */
    public int getItemsPerPage();

    /**
     * 페이지 네비게이션에 표시할 페이지번호 갯수 조회.
     *
     * @return 페이지 네비게이션에 표시할 페이지번호 갯수
     */
    public String getPagesPerNav();
}
