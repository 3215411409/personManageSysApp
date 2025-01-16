package com.mmsx.app.department;
public class Department {
	private String departmentID;
	private String departmentName;
	private String principal;//Department head
	private String tel;
	
	public Department(String departmentID, String departmentName,
			String principal, String tel) {
		super();
		this.departmentID = departmentID;
		this.departmentName = departmentName;
		this.principal = principal;
		this.tel = tel;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}	
	public String toString() {  
		return "id=" + departmentID + ", name=" + departmentName + ",sex=" + principal + ",age=" + tel;  
	} 
}
