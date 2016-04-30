package com.estsoft.mysite.vo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserVO {
	private Long no;
	
	@NotEmpty
	private String name;	// name은 값이 있어야 한다는 뜻
	
	@NotEmpty
	@Email
	private String email;
	
	private String password;
	private String gender;
	
	public UserVO(){	}

	public UserVO(String name, String password, String gender) {
		this.name = name;
		this.password = password;
		this.gender = gender;
	}

	public UserVO(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public UserVO(Long no, String name, String email) {
		this.no = no;
		this.name = name;
		this.email = email;
	}


	public UserVO(Long no, String name, String email, String gender) {
		this.no = no;
		this.name = name;
		this.email = email;
		this.gender = gender;
	}

	public UserVO(String name, String email, String password, String gender) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
	}


	public UserVO(Long no, String name, String email, String password, String gender) {
		this.no = no;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserVO [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + "]";
	}
	
	
}
