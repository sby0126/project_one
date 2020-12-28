package service;

import dao.CustomerDAO;
import vo.CustomerVO;

public class FindIdService {
	
	public String getId(String name, String email) {

		CustomerDAO customerDAO = CustomerDAO.getInstance();
		
		CustomerVO c = customerDAO.findId(name, email);
		
		if(c != null) {
			return c.getId();	
		}
		
		return null;
		
	}
}
