package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.JSONArray;

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
	
	public final String[] CATEGORY = {
        "", 
        "트렌드", 
        "댄디", 
        "유니크", 
        "레플리카·제작", 
        "스트릿", 
        "클래식수트", 
        "빅사이즈", 
        "슈즈", 
        "액세서리"				
	};
	
	public final String[] AGES = {
			"",
			"10대",
			"20대",
			"30대"
	};
	
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
	
	public String getCategory(String typeValue) {
		try {
			int type = Integer.parseInt(typeValue);
			
			return CATEGORY[type - 100];
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public String getAge(String typeValue) {
		return typeValue + "대";
	}
	
	/**
	 * 
	 * @param pageType
	 * @param genderType
	 * @param shopType
	 * @return
	 */
	public List<ProductVO> getData(String pageType, String genderType, String shopType, String category, String ages) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		if(category != null) {
			if(!category.equals("100")) {
				category = getCategory(category);
			} else {
				category = null;
			}
		}
		
		if(ages != null) {
			if(!ages.equals("all")) {
				ages = getAge(ages);
			} else {
				ages = null;
			}
		}
		
		
		System.out.println("카테고리 : " + category);
		System.out.println("연령대 : " + ages);
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(
					getQL("전체 데이터 추출")
					+ (category != null ? " AND texts LIKE ?" : "")
					+ (ages != null ? " AND texts LIKE ?" : "")
					);
			pstmt.setString(1, pageType);
			pstmt.setString(2, genderType);
			pstmt.setString(3, shopType);
			
			int i = 3;
			
			if(category != null) {
				i += 1;
				pstmt.setString(i, "%" + category + "%");
			}
			
			if(ages != null) {
				i += 1;
				pstmt.setString(i, "%" + ages + "%");
			}			
			
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
	
	/**
	 * 연령대로 JSON 데이터를 필터링합니다. category
	 * 
	 * @param pageType
	 * @param genderType
	 * @param shopType
	 * @param ages
	 * @return
	 */
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
	
	/**
	 * 카테고리로 검색
	 * @param pageType
	 * @param genderType
	 * @param shopType
	 * @param category
	 * @return
	 */
	public List<ProductVO> searchAsCategory(String pageType, String genderType, String shopType, String category) {
		return searchAsAge(pageType, genderType, shopType, category);
	}	
	
	/**
	 * 연령대로 JSON 데이터를 필터링합니다. category
	 * 
	 * @param pageType
	 * @param genderType
	 * @param shopType
	 * @param ages
	 * @return
	 */
	public List<ProductVO> searchAsAny(String pageType, String genderType, String shopType, String category, String ages) {
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("나이 또는 카테고리로 필터링"));
			pstmt.setString(1, pageType);
			pstmt.setString(2, genderType);
			pstmt.setString(3, shopType);
			pstmt.setString(4, "%" + category + "%");
			pstmt.setString(5, "%" + ages + "%");
			
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
	
	public JSONArray getItemCategories() {
		ResultSet rs = null;
		JSONArray categories = new JSONArray();
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("아이템 카테고리 생성"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				categories.add( rs.getString("category") );
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return categories;
	}
		
	
}
