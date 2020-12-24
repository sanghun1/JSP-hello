package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cos.hello.comfig.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {
	
	public int insert(Users user) { // Users user 
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션(동기화 동시접근 불가능)
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
//		String sql = "INSERT INTO users(username, password, email)";  
//		sql = "VALUES(?,?,?)";     // 주소가 계속 바뀜, 가독성 안좋음
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate(); // DML문장은 x.executeUpdate()
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
}
