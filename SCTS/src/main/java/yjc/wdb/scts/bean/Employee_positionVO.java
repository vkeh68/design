package yjc.wdb.scts.bean;

import java.sql.Date;

public class Employee_positionVO {
	private String	user_id;
	private int		dept_code;
	private String	emppsitn_rspofc;
	private Date	emppsitn_gnfd_de;
	private Date	emppsitn_leav_de;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getDept_code() {
		return dept_code;
	}
	public void setDept_code(int dept_code) {
		this.dept_code = dept_code;
	}
	public String getEmppsitn_rspofc() {
		return emppsitn_rspofc;
	}
	public void setEmppsitn_rspofc(String emppsitn_rspofc) {
		this.emppsitn_rspofc = emppsitn_rspofc;
	}
	public Date getEmppsitn_gnfd_de() {
		return emppsitn_gnfd_de;
	}
	public void setEmppsitn_gnfd_de(Date emppsitn_gnfd_de) {
		this.emppsitn_gnfd_de = emppsitn_gnfd_de;
	}
	public Date getEmppsitn_leav_de() {
		return emppsitn_leav_de;
	}
	public void setEmppsitn_leav_de(Date emppsitn_leav_de) {
		this.emppsitn_leav_de = emppsitn_leav_de;
	}
}
