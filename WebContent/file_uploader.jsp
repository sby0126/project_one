<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page import="java.util.List"%>
<%@ page import="java.util.UUID"%> 
<%@ page import="java.io.File"%> 
<%@ page import="java.io.FileOutputStream"%> 
<%@ page import="java.io.InputStream"%> 
<%@ page import="java.io.OutputStream"%> 
<%@ page import="org.apache.commons.fileupload.FileItem"%> 
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%> 
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<%

String return1 = "";
String return2 = "";
String return3 = "";
String name = "";

	if(ServletFileUpload.isMultipartContent(request)) {
		ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());

		uploadHandler.setHeaderEncoding("UTF-8");

		List<FileItem> items = uploadHandler.parseRequest(request);

		for(FileItem item : items) {
			if(item.getFieldName().equals("callback")) {
				return1 = item.getString("UTF-8");
			} else if(item.getFieldName().equals("callback_func")) {
				return2 = "?callback_func="+item.getString("UTF-8");
			} else if(item.getFieldName().equals("Filedata")) {
	
				if(item.getSize() > 0) {
					String ext = item.getName().substring(item.getName().lastIndexOf(".")+1);
					String defaultPath = request.getServletContext().getRealPath("/");
					String path = defaultPath + "upload" + File.separator;
					File file = new File(path);
			
					if(!file.exists()) { file.mkdirs(); }

					String realname = UUID.randomUUID().toString() + "." + ext;

					InputStream is = item.getInputStream();

					OutputStream os=new FileOutputStream(path + realname);

					int numRead; 
					byte b[] = new byte[(int)item.getSize()];
					while((numRead = is.read(b,0,b.length)) != -1) { os.write(b,0,numRead); }
					if(is != null)  is.close();
			
					os.flush();
					os.close();

					System.out.println("path : "+path); 
					System.out.println("realname : "+realname);

					return3 += "&bNewLine=true&sFileName="+name+"&sFileURL=/upload/"+realname;
				} else { 
					return3 += "&errstr=error"; 
				} 
			} 
		} 

	}	
	response.sendRedirect(return1+return2+return3);
%>


