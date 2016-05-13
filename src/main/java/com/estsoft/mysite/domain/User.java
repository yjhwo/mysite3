package com.estsoft.mysite.domain;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@NotEmpty
	@Column(name = "name", nullable = false, length = 100)
	private String name; // name은 값이 있어야 한다는 뜻

	@NotEmpty
	@Email
	@Column(name = "email", nullable = false, length = 200)
	private String email;

	@Column(name = "password", nullable = false, length = 32)
	private String password;
	
	
	@Column(name = "gender", nullable = false, columnDefinition="enum('FEMALE','MALE')")
	@Enumerated(value = EnumType.STRING)
	private Gender gender;
	
	public User(){		}

	public User(Long no, String name, String email, String password, Gender gender) {
		this.no = no;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;	
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [no=" + no + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + "]";
	}
	

}
