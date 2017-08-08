package yjc.wdb.scts.bean;

import java.sql.Date;

public class StockVO {
	private int bhf_code;
	private int wrhousng_qy;
	private Date wrhousng_de;
	private int puchas_pc;
	private Date puchas_de;

	private String user_id;
	private String suply_entrps_nm;
	private int suply_entrps_telno;
	private Date distb_de;
	private int invntry_qy;
	private int goods_code;
	private String goods_nm;
	
	public int getWrhousng_qy() {
		return wrhousng_qy;
	}
	public void setWrhousng_qy(int wrhousng_qy) {
		this.wrhousng_qy = wrhousng_qy;
	}
	public int getPuchas_pc() {
		return puchas_pc;
	}
	public void setPuchas_pc(int puchas_pc) {
		this.puchas_pc = puchas_pc;
	}
	public Date getPuchas_de() {
		return puchas_de;
	}
	public void setPuchas_de(Date puchas_de) {
		this.puchas_de = puchas_de;
	}
	public Date getWrhousng_de() {
		return wrhousng_de;
	}
	public void setWrhousng_de(Date wrhousng_de) {
		this.wrhousng_de = wrhousng_de;
	}
	public Date getDistb_de() {
		return distb_de;
	}
	public void setDistb_de(Date distb_de) {
		this.distb_de = distb_de;
	}
	public int getInvntry_qy() {
		return invntry_qy;
	}
	public void setInvntry_qy(int invntry_qy) {
		this.invntry_qy = invntry_qy;
	}
	public int getGoods_code() {
		return goods_code;
	}
	public void setGoods_code(int goods_code) {
		this.goods_code = goods_code;
	}
	public String getGoods_nm() {
		return goods_nm;
	}
	public void setGoods_nm(String goods_nm) {
		this.goods_nm = goods_nm;
	}
	public int getBhf_code() {
		return bhf_code;
	}
	public void setBhf_code(int bhf_code) {
		this.bhf_code = bhf_code;
	}
	public String getSuply_entrps_nm() {
		return suply_entrps_nm;
	}
	public void setSuply_entrps_nm(String suply_entrps_nm) {
		this.suply_entrps_nm = suply_entrps_nm;
	}
	public int getSuply_entrps_telno() {
		return suply_entrps_telno;
	}
	public void setSuply_entrps_telno(int suply_entrps_telno) {
		this.suply_entrps_telno = suply_entrps_telno;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
