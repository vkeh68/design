package yjc.wdb.scts.bean;

public class PageVO {
	private int page; /* 시작 페이지 번호 */	
	private int perPageNum; /* 한 페이지에 보여줄 데이터 개수 */
	private boolean msg = true; /* 검색 후, 전을 따지기 위한 변수 */
	
	private int bhf_code;
	private String startAmount;
	private String endAmount;
	private String check; /* 재고 관리에서 검색할 때 필요한 부분 */
	private String searchType;
	private String keyword;
	
	public String getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(String endAmount) {
		this.endAmount = endAmount;
	}
	
	public String getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(String startAmount) {
		this.startAmount = startAmount;
	}
	
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PageVO(){
		this.page = 1; 
		this.perPageNum = 10; 
	}
	
	public void setPage(int page) {
		if(page <= 0){ /* 사용자 실수로 0페이지가 될 경우 1페이지로 */
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) { /* 마찬가지 미스 처리 */
		if(perPageNum <= 0 || perPageNum > 100){
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getPageStart(){ 
		/* limit 절에서 시작위치 지정할 때 사용 */
		return (this.page - 1) * perPageNum;
	}
	
	public int getPerPageNum() {
		return this.perPageNum;
	}
	
	@Override
	public String toString(){
		return "Criteria [page=" + page + ", "
				+ "perPageNum = " + perPageNum + "]";
	}

	public boolean isMsg() {
		return msg;
	}

	public void setMsg(boolean msg) {
		this.msg = msg;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getBhf_code() {
		return bhf_code;
	}

	public void setBhf_code(int bhf_code) {
		this.bhf_code = bhf_code;
	}



}
