package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import dao.ContentDAO;
import vo.ProductVO;

/**
 * 최근 상점의 데이터를 가져오는 기능입니다.
 * 
 * @author User
 *
 */
public class RecentlyItemService {
	
	private Vector<ProductVO> cards;
	
	public RecentlyItemService(String num) {
		
		System.out.println(num);
		
		String str[] = num.split(",");
		List<String> list = Arrays.asList(str);
		
		List<Integer> idList = new ArrayList<>();
		
		for(String s: list) {
			idList.add(Integer.parseInt(s));
		}
		initWithCards();
		initWithShopDetailService(idList);
		
	}	
	
	/**
	 * 카드 빈을 생성합니다.
	 */
	public void initWithCards() {
		cards = new Vector<ProductVO>();
	}
	
	/**
	 * ID에 맞는 카드 데이터를 가져옵니다.
	 *  
	 * @param list
	 */
	public void initWithShopDetailService(List<Integer> list) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		
		list.forEach(id -> {
			ProductVO vo = contentDAO.findShopDataAsID(id);
			cards.add(vo);
		});
	}

	public Vector<ProductVO> getCards() {
		return cards;
	}

	public void setCards(Vector<ProductVO> cards) {
		this.cards = cards;
	}
	
}
