package service;

import java.util.List;

import dao.ContentDAO;
import vo.SearchVO;

public class BestItemListService {
	public List<SearchVO> getList() {
		// 최근 인기 검색어를 DB에서 가져옵니다.
		ContentDAO contentDAO = ContentDAO.getInstance();
		List<SearchVO> searchList = contentDAO.getBestKeyword();
		
		return searchList;
	}
}
