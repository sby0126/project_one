package router;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import utils.AdminUtil;

@WebListener
public class SessionListener implements HttpSessionListener {
	
	private int currentUser = 0;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		currentUser++;
		
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("google.com", 80));
			
			String id = se.getSession().getId().substring(0, 6);
			
			// AdminUtil.getInstance().loggingIP(id, socket.getLocalAddress().toString().substring(1));
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(currentUser);
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		currentUser--;
		String id = se.getSession().getId().substring(0, 6);
		// AdminUtil.getInstance().eraseIPFromLog(id);
		
		System.out.println(currentUser);
	}
}
