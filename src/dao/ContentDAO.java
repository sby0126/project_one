package dao;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;

import core.SQLHelper;
import sql.ContentLoader;
import utils.DBConnectionMgr;
import vo.CartNPayVO;
import vo.InterestVO;
import vo.MyShopVO;
import vo.ProductVO;
import vo.SearchVO;

public class ContentDAO implements IDAO {
	
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;
	
	private static ContentDAO instance = null;
	private ContentLoader qlList = null;
	
	public final String[] CATEGORY = {
        "", 
        "트랜드", 
        "댄디", 
        "유니크", 
        "레플리카·제작", 
        "스트릿", 
        "클래식수트", 
        "빅사이즈", 
        "슈즈", 
        "액세서리"				
	};
	
	public String[] ITEM_CATEGORY = {
			"", "ALL", "상의", "아우터", "하의", "트레이닝", "수트", "신발", "가방", "액세서리"		
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
	
	public String getCategory(String pageType, String typeValue) {
		int type = 100;
		
		switch(pageType) {
		default:
		case "shop":
			type = Integer.parseInt(typeValue);
			
			return CATEGORY[type - 100];			
		case "item":
			type = Integer.parseInt(typeValue);
			
			return ITEM_CATEGORY[type - 100];
		}
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
	public List<ProductVO> getData(String pageType, String genderType, String shopType, String category, String ages, int start, int end) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		if(category != null) {
			if(!category.equals("100")) {
				category = getCategory(pageType, category);
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
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(
					getQL("전체 데이터 추출2")
					+ (category != null ? " AND texts LIKE ?" : "")
					+ (ages != null ? " AND texts LIKE ?" : "")
					+ " group by contentUrl"
					+ " ORDER BY cnt desc"
					+ " limit ?, ?"
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
			
			i += 1;
			pstmt.setInt(i, 0);
			
			i += 1;
			pstmt.setInt(i, end);			
			
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
	
	public List<ProductVO> searchData(String pageType, String genderType, String shopType, String category, String ages, String searchKeyword, int start, int end) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		if(category != null) {
			if(!category.equals("100")) {
				category = getCategory(pageType, category);
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
		
		searchKeyword = URLDecoder.decode(searchKeyword);
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(
					getQL("전체 데이터 추출2")
					+ (category != null ? " AND texts LIKE ?" : "")
					+ (ages != null ? " AND texts LIKE ?" : "")
					+ (searchKeyword != null ? " AND title like ?" : "")
					+ " limit ?, ?"
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
			
			if(searchKeyword != null) {
				i += 1;
				pstmt.setString(i, "%" + searchKeyword + "%");
			}			
			
			i += 1;
			pstmt.setInt(i, 0);
			
			i += 1;
			pstmt.setInt(i, end);					
			
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
		List<String> rawCategories = new ArrayList<>();
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("아이템 카테고리 생성"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				categories.add( rs.getString("category") );
				rawCategories.add( rs.getString("category") );
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		ITEM_CATEGORY = rawCategories.toArray(new String[9]);
		
		return categories;
	}
	
	/** 
	 * 브랜드 명을 유일 키인 ID로 찾습니다.
	 * 
	 * @param id
	 * @return
	 */
	public String findShopName(int id) {
		ResultSet rs = null;
		String shopName = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("브랜드 명 찾기"));
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				shopName = rs.getString(1);
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return shopName;
		
	}
	
	public List<ProductVO> findThumbnail(String shopName) {
		ResultSet rs = null;
		List<ProductVO> list = null;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement(getQL("브랜드 썸네일 찾기"));
			pstmt.setString(1, shopName);
			
			rs = pstmt.executeQuery();
			
			List<ProductVO> myList = SQLHelper.putResult(rs, ProductVO.class);
			
			if(myList != null) {
				list = myList;
				
				System.out.println(Arrays.toString(list.toArray()));
			}
			
			
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
	 * 특정 쇼핑몰의 전체 상품을 검색합니다 (DB에 있는 것만 찾습니다)
	 * 
	 * @param pageType
	 * @param shopName
	 * @return
	 */
	public List<ProductVO> searchAsShopName(String pageType, String shopName) {
		ResultSet rs = null;
		List<ProductVO> retList = null;
		
		try {
			conn = pool.getConnection();
			// pstmt = conn.prepareStatement(getQL("브랜드 별 검색"));
			pstmt = conn.prepareStatement("CALL getItem(?)");
			
//			pstmt.setString(1, pageType);
			pstmt.setString(1, shopName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				List<ProductVO> list = SQLHelper.putResult(rs, ProductVO.class);
				retList = list;
			}
			
		}  catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return retList;
		
	}
	
	public List<ProductVO> getDetail(String title, int price) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		String sql = null;
		
		try {
			conn = pool.getConnection();
			sql = "select b.id, b.title, b.price, a.imgUrl, b.link FROM tblImageHash a, tblproduct b"
				  + " where title = ? and price = ?"
				  + " and a.imgUrl = b.contentUrl"
				  + " group by contentUrl";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setInt(2, price);
			
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
	
	public boolean insertDetail(String id, List<CartNPayVO> p) {
		
		boolean success = false;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			
			String query = "select ctmid from tblCustomer where ctmid = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {id = rs.getString("ctmid");}
			
			for(CartNPayVO list : p) {
				query = "insert into cartNpay(id, title, price, amount, contentUrl, link) "
					  + "values(?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, list.getTitle());
				pstmt.setInt(3, list.getPrice());
				pstmt.setInt(4, list.getAmount());
				pstmt.setString(5, list.getContentUrl());
				pstmt.setString(6, list.getLink());
			}			
			
			if(pstmt.executeUpdate() > 0) {
				conn.commit();
				success = true;
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return success;
	}
	
	/**
	 * ID로 상품 데이터 획득
	 * 
	 * @param id
	 * @return
	 */
	public ProductVO findShopDataAsID(int id) {
		List<ProductVO> list = null;
		ResultSet rs = null;
		
		ProductVO vo = null;
		
		try {
			conn = pool.getConnection();
			
			pstmt = conn.prepareStatement(getQL("상품 찾기"));
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			list = SQLHelper.putResult(rs, ProductVO.class);
			
			if(list != null) {
				vo = list.get(0);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return vo;
	}
	
	public List<SearchVO> getBestKeyword() {
		ResultSet rs = null;
		
		List<SearchVO> list = new ArrayList<>();
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("SELECT *, COUNT(keyword) FROM tblSearch GROUP BY keyword ORDER BY COUNT(keyword) DESC");

			rs = pstmt.executeQuery();
			
			while(rs.next()) {

				SearchVO vo = new SearchVO();
				
				vo.setKeyword( rs.getString(1) );
				vo.setRegdate( rs.getString(2) );
				vo.setCount( rs.getInt(3) );
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return list;	
	}
	
	public boolean insertKeywordToBestList(String keyword, String regdate) {
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("insert into tblSearch values(?, ?)");
			pstmt.setString(1, keyword);
			
			// 문자열에서 시간으로 변환 설정
			java.sql.Timestamp t = java.sql.Timestamp.valueOf(regdate);
			pstmt.setTimestamp(2, t);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return true;
	}
	
	/**
	 * 관심 상품을 DB에 추가합니다.
	 * 
	 * @param customerId
	 * @param productId
	 * @return
	 */
	public boolean insertInterest(String customerId, int productId) {
		
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("insert into tblInterest(cust_id, product_id) values(?, ?)");
			pstmt.setString(1, customerId);
			pstmt.setInt(2, productId);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
	}
	
	/**
	 * 관심 상품을 DB에 추가합니다.
	 * 
	 * @param customerId
	 * @param productId
	 * @return
	 */
	public boolean deleteInterest(String customerId, int productId) {
		
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("delete from tblInterest where cust_id = ? and product_id = ?");
			pstmt.setString(1, customerId);
			pstmt.setInt(2, productId);

			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
	}
	/**
	 * 관심 상품을 DB에 추가합니다.
	 * 
	 * @param customerId
	 * @param productId
	 * @return
	 */
	public boolean deleteAllInterest(String customerID) {
		
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("delete from tblInterest where cust_id = ?");
			pstmt.setString(1, customerID);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
	}
	
	/**
	 * 관심 상품을 List로 가져옵니다.
	 * @param customerId
	 * @param productId
	 * @return
	 */
	public List<InterestVO> getInterests(String customerId) {
		
		List<InterestVO> list = null;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("select distinct * from tblInterest where cust_id = ?");
			pstmt.setString(1, customerId);
			
			rs = pstmt.executeQuery();
			
			list = SQLHelper.putResult(rs, InterestVO.class);			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return list;
	}
	
	public boolean addMyShop(String customerID, int shopId) {
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("addMyShop"));
			pstmt.setString(1, customerID);
			pstmt.setInt(2, shopId);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
	}
	
	public List<MyShopVO> getMyShop(String customerID) {
		List<MyShopVO> myShopList = null;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("getMyShop"));
			pstmt.setString(1, customerID);
			
			rs = pstmt.executeQuery();
			
			myShopList = SQLHelper.putResult(rs, MyShopVO.class);	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return myShopList;
	}
	
	public boolean deleteMyShop(String customerID, int shopId) {
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("deleteMyShop"));
			pstmt.setString(1, customerID);
			pstmt.setInt(2, shopId);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
	}	
	public boolean deleteAllMyShop(String customerID) {
		boolean isOK = false;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(getQL("deleteAllMyShop"));
			pstmt.setString(1, customerID);
			
			if(pstmt.executeUpdate() > 0) {
				isOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		return isOK;
	}	
}
