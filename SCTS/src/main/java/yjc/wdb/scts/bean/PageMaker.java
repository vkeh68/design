package yjc.wdb.scts.bean;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
/* 페이징 처리용 클래스 */

public class PageMaker {
	private int totalCount; /* 전체 데이터 개수  */
	private int startPage; /* 시작 페이지 번호 11~20이라면 11 */
	private int endPage; /* 끝 페이지 번호 11~20이라면 20 */
	private boolean prev;
	private boolean next;
	private int displayPageNum = 10; /* 페이지 번호 개수 */
	private PageVO cri;

	public void setCri(PageVO cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	
	private void calcData(){
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}
	
	/* 20개 이상의 페이지가 올 경우 제대로 처리하지 못해서 해결. */
	public String makeSearch(int page){
		UriComponents uriComponents =	
				UriComponentsBuilder.newInstance()
				.queryParam("page", page) 
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((PageVO)cri).getSearchType())
				.queryParam("keyword", ((PageVO)cri).getKeyword())
				.queryParam("startAmount", ((PageVO)cri).getStartAmount())
				.queryParam("endAmount", ((PageVO)cri).getEndAmount())
				.queryParam("check", ((PageVO)cri).getCheck())
				.queryParam("msg", ((PageVO)cri).isMsg())
				.build();
		return uriComponents.toUriString();
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public PageVO getCri() {
		return cri;
	}

}
