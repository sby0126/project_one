package router;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import utils.Base64Util;
import utils.DBConnectionMgr;

@WebServlet("/myadmin/*")
public class AdminRouter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminRouter() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void deleteFile(String realFileName) {
		// 파일이 존재하는가?
		File file = new File(realFileName);
		if(file.exists()) {
			// 존재한다면 삭제 처리합니다.
			file.delete();
		}				
	}
	
	public void openExplorer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String platform = System.getProperty("os.name");		
		String filename = request.getParameter("filename");
		
		// 실행 환경이 윈도우즈 인가?
		if(platform.indexOf("Windows") >= 0) {
	    	try {
				Runtime.getRuntime().exec("explorer.exe /select," + filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		
    	response.sendRedirect("/admin");	
	}
	public void deleteFileOnce(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("filename");
		
		try {
			String realFileName = request.getServletContext().getRealPath(filename);
			
			// 파일이 존재하는가?
			deleteFile(realFileName);				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("/admin");	
	}
	
	public void deleteFileMultiple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> files = Arrays.asList(request.getParameterValues("file"));
		
		for(String s : files) {
			String realFileName = request.getServletContext().getRealPath(s);
			deleteFile(realFileName);		
		}
		
		response.sendRedirect("/admin");	
	}
	
	public void uploadProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 기본 매개변수를 가져옵니다.
		String sql = request.getParameter("sql");
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		int price = Integer.parseInt(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		String regdate = request.getParameter("regdate");
		
		boolean isUpdated = false;
		
		// SQL 문이 없습니다.
		if(sql == null) {
			response.sendError(401);
			return;
		}
		
		// DAO와 서비스 없이 DB에 직접 연결합니다.
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			// insert into tblExtItem(id, title, price, stock, regdate) values(?, ?, ?, ?, ?) 
			pstmt.setInt(1, id);
			pstmt.setString(2, title);
			pstmt.setInt(3, price);
			pstmt.setInt(4, stock);
			pstmt.setString(5, regdate);
			
			if(pstmt.executeUpdate() > 0) {
				isUpdated = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		if(!isUpdated) {
			response.sendError(402);
			return;
		}
		
		JSONObject statusText = new JSONObject();
		statusText.put("status", "success");
		
		
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(statusText.toJSONString());
	}
	
	public void uploadItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 기본 매개변수를 가져옵니다.
		String sql = request.getParameter("sql");
		int id = Integer.parseInt(request.getParameter("id"));
		String pageType = request.getParameter("pageType");
		String genderType = request.getParameter("genderType");
		String shopType = request.getParameter("shopType");
		String shopName = request.getParameter("shopName");
		String texts = request.getParameter("texts");
		String contentUrl = request.getParameter("contentUrl");
		String title = request.getParameter("title");
		String price = request.getParameter("price");
		String term = request.getParameter("term");
		String link = request.getParameter("link");
		
		byte[] rawBytes = Base64Util.decode(contentUrl);
		
//		if(!pageType.equals("sale")) {
//			
//			Paths.get(request.getServletContext().getRealPath("/uploads"), pageType, genderType, shopType, UUID.randomUUID() + ".png");
//			
//			String realPath =  + "/" + pageType + "";
//			Files.copy(new ByteArrayInputStream(bytes),)	
//		} else {
//			
//		}
//		
		
		
		boolean isUpdated = false;
		
		// SQL 문이 없습니다.
		if(sql == null) {
			response.sendError(401);
			return;
		}
		
		// DAO와 서비스 없이 DB에 직접 연결합니다.
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			// insert into tblproduct(pageType, genderType, shopType, shopName, texts, contentUrl, title, price, term, link) values(?, ?, ?, ?, ?, ?, ?, ?,?,?)
			pstmt.setString(1, pageType);
			pstmt.setString(2, genderType);
			pstmt.setString(3, shopType);
			pstmt.setString(4, shopName);
			pstmt.setString(5, texts);
			pstmt.setString(6, contentUrl);
			pstmt.setString(7, title);
			pstmt.setString(8, price);
			pstmt.setString(9, term);
			pstmt.setString(10, link);
			
			
			if(pstmt.executeUpdate() > 0) {
				isUpdated = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		
		if(!isUpdated) {
			response.sendError(402);
			return;
		}
		
		JSONObject statusText = new JSONObject();
		statusText.put("status", "success");
		
		
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(statusText.toJSONString());
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqeustURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = reqeustURI.substring(contextPath.length());
		String path = request.getPathInfo();

		if (path.equals("/openFileBrowser.do")) { // 폴더 열기 (Windows 전용)
			openExplorer(request, response);
	    	
		} else if(path.equals("/fileDelete.do")) { // 파일 삭제
			deleteFileOnce(request, response);
		} else if(path.equals("/multipleFileDelete.do")) { // 다중 파일 삭제
			deleteFileMultiple(request, response);
		} else if(path.equals("/uploadProduct.do")) {
			uploadProduct(request, response);
		} else if(path.equals("/uploadItem.do")) {
			uploadItem(request, response);
		}
		
	}

}
