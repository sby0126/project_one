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
public class RecentlyShopService {
	
	private Vector<ProductVO> cards;
	
	public RecentlyShopService(String num) {
		
		String str[] = num.split(",");
		List<String> list = Arrays.asList(str);
		
		List<Integer> idList = new ArrayList<>();
		
		for(String s: list) {
			
			try {
				int n = Integer.parseInt(s);
				idList.add(n);
			} catch(NumberFormatException e) {
				//
			}
		}
		
		idList = uniqueArray(idList);	
		
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
	 * 중복 제거
	 * 
	 * @param list
	 * @return
	 */
	public List<Integer> uniqueArray(List<Integer> list) {
		List<Integer> idList = new ArrayList<Integer>();
		for(Integer val: list) {
		  if(!idList.contains(val)) {
			  idList.add(val);
		  }
		}
		
		return idList;

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
