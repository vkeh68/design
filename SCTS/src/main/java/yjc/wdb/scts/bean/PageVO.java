package yjc.wdb.scts.bean;

public class PageVO {
	private int page; /* ���� ������ ��ȣ */	
	private int perPageNum; /* �� �������� ������ ������ ���� */
	private boolean msg = true; /* �˻� ��, ���� ������ ���� ���� */
	
	private int bhf_code;
	private String startAmount;
	private String endAmount;
	private String check; /* ��� �������� �˻��� �� �ʿ��� �κ� */
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
		if(page <= 0){ /* ����� �Ǽ��� 0�������� �� ��� 1�������� */
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) { /* �������� �̽� ó�� */
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
		/* limit ������ ������ġ ������ �� ��� */
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
