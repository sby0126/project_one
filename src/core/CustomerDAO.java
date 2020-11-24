package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CustomerDAO implements AutoCloseable  {
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	private DBConnectionMgr pool;
	
	public CustomerDAO() {
		try {			
			pool = DBConnectionMgr.getInstance();
			
		}  catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<CustomerVO> listMembers() {
		List<CustomerVO> customerList = new ArrayList<CustomerVO>();
		String query = "select * from tblCustomer order by joinDate desc";
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("CTMID");
				String hashedPassword = rs.getString("CTMPW");
				String no = rs.getString("CTMNO");
				String name = rs.getString("CTMNM");
				String address = rs.getString("ADDR");
				String tel = rs.getString("TEL");
				String email = rs.getString("EMAIL");
				String zipCode = rs.getString("ZIPCODE");
				String isAdmin = rs.getString("isAdmin");
				String joinDate = rs.getString("joinDate");
				String salt = rs.getString("salt");
				String lastLogin = rs.getString("LAST_LOGIN");
				String failedLoginCount = rs.getString("FAILED_LOGIN_COUNT");
				String isLock = rs.getString("IS_LOCK");
				
				CustomerVO c = new CustomerVO();
				
				c
					.setId(id)
					.setPassword(hashedPassword)
					.setNo(no)
					.setName(name)
					.setAddress(address)
					.setTel(tel)
					.setEmail(email)
					.setZipCode(zipCode)
					.setIsAdmin(isAdmin)
					.setJoinDate(joinDate)
					.setSalt(salt);
				
				c.setLastLogin(lastLogin);
				c.setFailedLoginCount(failedLoginCount);
				c.setIsLock(isLock);
				
				customerList.add(c);
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return customerList;
	}
	
	/**
	 * 아이디 중복 여부 판단 (쿼리문 작성)
	 * 
	 * 해당 아이디의 멤버가 조회되지 않는다면 유일한 아이디로 판단한다.
	 * 
	 * @param id
	 * @return
	 */
	public boolean isInvalidID(String id) {
		
		List<CustomerVO> customerList = new ArrayList<CustomerVO>();
		String query = "select * from tblCustomer where CTMID = ?";
		
		boolean isUnique = false;
		ResultSet rs = null;
				
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);	
			
			pstmt.setString(1, id);
			
			rs =pstmt.executeQuery();
			
			isUnique = rs.next();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return isUnique;
	}
	
	public void addCustomer(CustomerVO c) {
		try {
			
			conn = dataFactory.getConnection();
			
			String no = c.getNo();
			String id = c.getId();
			String hashedPassword = c.getPassword();
			String name = c.getName();
			String address = c.getAddress();
			String tel = c.getTel();
			String email = c.getEmail();
			String zipcode = c.getZipCode();
			String isAdmin = c.getIsAdmin();
			String joinDate = c.getJoinDate();
			String salt = c.getSalt();
			String lastLogin = c.getLastLogin();
			String failedLoginCount = c.getFailedLoginCount();
			String isLock = c.getIsLock();
			
			String query = "insert into tblCustomer" + 
					" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, hashedPassword);
			pstmt.setString(3, name);
			pstmt.setString(4, address);
			pstmt.setString(5, tel);
			pstmt.setString(6, email);
			pstmt.setString(7, isAdmin);
			pstmt.setString(8, joinDate);
			pstmt.setString(9, salt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		if(pool != null) {
			pool.freeConnection(conn, pstmt);
		}
		
	}
	
}
