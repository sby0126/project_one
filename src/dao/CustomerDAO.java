package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sql.CustomerSQL;
import utils.DBConnectionMgr;
import utils.SHA256Util;
import vo.CustomerVO;

public class CustomerDAO implements IDAO {
	
	public static final String TABLE_NAME = "tblCustomer";
	public static final String QL_VERSION = "mysql1";
	
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;

	private CustomerSQL qlNotes;
	
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
		qlNotes = new CustomerSQL();
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
					.setIsLock( 	rs.getString("IS_LOCK") )
					.setCtmtype( rs.getString("CTMTYPE"));
				
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
				String ctmType = rs.getString("CTMTYPE");
				
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
					.setSalt(salt)
					.setCtmtype(ctmType);
				
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
	
	public boolean isSNSMember(String id) {
		boolean isOK = false;;
		
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("select * from tblCustomer where CTMID = ?");
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				String ctmType = rs.getString("CTMTYPE");
				if(ctmType.equals("네이버") || ctmType.equals("카카오")) {
					return true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return false;
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
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
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
			pstmt.setString(10, c.getCtmtype());
			
			if(pstmt.executeUpdate() > 0) {
				conn.commit();
				System.out.println("회원이 추가되었습니다.");
				
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
	}
	
	public boolean updateCustomer(CustomerVO c) {
		
		boolean ret = false;
		
		try {
			conn = pool.getConnection();
			
			String query = "update tblCustomer set CTMPW = ?, CTMNM = ?, ADDR = ?, TEL = ?, ZIPCODE = ?, SALT = ? where CTMID = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, c.getPassword());
			pstmt.setString(2, c.getName());
			pstmt.setString(3, c.getAddress());
			pstmt.setString(4, c.getTel());
			pstmt.setString(5, c.getZipCode());
			pstmt.setString(6, c.getSalt());
			pstmt.setString(7, c.getId());
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
				conn.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	/**
	 * 회원 탈퇴 처리 (아이디, 비밀번호 필요)
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean secessionCustomer(String id, String password) {
		boolean isSecession = false;
		
		try {
			conn = pool.getConnection();
			
			CustomerVO vo = getMember(id);
						
			String salt = vo.getSalt();
			
			if(password == null) {
				password = "0";
			}
			
			String hashedPassword = SHA256Util.getEncrypt(password, salt);
			
			boolean isValid = vo.getPassword().equals(hashedPassword);
			
			if(vo.getCtmtype().equals("카카오") || vo.getCtmtype().equals("네이버")) {
				isValid = true;
			}
			
			// 비밀 번호 확인 처리
			if(isValid) {
				
				// 외래키가 걸린 게시물의 댓글 삭제
				pstmt = conn.prepareStatement("delete from tblQNABoardComments where authorID = ?");
				pstmt.setString(1, id);
				pstmt.executeUpdate();
				
				// 외래키가 걸린 게시물 삭제
				pstmt = conn.prepareStatement("delete from tblQNABoard where authorID = ?");
				pstmt.setString(1, id);
				pstmt.executeUpdate();
				
				// 회원 탈퇴 처리
				pstmt = conn.prepareStatement("delete from tblCustomer where CTMID = ?");
				pstmt.setString(1, id);
				if(pstmt.executeUpdate() > 0) {
					isSecession = true;
					conn.commit();
				}
				
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isSecession;
	}
	
	/**
	 * 회원 탈퇴 처리 (관리자 전용)
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean secessionCustomerForAdminMode(String id) {
		boolean isSecession = false;
		
		try {
			conn = pool.getConnection();
			
			CustomerVO vo = getMember(id);
				
			// 외래키가 걸린 게시물의 댓글 삭제
			pstmt = conn.prepareStatement("delete from tblQNABoardComments where authorID = ?");
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			// 외래키가 걸린 게시물 삭제
			pstmt = conn.prepareStatement("delete from tblQNABoard where authorID = ?");
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			// 회원 탈퇴 처리
			pstmt = conn.prepareStatement("delete from tblCustomer where CTMID = ?");
			pstmt.setString(1, id);
			if(pstmt.executeUpdate() > 0) {
				isSecession = true;
				conn.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isSecession;
	}
	
	/**
	 * 마지막 시간을 업데이트 합니다.
	 * @param id
	 */
	public boolean updateLastLogin(String id) {
		boolean ret = false;
		
		try {
			conn = pool.getConnection();
			String query = "UPDATE tblCustomer SET last_login = NOW() WHERE ctmid = ?";
			
			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, id);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	/**
	 * ID와 이메일이 DB에 있는지 확인합니다. 
	 *  
	 * @param id
	 * @param email
	 * @return
	 */
	public boolean checkWithIdAndEmail(String id, String email) {
		boolean isValidID = this.isInvalidID(id);
		boolean isValidEmail = false;
		
		ResultSet rs = null;
		
		try {
			
			conn = pool.getConnection();
			String query = "select CTMID, EMAIL from tblCustomer where ctmid = ? and email = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			
			rs = pstmt.executeQuery();
			
			isValidEmail = rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isValidID && isValidEmail;
	}
	/**
	 * 이메일이 DB에 있는지 확인합니다. 
	 *  
	 * @param id
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email) {
		boolean isValidEmail = false;
		
		ResultSet rs = null;
		
		try {
			
			conn = pool.getConnection();
			String query = "select CTMID, EMAIL from tblCustomer where email = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			isValidEmail = rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isValidEmail;
	}
	
	/**
	 * 비밀번호를 변경합니다.
	 * 
	 * @param id
	 * @param password
	 */
	public boolean changePassword(String id, String password) {
		
		boolean ret = false;
		
		try {
			conn = pool.getConnection();
			
			String query = "update tblCustomer set ctmpw = ?, salt = ? where ctmid = ?";
			
			pstmt = conn.prepareStatement(query);
			
			String salt = SHA256Util.generateSalt();			
			String hashedPassword = SHA256Util.getEncrypt(password, salt);
			
			pstmt.setString(1, hashedPassword);
			pstmt.setString(2, salt);
			pstmt.setString(3, id);
			
			if(pstmt.executeUpdate() > 0) {
				ret = true;
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return ret;
	}
	
	public CustomerVO findId(String name, String email) {
		String retId = null;
		ResultSet rs = null;
		
		CustomerVO c = null;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement("select * from tblCustomer where CTMNM = ? and EMAIl = ?");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("CTMID");
				String hashedPassword = rs.getString("CTMPW");
				int no = rs.getInt("CTMNO");
				String rName = rs.getString("CTMNM");
				String address = rs.getString("ADDR");
				String tel = rs.getString("TEL");
				String rEmail = rs.getString("EMAIL");
				String zipCode = rs.getString("ZIPCODE");
				String joinDate = rs.getString("joinDate");
				String salt = rs.getString("salt");
				String lastLogin = rs.getString("LAST_LOGIN");
				String failedLoginCount = rs.getString("FAILED_LOGIN_COUNT");
				String isLock = rs.getString("IS_LOCK");
				String ctmType = rs.getString("CTMTYPE");
				
				c = new CustomerVO();
				
				c
					.setId(id)
					.setPassword(hashedPassword)
					.setNo(no)
					.setName(rName)
					.setAddress(address)
					.setTel(tel)
					.setEmail(rEmail)
					.setZipCode(zipCode)
					.setJoinDate(joinDate)
					.setSalt(salt)
					.setCtmtype(ctmType);
				
				c.setLastLogin(lastLogin);
				c.setFailedLoginCount(failedLoginCount);
				c.setIsLock(isLock);
				
				return c;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return c;
 		
	}
}
