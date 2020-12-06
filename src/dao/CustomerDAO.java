package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.DBConnectionMgr;
import utils.SHA256Util;
import vo.CustomerVO;

public class CustomerDAO implements IDAO {
	
	public static final String TABLE_NAME = "tblCustomer";
	public static final String QL_VERSION = "mysql1";
	
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;

	private HashMap<String, String> qlNotes = new HashMap<String, String>();
	
	private static CustomerDAO instance = null;
	
	private CustomerDAO() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		initSQLNotes();
	}
	
	/**
	 * 싱글턴 패턴으로 인스턴스를 하나만 생성합니다.
	 * @return
	 */
	public static synchronized CustomerDAO getInstance() {
		if(instance == null) {
			instance = new CustomerDAO();
		}
		
		return instance;
	}
	
	public void initSQLNotes() {
		
		qlNotes.put("mysql1.selectOnce", "select * from tblCustomer where ctmid = ?");
		qlNotes.put("mysql1.selectAll", "select * from tblCustomer");
		
		qlNotes.put("mysql1.insert", "insert into tblCustomer "
				+ "(CTMID, CTMPW, CTMNM, ADDR, TEL, EMAIL, ZIPCODE, IS_ADMIN, JOINDATE, SALT)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?)");
		
		qlNotes.put("mysql1.updateName", "update * from tblCustomer set ctmnm = ? where ctmid = ?");
		qlNotes.put("mysql1.delete", "delete from tblCustomer where ctmid = ?");
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
		String query = getQuery("selectOnce");
		CustomerVO cust = new CustomerVO();
		ResultSet rs = null;
		
		try  {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, custId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {

				CustomerVO c = new CustomerVO();
				
				c
					.setId( 		rs.getString("CTMID") )
					.setPassword( 	rs.getString("CTMPW") )
					.setNo( 		rs.getInt("CTMNO") )
					.setName( 		rs.getString("CTMNM") )
					.setAddress(	rs.getString("ADDR") )
					.setTel( 		rs.getString("TEL") )
					.setEmail( 		rs.getString("EMAIL") )
					.setZipCode(	rs.getString("ZIPCODE") )
					.setJoinDate(	rs.getString("joinDate") )
					.setSalt( 		rs.getString("salt") )
					.setIsAdmin(    rs.getString("IS_ADMIN"))
					.setLastLogin( 	rs.getString("LAST_LOGIN") )
					.setFailedLoginCount( rs.getString("FAILED_LOGIN_COUNT") )
					.setIsLock( 	rs.getString("IS_LOCK") );
				
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
		String query = getQuery("selectAll");
		ResultSet rs = null;
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("CTMID");
				String hashedPassword = rs.getString("CTMPW");
				int no = rs.getInt("CTMNO");
				String name = rs.getString("CTMNM");
				String address = rs.getString("ADDR");
				String tel = rs.getString("TEL");
				String email = rs.getString("EMAIL");
				String zipCode = rs.getString("ZIPCODE");
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
	
	/**
	 * 관리자 목록을 추출합니다.
	 * @return
	 */
	public List<String> getAdminators() {
		List<String> adminators = new ArrayList<String>();
		ResultSet rs = null;
		
		try {
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("select CTMID" + 
					" from tblCustomer" +
					" where IS_ADMIN = 'Y'");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				adminators.add(rs.getString("CTMID"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return adminators;
	}
	
	/**
	 * 로그인 시간을 업데이트 합니다.
	 * @param memberID
	 * @return
	 */
	public boolean updateLastLogin(String memberID) {
		
		String formatedDate = "";
		boolean ret = false;
		
		try {
			// 현재 시각을 구합니다.
			LocalDateTime dateTime = LocalDateTime.now();
			
			// LocalDateTime toString과 동일
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			formatedDate = dateTime.format(formatter);
			
			System.out.println(formatedDate);
			
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement("update tblCustomer set LAST_LOGIN = ? where CTMID = ?");
			pstmt.setString(1, formatedDate);
			pstmt.setString(2, memberID);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
			}
			
			
		} catch(SQLException | DateTimeParseException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public boolean processLogin(String id, String pw) {
		boolean ret = false;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			
			String query = getQuery("selectOnce");
			
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
		String query = getQuery("selectOnce");
		
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
			
			String query = getQuery("insert");
		
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, c.getId());
			pstmt.setString(2, c.getPassword());
			pstmt.setString(3, c.getName());
			pstmt.setString(4, c.getAddress());
			pstmt.setString(5, c.getTel());
			pstmt.setString(6, c.getEmail());
			pstmt.setString(7, c.getZipCode());
			pstmt.setString(8, c.getIsAdmin());
			pstmt.setString(9, c.getSalt());
			
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
