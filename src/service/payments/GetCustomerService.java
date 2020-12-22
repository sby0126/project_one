package service.payments;

import java.util.HashMap;

import dao.CustomerDAO;
import vo.CustomerVO;

public class GetCustomerService {
	
	public HashMap<String, String> getCustomer(String id) {
		HashMap<String, String> info = new HashMap<String, String>();
		
		CustomerDAO customerDAO = CustomerDAO.getInstance();
		CustomerVO vo = customerDAO.getMember(id);
		
		info.put("name", vo.getName());
		info.put("email", vo.getEmail());
		info.put("phone", vo.getTel());
		info.put("address", vo.getAddress());
		info.put("zipcode", vo.getZipCode());
		
		return info;
	}
}
