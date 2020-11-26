package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import utils.SHA256Util;

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
	
	public boolean processLogin(String id, String pw) {
		boolean ret = false;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			
			String query = "select * from tblCustomer where CTMID = ?";
			
			String salt = "";
			String targetPW = "";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				targetPW = rs.getString("CTMPW");
				salt = rs.getString("SALT");
				
				System.out.println(targetPW);
				
				// 비밀 번호가 맞는가?
				String hashedPW = SHA256Util.getEncrypt(pw, salt);
				
				System.out.println("hashed : " + hashedPW);
				if(hashedPW.equals(targetPW)) {
					ret = true;
				} else {
					ret = false;
				}				
			}
			
			rs.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
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
			
			rs = pstmt.executeQuery();
			
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
			
			conn = pool.getConnection();
			
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
			
			String query = "insert into tblCustomer (CTMID, CTMPW, CTMNO, CTMNM, ADDR, TEL, EMAIL, ZIPCODE, IS_ADMIN, JOINDATE, SALT)" + 
					" values(?, ?, null, ?, ?, ?, ?, ?, ?, CURDATE(), ?)";
		
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, hashedPassword);
			
			pstmt.setString(3, name);
			pstmt.setString(4, address);
			pstmt.setString(5, tel);
			pstmt.setString(6, email);
			pstmt.setString(7, zipcode);
			pstmt.setString(8, isAdmin);
			pstmt.setString(9, salt);
			
			if(pstmt.executeUpdate() > 0) {
				conn.commit();
				System.out.println("회원이 추가되었습니다.");
				
			};
			
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
