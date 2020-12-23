package service;

import java.util.List;

import dao.ContentDAO;
import vo.InterestVO;

public class InterestService {

	
	public boolean checkInterest(String customerId, int productId) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		boolean isOK = contentDAO.checkInterest(customerId, productId);
		
		return isOK;
	}
	
	public List<InterestVO> getInterest(String customerId) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		List<InterestVO> list = contentDAO.getInterests(customerId);
		
		return list;
	}
	
}
