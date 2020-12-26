package service;

import java.util.ArrayList;
import java.util.List;

import vo.ProductVO;

public class MyShopService {
	
	/**
	 * 특정 샵의 아이템을 가져옵니다. 
	 * 
	 * @param ids
	 * @return
	 */
	public List<ProductVO> getList(Integer...ids) {
		
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

}
