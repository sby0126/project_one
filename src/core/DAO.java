package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DAO {

	
	protected Connection conn;
	protected PreparedStatement pstmt;
	protected DBConnectionMgr pool;
	
	interface Action<T> {
		public List<T> execute(Connection conn, PreparedStatement pstmt, ResultSet rs);
	}
	
	interface CallbackFunction {
		public boolean call(Connection conn, PreparedStatement pstmt, ResultSet rs);
	}
	
	public DAO() {
		try {			
			pool = DBConnectionMgr.getInstance();
		}  catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public <T> List<T> getListMember(Action<T> action, String query) {
		
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			list = action.execute(conn, pstmt, rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public boolean execute(CallbackFunction func, 
			Connection conn, 
			PreparedStatement pstmt, 
			ResultSet rs, String query) {
		boolean result = false;
		
		try {
			result = func.call(conn, pstmt, rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return result;
		
	}
}
