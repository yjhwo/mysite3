package com.estsoft.mysite.vo;

public class GuestBookVO {
	private Long no;
	private String name;
	private String reg_date;
	private String message;
	private String passwd;
	
	public GuestBookVO(){	}

	
	public GuestBookVO(String name, String passwd, String message) {
		this.name = name;
		this.passwd = passwd;
		this.message = message;
	}


	public GuestBookVO(Long no, String name, String reg_date, String message) {
		this.no = no;
		this.name = name;
		this.reg_date = reg_date;
		this.message = message;
	}


	public GuestBookVO(Long no, String name, String reg_date, String message, String passwd) {
		this.no = no;
		this.name = name;
		this.reg_date = reg_date;
		this.message = message;
		this.passwd = passwd;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "GuestBookVO [no=" + no + ", name=" + name + ", reg_date=" + reg_date + ", message=" + message
				+ ", passwd=" + passwd + "]";
	}
	
	
}
