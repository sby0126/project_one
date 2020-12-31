package command;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import action.ActionResult;
import utils.DBConnectionMgr;

public class ItemUploaderCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);

        String saveFolder = "/images";
        
        int fileSize = 5 * 1024 * 1024;
        ServletContext context = request.getServletContext();
        String realFolder = context.getRealPath(saveFolder);

        System.out.println(realFolder);
		
		FileRenamePolicy policy = new DefaultFileRenamePolicy(); 
		
		// request 객체를 전송하여 파일을 서버로 업로드 한다.
		MultipartRequest multi = new MultipartRequest(
				request, 
				realFolder, 
				fileSize,
				"UTF-8",
				policy);
													
		String pageType = multi.getParameter("pageType");
		String genderType = multi.getParameter("genderType");
		String shopType = multi.getParameter("shopType");
		
		String targetPath = "/images/" + pageType + "/" + genderType + "/" + shopType;
		
		// 할인할 경우.
		if(pageType.equals("sale")) {
			targetPath = "/images/" + pageType + "/" + genderType;
		}
		
		String realFilePath = context.getRealPath(targetPath);
		
		try {			
			File file = FileUtils.getFile(realFolder + "/" + multi.getOriginalFileName("contentUrl"));
			File fileToMove = FileUtils.getFile(realFilePath);
			
			FileUtils.moveFileToDirectory(file, fileToMove, false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String shopName = multi.getParameter("shopName");
		String texts = multi.getParameter("texts");
		
		String contentUrl = multi.getOriginalFileName("contentUrl");
		
		String title = multi.getParameter("title");
		String price = multi.getParameter("price");
		String term = multi.getParameter("term");
		String link = multi.getParameter("link");
		
		// DAO와 서비스 없이 DB에 직접 연결합니다.
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean isUpdated = false;
		
		try {
			
			conn = pool.getConnection();
			pstmt = conn.prepareStatement("insert into tblproduct(pageType, genderType, shopType, shopName, texts, contentUrl, title, price, term, link) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
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
			return null;
		}
		
		response.sendRedirect("/index.jsp");
		
		return null;
	}
	
}
