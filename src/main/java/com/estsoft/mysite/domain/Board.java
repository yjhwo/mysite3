package com.estsoft.mysite.domain;

import java.util.Date;

import javax.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no; // 번호

	@NotEmpty
	@Column(name = "title", nullable = false, length = 150)
	private String title; // 제목

	@Column(name = "content", nullable = false)
	@Lob
	private String content; // 내용

	@Column(name = "reg_date", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date reg_date;// 등록일

	@Column(name = "viewCount", nullable = false)
	private Long viewCount; // 조회수

	@Column(name = "group_no", nullable = false)
	private Long group_no; // 그룹번호

	@Column(name = "order_no", nullable = false)
	private Long order_no; // 그룹 내 순서

	@Column(name = "depth", nullable = false)
	private Long depth; // 글 깊이

	@Column(name = "user_no", nullable = false)
	private Long user_no; // 회원번호
	// private String user_name; // 글쓴이

	public Board() {	}
	public Board(Long no, String title, String content, Date reg_date, Long viewCount, Long group_no, Long order_no,
			Long depth, Long user_no) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.viewCount = viewCount;
		this.group_no = group_no;
		this.order_no = order_no;
		this.depth = depth;
		this.user_no = user_no;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Long getViewCount() {
		return viewCount;
	}
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	public Long getGroup_no() {
		return group_no;
	}
	public void setGroup_no(Long group_no) {
		this.group_no = group_no;
	}
	public Long getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Long order_no) {
		this.order_no = order_no;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public Long getUser_no() {
		return user_no;
	}
	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}
	@Override
	public String toString() {
		return "Board [no=" + no + ", title=" + title + ", content=" + content + ", reg_date=" + reg_date
				+ ", viewCount=" + viewCount + ", group_no=" + group_no + ", order_no=" + order_no + ", depth=" + depth
				+ ", user_no=" + user_no + "]";
	}

}
