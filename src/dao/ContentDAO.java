package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.SQLHelper;
import sql.ContentLoader;
import utils.DBConnectionMgr;
import vo.ProductVO;

public class ContentDAO implements IDAO {
	
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;
	
	private static ContentDAO instance = null;
	private ContentLoader qlList = null;
	
	private ContentDAO() {
		create();
	}
	
	public void create() {
		createPool();
		initWithSQL();
	}
	
	public void createPool() {
		try {
			pool = DBConnectionMgr.getInstance();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initWithSQL() {
		qlList = new ContentLoader();
	}
	
	public static synchronized ContentDAO getInstance() {
		if(instance == null) {
			instance = new ContentDAO();
		}
		
		return instance;
	}
	
	public String getQL(String command) {
		return qlList.get(command);
	}
	
	/**
	 * 
	 * @param pageType
	 * @param genderType
	 * @param shopType
	 * @return
	 */
	public List<ProductVO> getData(String pageType, String genderType, String shopType) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("전체 데이터 추출"));
			pstmt.setString(1, pageType);
			pstmt.setString(2, genderType);
			pstmt.setString(3, shopType);
			
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, ProductVO.class);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public List<ProductVO> searchAsAge(String pageType, String genderType, String shopType, String ages) {
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("번호 붙여 검색"));
			pstmt.setString(1, pageType);
			pstmt.setString(2, genderType);
			pstmt.setString(3, shopType);
			pstmt.setString(4, "%" + ages + "%");
			
			rs = pstmt.executeQuery();
			list = SQLHelper.putResult(rs, ProductVO.class);
			
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
