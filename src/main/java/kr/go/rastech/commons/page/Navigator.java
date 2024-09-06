package kr.go.rastech.commons.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 게시판과 같이 페이지 네비게이션이 필요한 목록들에서 네비게이션에 필요한 수치들을 계산해주는 지원클래스.
 *
 * @author citywork
 */
public class Navigator {
    private Log logger = LogFactory.getLog(this.getClass());

    /** Default 첫 페이지 번호 */
    public static final int DEFAULT_FIRST_PAGE = 1;
    /** 페이지당 Default 아이템(레코드) 갯수 */
    public static final int DEFAULT_ITEMS_PER_PAGE = 10;
    /** 네비게이션 당 표시할 Default페이지 갯수 */
    public static final int DEFAULT_PAGES_PER_NAV = 10;
    /** 페이지에 표시할 최소 아이템(레코드) 갯수 */
    public static final int MIN_ITEMS_PER_PAGE = 1;
    /** 페이지에 표시할 최대 아이템(레코드) 개수 */
    public static final int MAX_ITEMS_PER_PAGE = 100;

    private int totalCnt;
    private int itemsPerPage;
    private int pagesPerNav;

    private int pageNum;
    private int lastPageNum;
    private int prevPageNum;
    private int nextPageNum;
    private int firstPageInNav;
    private int lastPageInNav;
    private PagingSupport page;
    @SuppressWarnings("rawtypes")
    private List pageListInNav;

    public int getFirstItem() {
        return (this.pageNum - 1) * (this.itemsPerPage + 1);
    }

    public int getLastItem() {
        return this.pageNum * this.itemsPerPage;
    }

    /**
     * 페이지 네비게이션을 지원하는 네비게이터 생성자.
     *
     * @param totalCnt 총 데이터 레코드 카운트
     * @param page 페이징 요청 객체
     * @throws NullPointerException (page == null)
     * @throws IllegalArgumentException (totalCnt < 1)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Navigator(int totalCnt, PagingSupport page) throws NullPointerException, IllegalArgumentException {
        // pageObject
        if(page == null) {
            throw new NullPointerException("PagingSupport is null");
        } else {
            this.page = page;
        }

        // totalCnt
        if(totalCnt < 1) {
            throw new IllegalArgumentException("totalCnt(" + totalCnt + ") is lower than 1");
        } else {
            this.totalCnt = totalCnt;
        }

        // itemsPerPage
        this.itemsPerPage = page.getItemsPerPage();
        if(this.itemsPerPage < Navigator.MIN_ITEMS_PER_PAGE) {
            if(logger.isWarnEnabled()) { logger.warn("itemsPerPage(" + this.itemsPerPage + ") is lower than MIN_ITEMS_PER_PAGE. Use default value [" + Navigator.DEFAULT_ITEMS_PER_PAGE + "]"); }
        } else if(this.itemsPerPage > Navigator.MAX_ITEMS_PER_PAGE) {
            if(logger.isWarnEnabled()) { logger.warn("itemsPerPage(" + this.itemsPerPage + ") is higher than MAX_ITEMS_PER_PAGE. Use default value [" + Navigator.DEFAULT_ITEMS_PER_PAGE + "]"); }
        }

        // pagesPerNav
        this.pagesPerNav = NumberUtils.toInt(page.getPagesPerNav());
        if(pagesPerNav < 1) {
            if(logger.isWarnEnabled()) { logger.warn("pagesPerNav(" + this.pagesPerNav + ") is lower than 1. Use default value [" + Navigator.DEFAULT_PAGES_PER_NAV + "]"); }
            this.pagesPerNav = Navigator.DEFAULT_PAGES_PER_NAV;
        }

        // lastPageNum
        this.lastPageNum = (int) Math.ceil(this.totalCnt / (double) itemsPerPage);

        // pageNum
        this.pageNum = NumberUtils.toInt(page.getPage(), 1);
        if(this.pageNum < 1) {
            if(logger.isWarnEnabled()) { logger.warn("pageNum(" + this.pageNum + ") is lower than 1. Use default value [" + Navigator.DEFAULT_FIRST_PAGE + "]"); }
            this.pageNum = Navigator.DEFAULT_FIRST_PAGE;
        } else if (this.pageNum > this.lastPageNum) {
            if(logger.isWarnEnabled()) { logger.warn("pageNum(" + this.pageNum + ") is exceed lastPageNum(" + this.lastPageNum + "). Use lastPageNum [" + this.lastPageNum + "]"); }
            this.pageNum = this.lastPageNum;
        }

        this.prevPageNum = this.pageNum - 1;
        this.nextPageNum = this.pageNum + 1;

        this.firstPageInNav = (((pageNum - 1) / pagesPerNav) * pagesPerNav) + 1;
        this.lastPageInNav = firstPageInNav + (pagesPerNav - 1);
        if(lastPageInNav > lastPageNum) {
            lastPageInNav = lastPageNum;
        }
        this.pageListInNav = new ArrayList();
        for(int i = this.firstPageInNav ; i <= this.lastPageInNav ; ++i) {
            pageListInNav.add(new Integer(i));
        }
    }

    /**
     * 아이템(레코드) 총계를 조회.
     *
     * @return 아이템(레코드) 총계
     */
    public int getTotalCnt() {
        return this.totalCnt;
    }

    /**
     * 페이지당 표시할 아이템(레코드) 갯수를 조회.
     *
     * @return 페이지당 표시할 아이템(레코드) 갯수
     */
    public int getItemsPerPage() {
        return this.itemsPerPage;
    }

    /**
     * 첫 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부를 확인.
     *
     * 현재 첫페이지가 아닌경우 항상 true를 반환
     *
     * @return 첫 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부
     */
    public boolean getShowFirstPageNav() {
        if(pageNum == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 마지막 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부를 확인.
     *
     * 현재 마지막 페이지가 아닌경우 항상 true를 반환
     *
     * @return 마지막 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부
     */
    public boolean getShowLastPageNav() {
        if(pageNum == lastPageNum) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 마지막 페이지 번호를 확인.
     *
     * @return 마지막 페이지 번호
     */
    public int getLastPageNum() {
        return this.lastPageNum;
    }

    /**
     * 현재 페이지 번호를 확인.
     *
     * @return 현재 페이지 번호
     */
    public int getPageNum() {
        return this.pageNum;
    }

    /**
     * 목록에 표시할 레코드번호를 감소순으로 표시할 경우 첫번째 레코드가 가질 항목번호를 확인.
     *
     * @return
     */
    public int getFirstRowNumDesc() {
        return this.totalCnt - ((this.pageNum - 1) * this.itemsPerPage);
    }

    /**
     * 목록에 표시할 레코드번호를 증가순으로 표시할 경우 첫번째 레코드가 가질 항목번호를 확인.
     *
     * @return
     */
    public int getFirstRowNumAsc() {
        return (this.pageNum - 1) * this.itemsPerPage;
    }

    /**
     * 아이템(레코드) 목록에서 첫번째 아이템의 항목번호를 확인.
     *
     * @return 아이템(레코드) 목록에서 첫번째 아이템의 항목번호
     */
    public int getFirstRowNum() {
        return (this.pageNum - 1) * this.itemsPerPage;
    }

    public int getLastRowNum() {
        return this.getFirstRowNum() + (this.itemsPerPage - 1);
    }

    /**
     * 이전 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부를 확인.
     *
     * 이전 페이지가 존재하는 경우 항상 true를 반환
     *
     * @return 이전 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부
     */
    public boolean getPrevPageNavFlag() {
        if(prevPageNum < 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 이전 페이지번호를 확인.
     *
     * @return 이전 페이지 번호
     */
    public int getPrevPageNum() {
        return this.prevPageNum;
    }

    /**
     * 다음 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부를 확인.
     *
     * 다음 페이지가 존재하는 경우 항상 true를 반환
     *
     * @return 다음 페이지로 이동하는 네비게이션 버튼을 보여줘야하는지 여부
     */
    public boolean getNextPageNavFlag() {
        if(nextPageNum > lastPageNum) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 다음 페이지 번호를 확인.
     *
     * @return 다음 페잊 번호
     */
    public int getNextPageNum() {
        return nextPageNum;
    }

    /**
     * 네비게이션 목록의 첫번째 페이지 번호를 확인.
     *
     * @return 네비게이션 목록의 첫번째 페이지 번호
     */
    public int getFirstPageInNav() {
        return firstPageInNav;
    }

    /**
     * 네비게이션 목록의 마지막 페이지 번호를 확인.
     *
     * @return 네비게이션 목록의 마지막 페이지 번호
     */
    public int getLastPageInNav() {
        return lastPageInNav;
    }

    /**
     * 네비게이션 목록의 페이지 번호들의 리스트를 반환.
     *
     * @return 네비게이션 목록의 페이지 번호들의 리스트
     */
    @SuppressWarnings("rawtypes")
    public List getPageListInNav() {
        return this.pageListInNav;
    }

    protected PagingSupport getPagingSupport() {
        return this.page;
    }
}