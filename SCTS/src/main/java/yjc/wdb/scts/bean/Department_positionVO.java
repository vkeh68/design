package yjc.wdb.scts.bean;

import java.sql.Date;

public class Department_positionVO {
	private int		dept_code;
	private int		bhf_code;
	private Date	deptpsitn_abl_de;
	private Date	deptpsitn_estbl_de;
	
	public int getDept_code() {
		return dept_code;
	}
	public void setDept_code(int dept_code) {
		this.dept_code = dept_code;
	}
	public int getBhf_code() {
		return bhf_code;
	}
	public void setBhf_code(int bhf_code) {
		this.bhf_code = bhf_code;
	}
	public Date getDeptpsitn_abl_de() {
		return deptpsitn_abl_de;
	}
	public void setDeptpsitn_abl_de(Date deptpsitn_abl_de) {
		this.deptpsitn_abl_de = deptpsitn_abl_de;
	}
	public Date getDeptpsitn_estbl_de() {
		return deptpsitn_estbl_de;
	}
	public void setDeptpsitn_estbl_de(Date deptpsitn_estbl_de) {
		this.deptpsitn_estbl_de = deptpsitn_estbl_de;
	}
}
