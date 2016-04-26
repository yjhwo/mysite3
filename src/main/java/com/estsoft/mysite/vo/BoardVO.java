package com.estsoft.mysite.vo;

public class BoardVO {
	private Long no;		// 번호
	private String title;	// 제목
	private String content;	// 내용
	private String reg_date;// 등록일
	private Long viewCount;	// 조회수
	private Long group_no;	// 그룹번호
	private Long order_no;	// 그룹 내 순서
	private Long depth;		// 글 깊이
	private Long user_no;	// 회원번호
	private String user_name;	// 글쓴이
	
	public BoardVO(){	}



	public BoardVO(Long group_no, Long order_no, Long depth) {
		this.group_no = group_no;
		this.order_no = order_no;
		this.depth = depth;
	}



	public BoardVO(String title, String content) {
		this.title = title;
		this.content = content;
	}


	public BoardVO(Long no, String title, String content) {	// 글 번호
		this.no = no;
		this.title = title;
		this.content = content;
	}


	public BoardVO(String title, String content, Long user_no) {	// 사용자 번호 
		this.title = title;
		this.content = content;
		this.user_no = user_no;
	}

	public BoardVO(Long no, String title, String content, Long user_no, String user_name, Long viewCount, String reg_date) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.user_no = user_no;
		this.user_name = user_name;
		this.viewCount = viewCount;
		this.reg_date = reg_date;
	}



	public BoardVO(Long no, String title, String content, String reg_date, Long viewCount, Long group_no, Long order_no,
			Long depth, Long user_no, String user_name) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.viewCount = viewCount;
		this.group_no = group_no;
		this.order_no = order_no;
		this.depth = depth;
		this.user_no = user_no;
		this.user_name = user_name;
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

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
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
	
	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", title=" + title + ", content=" + content + ", reg_date=" + reg_date + ", viewCount="
				+ viewCount + ", group_no=" + group_no + ", order_no=" + order_no + ", depth=" + depth + ", user_no="
				+ user_no + "]";
	}
	
	
	
}
