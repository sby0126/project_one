package service;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class RecentlyShopService {
	
	private Vector<ShopDetailService> cards;
	
	/**
	 * This class can create a card list from a local storage of client browser.
	 * @param id
	 */
	public RecentlyShopService(Integer ... id) {
		List<Integer> idList = Arrays.asList(id);
		
		initWithShopDetailService(idList);
	}
	
	/**
	 * 
	 * @param list
	 */
	public void initWithShopDetailService(List<Integer> list) {
		list.forEach(id -> {
			ShopDetailService detail = new ShopDetailService(id, "item");
			cards.add(detail);
		});
	}

	public Vector<ShopDetailService> getCards() {
		return cards;
	}

	public void setCards(Vector<ShopDetailService> cards) {
		this.cards = cards;
	}
	
}
