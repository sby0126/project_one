package service;

import dao.PaymentDAO;
import vo.OrderVO;

public class OrderService {
	public boolean processOrder(OrderVO order) {
		PaymentDAO paymentDAO = PaymentDAO.getInstance();
		return paymentDAO.insertOrder(order);
	}
}
