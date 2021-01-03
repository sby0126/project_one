package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.SQLHelper;
import utils.DBConnectionMgr;
import vo.OrderVO;
import vo.PaymentVO;

public class PaymentDAO implements IDAO {
	private DBConnectionMgr pool; 
	private Connection conn;
	private PreparedStatement pstmt;
	
	private static PaymentDAO instance = null;
	
	public static synchronized PaymentDAO getInstance() {
		if(instance == null) {
			instance = new PaymentDAO();
		}
		
		return instance;
	}
	
	public void createPool() {
		try {
			pool = DBConnectionMgr.getInstance();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private PaymentDAO() {
		create();
	}
	
	public void create() {
		createPool();
	}
	
	public boolean insertPayment(PaymentVO vo) {
		
		boolean isOK = false;
		
		String query = "INSERT INTO tblPayment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
		// String query = "call payment(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, vo.getImpUid());
			pstmt.setString(2, vo.getBuyerName());
			pstmt.setString(3,  vo.getBuyerEmail());
			pstmt.setString(4,  vo.getBuyerTel());
			pstmt.setString(5, vo.getBuyerAddr());
			pstmt.setString(6, vo.getBuyerPostcode());
			pstmt.setInt(7, vo.getProductId());
			pstmt.setString(8, vo.getProductName());
			pstmt.setInt(9, vo.getPaidAmount()); 
			pstmt.setString(10, vo.getMerchantUid());
			pstmt.setString(11, vo.getPaymentStatus());
			
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
	
	public boolean updatePayment(String imp_uid, String payment_status) {
		boolean isOK = false;
		
		try {

			conn = pool.getConnection();
			pstmt = conn.prepareStatement("update tblPayment set payment_status = ? where imp_uid = ?");
			pstmt.setString(1, payment_status);
			pstmt.setString(2, imp_uid);
			
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
	
	public PaymentVO getPayment(String imp_uid) {
		boolean isOK = false;
		ResultSet rs = null;
		String query = "select * from tblPayment where imp_uid = ?";
		PaymentVO payment = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, imp_uid);
			
			rs = pstmt.executeQuery();
			
			List<PaymentVO> list = SQLHelper.putResult(rs, PaymentVO.class);
			
			if(!list.isEmpty()) {
				payment = list.get(0);	
			}
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		
		return payment;
	}
	
	public boolean insertOrder(OrderVO order) {
		boolean isOK = false;
		
		String sql = "insert into tblOrder(user_id, product_name, order_amount) values(?, ?, ?)";
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, order.getUserId());
			pstmt.setString(2, order.getProductName());
			pstmt.setInt(3, order.getOrderAmount());
			
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
	
	public List<OrderVO> getOrder(int orderNo) {
		
		List<OrderVO> list = null;
		ResultSet rs = null;
		
		try {
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("select * from tblOrder where order_no = ?");
			pstmt.setInt(1, orderNo);
			
			rs = pstmt.executeQuery();
			
			list = SQLHelper.putResult(rs, OrderVO.class);
			
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
