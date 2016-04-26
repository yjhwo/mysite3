package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.db.DBConnection;
import com.estsoft.mysite.vo.UserVO;

@Repository
public class UserDAO {
	
	@Autowired
	private DBConnection dbConnection;

	public UserVO get(String email){
		UserVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dbConnection.getConnection();
			String sql = "SELECT no,email FROM user WHERE email = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				vo = new UserVO();
				vo.setNo(rs.getLong(1));
				vo.setEmail(rs.getString(2));
			}
			
			return vo;
		}catch(SQLException ex){
			System.out.println("error:"+ex);
			return null;
		}finally{
			try {
				if (pstmt != null)			pstmt.close();
				if (conn != null)			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// method는 테이블에 CRUD하는 이름으로 지어주는 게 좋다.
	public void update(UserVO vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = dbConnection.getConnection();
			
			String sql = "UPDATE user SET name=?, passwd=password(?), gender=? WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			
			pstmt.executeUpdate();
			
		}catch(SQLException ex){
			System.out.println("error: "+ex);
		}finally { 
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 여러가지 방식으로 가져올 수 있다.
	public UserVO get(Long no) {
		UserVO userVo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbConnection.getConnection();
			
			String sql = "SELECT name, email, gender FROM user WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			// 이메일과 pw가 일치하는 경우여서 한 row를 가져온 것
			if(rs.next()){
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				
				userVo = new UserVO(no, name, email, gender);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if( rs != null) 	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return userVo;
	}

	// 보안 = 인증 + 권한
	// 인증(Auth)
	public UserVO get(String email, String password) {

		return null;
	}

	public UserVO get(UserVO vo) {
		UserVO userVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbConnection.getConnection();
			
			String sql = "SELECT no, name, email, gender FROM user WHERE email=? AND passwd = password(?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			rs = pstmt.executeQuery();
			
			// 이메일과 pw가 일치하는 경우여서 한 row를 가져온 것
			if(rs.next()){
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				userVo = new UserVO(no, name, email, gender);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if( rs != null) 	rs.close();
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return userVo;
		
	}

	public void insert(UserVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConnection.getConnection();
			String sql = "INSERT INTO user VALUES(null,?,?,password(?),?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null)		pstmt.close();
				if (conn != null)		conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
