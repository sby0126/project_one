package service;

import dao.ContentDAO;

public class ContentService {
	private ContentDAO contentDAO;
	
	public ContentService() {
		contentDAO = ContentDAO.getInstance();
	}
	
	public ContentDAO getDAO() {
		return contentDAO;
	}
	
}
