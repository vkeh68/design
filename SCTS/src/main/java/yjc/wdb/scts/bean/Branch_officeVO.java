package yjc.wdb.scts.bean;

public class Branch_officeVO {
	private int		bhf_code;
	private String	bhf_nm;
	private String	bhf_adres;
	private String	bhf_telno;
	private String totalPrice;
	private int totalSale;
	
	public int getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(int totalSale) {
		this.totalSale = totalSale;
	}
	public int getBhf_code() {
		return bhf_code;
	}
	public void setBhf_code(int bhf_code) {
		this.bhf_code = bhf_code;
	}
	public String getBhf_nm() {
		return bhf_nm;
	}
	public void setBhf_nm(String bhf_nm) {
		this.bhf_nm = bhf_nm;
	}
	public String getBhf_adres() {
		return bhf_adres;
	}
	public void setBhf_adres(String bhf_adres) {
		this.bhf_adres = bhf_adres;
	}
	public String getBhf_telno() {
		return bhf_telno;
	}
	public void setBhf_telno(String bhf_telno) {
		this.bhf_telno = bhf_telno;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
}
