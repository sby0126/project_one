package dao;

import com.mysql.jdbc.PreparedStatement;

import utils.DBConnectionMgr;

public class SQLManagerDAO implements IDAO {
	
	private DBConnectionMgr pool;
	private PreparedStatement pstmt;
	
	public SQLManagerDAO() {
		initWithPool();
	}
	
	public void initWithPool() {
		pool = DBConnectionMgr.getInstance();
	}
	
}
