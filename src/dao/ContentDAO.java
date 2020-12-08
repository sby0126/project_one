package dao;

import sql.ContentLoader;

public class ContentDAO implements IDAO {
	
	private static ContentDAO instance = null;
	private ContentLoader qlList = null;
	
//	class ContentParams {
//		public int startNumber;
//		public int endNumber;
//		String pageType;
//		String shopType;
//		String gndr;
//		
//		public ContentParams(int startNumber, int endNumber, String pageType, String shopType, String gndr) {
//			this.startNumber = startNumber;
//			this.endNumber = endNumber;
//			this.pageType = pageType;
//			this.shopType = shopType;
//			this.gndr = gndr;
//		}
//		
//	}
	
	private ContentDAO() {
		create();
	}
	
	public void create() {
		initWithSQL();
	}
	
	public void initWithSQL() {
		qlList = new ContentLoader();
	}
	
	public synchronized ContentDAO getInstance() {
		if(instance == null) {
			instance = new ContentDAO();
		}
		
		return instance;
	}
	
}
