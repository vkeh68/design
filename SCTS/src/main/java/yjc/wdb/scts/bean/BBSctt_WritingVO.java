package yjc.wdb.scts.bean;

import java.sql.Date;

public class BBSctt_WritingVO {
	
	private int bhf_code;
	private String user_id;
	private int bbsctgry_code;
	private int bbsctt_code;
	private Date bbsctt_rgsde;
	private int bbsctt_rdcnt;
	
	public int getBhf_code() {
		return bhf_code;
	}
	public void setBhf_code(int bhf_code) {
		this.bhf_code = bhf_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getBbsctgry_code() {
		return bbsctgry_code;
	}
	public void setBbsctgry_code(int bbsctgry_code) {
		this.bbsctgry_code = bbsctgry_code;
	}
	public int getBbsctt_code() {
		return bbsctt_code;
	}
	public void setBbsctt_code(int bbsctt_code) {
		this.bbsctt_code = bbsctt_code;
	}
	public Date getBbsctt_rgsde() {
		return bbsctt_rgsde;
	}
	public void setBbsctt_rgsde(Date bbsctt_rgsde) {
		this.bbsctt_rgsde = bbsctt_rgsde;
	}
	public int getBbsctt_rdcnt() {
		return bbsctt_rdcnt;
	}
	public void setBbsctt_rdcnt(int bbsctt_rdcnt) {
		this.bbsctt_rdcnt = bbsctt_rdcnt;
	}

}
