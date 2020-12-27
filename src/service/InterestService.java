package service;

import java.util.List;

import dao.ContentDAO;
import vo.InterestVO;

public class InterestService {

	public boolean insertInterest(String customerId, int productId) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		boolean isOK = contentDAO.insertInterest(customerId, productId);
		
		return isOK;
	}
	
	public boolean deleteInterest(String customerId, int productId) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		boolean isOK = contentDAO.deleteInterest(customerId, productId);
		
		return isOK;
	}
	
	public boolean deleteAllInterest(String customerId) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		boolean isOK = contentDAO.deleteAllInterest(customerId);
		
		return isOK;
	}
	
	public List<InterestVO> getInterest(String customerId) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		List<InterestVO> list = contentDAO.getInterests(customerId);
		
		return list;
	}
	
}
