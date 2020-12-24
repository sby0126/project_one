package utils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.SQLHelper;
import vo.CurrentLoginMembersVO;
import vo.IpLogger;



public class AdminUtil {

	private DBConnectionMgr pool;
	private Connection conn;
	private static AdminUtil inst;
	
	private AdminUtil() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static synchronized AdminUtil getInstance() {
		if(inst == null) {
			inst = new AdminUtil(); 
		}
		
		return inst;
	}
	
	/**
	 * 파일을 삭제합니다.
	 * @param filename
	 */
	public void deleteFile(String filename) {
		File targetFile = new File(filename);
		
		if(targetFile.exists()) {
			if(targetFile.delete()) {
				System.out.println("파일이 삭제되었습니다.");
			}
		}
	}
	
	/**
	 * 아이피를 기록합니다.
	 * 
	 * @param ID
	 * @param IP
	 * @return
	 */
	public boolean loggingIP(String ID, String IP) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean isAlreadyLogin = false;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement("select * from tblCurrentlyMembers where ip = ?");
			pstmt.setString(1, IP);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isAlreadyLogin = true;
			} else {
				pstmt = conn.prepareStatement("insert into tblCurrentlyMembers values(?, ?, now())");
				pstmt.setString(1, ID);
				pstmt.setString(2, IP);
				pstmt.executeUpdate();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return isAlreadyLogin;
		
	}
	public boolean eraseIPFromLog(String ID) {
		PreparedStatement pstmt = null;
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("delete from tblCurrentlyMembers where ID = ?");
			pstmt.setString(1, ID);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
		
	}
	
	public List<IpLogger> getIpLogger() {
		List<IpLogger> list = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement("select * from tblIpLogger");
			rs = pstmt.executeQuery();
			
			list = SQLHelper.putResult(rs, IpLogger.class);
						
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return list;
	}
	public List<CurrentLoginMembersVO> getCurrentLoginMembers() {
		List<CurrentLoginMembersVO> list = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement("select * from tblCurrentlyMembers");
			rs = pstmt.executeQuery();
			
			list = SQLHelper.putResult(rs, CurrentLoginMembersVO.class);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();			
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return list;
	}
	
}
