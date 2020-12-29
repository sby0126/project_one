package service;

import java.util.ArrayList;
import java.util.List;

import dao.ContentDAO;
import vo.MyShopVO;
import vo.ProductVO;

public class MyShopService {
	
	/**
	 * 특정 샵의 아이템을 가져옵니다. 
	 * 
	 * @param ids
	 * @return
	 */
	public List<ProductVO> getList(List<Integer> ids) {
		
		List<List<ProductVO>> demensionalList = new ArrayList<>(); 
		List<ProductVO> myList = new ArrayList<>();
		
		// 아이템 목록을 획득합니다.
		for(Integer id : ids) {
			ShopDetailService service = new ShopDetailService(id, "item");
			
			List<ProductVO> list = service.getList();
			demensionalList.add(list);
		}
		
		demensionalList.forEach(myList::addAll);
		
		return myList;
	}
	
	/**
	 * 마이샵을 추가합니다.
	 * 
	 * @param customerID
	 * @param shopId
	 * @return
	 */
	public boolean addMyShop(String customerID, int shopId) {
		boolean isOK = false;
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		isOK = contentDAO.addMyShop(customerID, shopId);
		
		return isOK;
	}
	
	/**
	 * 마이샵을 삭제합니다.
	 * 
	 * @param customerID
	 * @param shopId
	 * @return
	 */
	public boolean deleteMyShop(String customerID, int shopId) {
		boolean isOK = false;
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		isOK = contentDAO.deleteMyShop(customerID, shopId);
		
		return isOK;
	}
	
	/**
	 * 마이샵을 전부 삭제합니다.
	 * 
	 * @param customerID
	 * @param shopId
	 * @return
	 */
	public boolean deleteAllMyShop(String customerID) {
		boolean isOK = false;
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		isOK = contentDAO.deleteAllMyShop(customerID);
		
		return isOK;
	}
	
	/**
	 * customerID에 대한 마이샵 목록을 List<ProductVO>로 반환합니다.
	 * 
	 * @param customerID
	 * @return
	 */
	public List<ProductVO> getMyShop(String customerID) {
		
		List<ProductVO> list = null;
		
		// DAO를 통해 마이샵 목록을 가져옵니다.
		ContentDAO contentDAO = ContentDAO.getInstance();
		List<MyShopVO> myShopList = contentDAO.getMyShop(customerID);
		
		List<Integer> iList = new ArrayList<>();
		
		// 마이샵 목록에서 샵 ID를 추출합니다.
		myShopList.forEach(i -> {
			iList.add(i.getShopId());
		});
		
		// 리스트로 변환합니다.
		list = getList(iList);
		
		return list;
	}
	
	/**
	 * Get the 'MyShop' that has an id value from database named 'mydb'
	 * 
	 * @param customerID
	 * @return
	 */
	public List<Integer> getIdList(String customerID) {
		
		// DAO를 통해 마이샵 목록을 가져옵니다.
		ContentDAO contentDAO = ContentDAO.getInstance();
		List<MyShopVO> myShopList = contentDAO.getMyShop(customerID);
		
		List<Integer> iList = new ArrayList<>();
		
		// 마이샵 목록에서 샵 ID를 추출합니다.
		for(MyShopVO vo : myShopList) {
//			System.out.println(vo.getShopId());
			iList.add(vo.getShopId());
		}
		
		return iList;
	}
	
	public boolean isValidMyShop(int shopId, String customerID) {
		
		List<Integer> idList = this.getIdList(customerID);
				
		return !idList.isEmpty() && idList.contains(shopId);
	}

}
