package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.SHA256Util;

public class CustomerDAO {
	
	public static final String TABLE_NAME = "tblCustomer";
	public static final String QL_VERSION = "mysql1";
	
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;

	private HashMap<String, String> qlNotes = new HashMap<String, String>();
	
	public CustomerDAO() {
		super();
		initSQLNotes();
	}
	
	public void initSQLNotes() {
		// mysql1 (ctmno에 unique auto_increment가 설정된 버전)
		qlNotes.put("mysql1.getMember", "select * from tblCustomer where ctmid = ?");
		qlNotes.put("mysql1.listMembers", "select * from tblCustomer where ctmid = ?");
		qlNotes.put("mysql1.processLogin", "select * from tblCustomer where CTMID = ?");
		qlNotes.put("mysql1.isInvalidID", "select * from tblCustomer where CTMID = ?");
		qlNotes.put("mysql1.addCustomer", "insert into tblCustomer "
				+ "(CTMID, CTMPW, CTMNM, ADDR, TEL, EMAIL, ZIPCODE, IS_ADMIN, JOINDATE, SALT)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?)");
		
		// mysql 2 
		
	}
	
	public ArrayList<CustomerVO> test(String id) {
		ResultSet rs = null;
		ArrayList<CustomerVO> list = null;
		
		try {
			String query = getQuery("getMember");
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			list = SQLHelper.putResult(rs, CustomerVO.class);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return list;
	}
	
	/**
	 * 쿼리문을 가져옵니다.
	 * @param command
	 * @return
	 */
	public String getQuery(String command) {
		String commandName = QL_VERSION + "." + command;
		
		return qlNotes.get(commandName);
	}
	
	public CustomerVO getMember(String custId) {
		String query = getQuery("getMember");
		CustomerVO cust = new CustomerVO();
		ResultSet rs = null;
		
		try  {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, custId);
			rs = pstmt.executeQuery();
			
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
				
				cust = c;
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return cust;
		
	}
	
	/***
	 * 전체 회원 목록을 받아옵니다.
	 * 
	 * @return List<CustomerVO>
	 */
	public List<CustomerVO> listMembers() {
		List<CustomerVO> customerList = new ArrayList<CustomerVO>();
		String query = getQuery("listMembers");
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return customerList;
	}
	
	public boolean processLogin(String id, String pw) {
		boolean ret = false;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			
			String query = getQuery("processLogin");
			
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
		String query = getQuery("isInvalidID");
		
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
			
			String query = getQuery("addCustomer");
		
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
	
}
