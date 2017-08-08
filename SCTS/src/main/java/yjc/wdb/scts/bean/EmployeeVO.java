package yjc.wdb.scts.bean;

import java.sql.Date;

public class EmployeeVO {
	private String	user_id;
	private String	emp_ihidnum;
	private int		emp_anslry;
	private	String	emp_acnutno;
	private Date	emp_encpn;
	private Date	emp_retire;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEmp_ihidnum() {
		return emp_ihidnum;
	}
	public void setEmp_ihidnum(String emp_ihidnum) {
		this.emp_ihidnum = emp_ihidnum;
	}
	public int getEmp_anslry() {
		return emp_anslry;
	}
	public void setEmp_anslry(int emp_anslry) {
		this.emp_anslry = emp_anslry;
	}
	public String getEmp_acnutno() {
		return emp_acnutno;
	}
	public void setEmp_acnutno(String emp_acnutno) {
		this.emp_acnutno = emp_acnutno;
	}
	public Date getEmp_encpn() {
		return emp_encpn;
	}
	public void setEmp_encpn(Date emp_encpn) {
		this.emp_encpn = emp_encpn;
	}
	public Date getEmp_retire() {
		return emp_retire;
	}
	public void setEmp_retire(Date emp_retire) {
		this.emp_retire = emp_retire;
	}
}
