package com.estsoft.mysite.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "guestbook")
public class GuestBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "reg_date", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date reg_date;
	
	@Column(name = "message", nullable = false)
	@Lob
	private String message;
	
	@Column(name = "passwd", nullable = false, length = 32)
	private String passwd;
	
	public GuestBook(){		}

	public GuestBook(Long no, String name, Date reg_date, String message, String passwd) {
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

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
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
		return "GuestBook [no=" + no + ", name=" + name + ", reg_date=" + reg_date + ", message=" + message
				+ ", passwd=" + passwd + "]";
	}

	
}
