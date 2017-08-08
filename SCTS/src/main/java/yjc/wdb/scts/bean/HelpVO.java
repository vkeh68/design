package yjc.wdb.scts.bean;

import java.util.Date;

public class HelpVO {
	private Integer bbsctt_code;  /*게시글 번호*/
	private Integer bhf_code; /* 지점코드 */
	private Integer bbsctgry_code; /* 카테고리 */
	private String bbsctt_sj;  /* 게시글 제목 */
	private String bbsctt_cn;  /*게시글 내용*/
	private String user_id;  /*작성자 (고정)*/
	private Date bbsctt_rgsde;  /* 게시글 등록날짜*/
	
	public Integer getBbsctt_code() {
		return bbsctt_code;
	}
	public void setBbsctt_code(Integer bbsctt_code) {
		this.bbsctt_code = bbsctt_code;
	}
	public String getBbsctt_sj() {
		return bbsctt_sj;
	}
	public void setBbsctt_sj(String bbsctt_sj) {
		this.bbsctt_sj = bbsctt_sj;
	}
	public String getBbsctt_cn() {
		return bbsctt_cn;
	}
	public void setBbsctt_cn(String bbsctt_cn) {
		this.bbsctt_cn = bbsctt_cn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getBbsctt_rgsde() {
		return bbsctt_rgsde;
	}
	public void setBbsctt_rgsde(Date bbsctt_rgsde) {
		this.bbsctt_rgsde = bbsctt_rgsde;
	}
	public Integer getBhf_code() {
		return bhf_code;
	}
	public void setBhf_code(Integer bhf_code) {
		this.bhf_code = bhf_code;
	}
	public Integer getBbsctgry_code() {
		return bbsctgry_code;
	}
	public void setBbsctgry_code(Integer bbsctgry_code) {
		this.bbsctgry_code = bbsctgry_code;
	}
	
}
