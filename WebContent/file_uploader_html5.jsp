<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%> 
<%@ page import="java.util.UUID"%> 
<%@ page import="java.io.File"%> 
<%@ page import="java.net.*"%> 
<%@ page import="java.io.FileOutputStream"%> 
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%> 
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%> 
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%
	String sFileInfo = "";
	String name = request.getHeader("file-name");
	String ext = name.substring(name.lastIndexOf(".")+1);
	String defaultPath = request.getServletContext().getRealPath("/");
	String path = defaultPath + "upload" + File.separator;
	
	// 파일 생성
	File file = new File(path);
	
	// 새로운 폴더 생성
	if(!file.exists()) { 
		file.mkdirs(); 
	}
	
	// 파일 이름 램덤으로 설정하기
	String realname = UUID.randomUUID().toString() + "." + ext; 
	InputStream is = request.getInputStream();
	OutputStream os = new FileOutputStream(path + realname); 
	int numRead;
	
	byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
	while((numRead = is.read(b,0,b.length)) != -1) {
		os.write(b,0,numRead);
	}
	
	if(is != null) { 
		is.close(); 
	}
	
	os.flush(); 
	os.close(); 
	System.out.println("path : "+path); 
	System.out.println("realname : "+realname);
	
	
%>
	
<%
	// 동적으로 태그 삽입
	sFileInfo += "&bNewLine=true&sFileName="+name+"&sFileURL="+"/upload/"+realname;
	String ms = new String(sFileInfo.toString());
	out.println(ms);
%>