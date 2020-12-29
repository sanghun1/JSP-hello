package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.config.DBConn;
import com.cos.hello.dto.JoinDto;
import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;

public class UsersDao {
	
	public int insert(JoinDto joinDto) { // Users user 
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화 동시접근 불가능)
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
//		String sql = "INSERT INTO users(username, password, email)";  
//		sql = "VALUES(?,?,?)";     // 주소가 계속 바뀜, 가독성 안좋음
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, joinDto.getUsername());
			pstmt.setString(2, joinDto.getPassword());
			pstmt.setString(3, joinDto.getEmail());
			int result = pstmt.executeUpdate(); // DML문장은 x.executeUpdate()
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public Users login(LoginDto loginDto) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화 동시접근 불가능)
		sb.append("SELECT id, username, email FROM users ");
		sb.append("WHERE username =? AND password =?");
		String sql = sb.toString();
//		String sql = ("SELEST id, username, email FROM users WHERE username = ? AND password = ?");
		
		Connection conn = DBConn.getInstance();
	
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginDto.getUsername());
			pstmt.setString(2, loginDto.getPassword());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Users selectById(int id) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화 동시접근 불가능)
		sb.append("SELECT id, password, username, email FROM users WHERE id = ?");
		String sql = sb.toString();
		
		Connection conn = DBConn.getInstance();
	
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.password(rs.getString("password"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public int update(Users user) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화 동시접근 불가능)
		sb.append("UPDATE users SET password = ? , email = ? WHERE id = ? ");
		String sql = sb.toString();
		
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			int result = pstmt.executeUpdate(); // DML문장은 x.executeUpdate()
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int delete(int id) {

		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화 동시접근 불가능)
		sb.append("DELETE FROM users WHERE id = ? ");
		String sql = sb.toString();
		
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate(); // DML문장은 x.executeUpdate()
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
