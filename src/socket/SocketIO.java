package socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@ServerEndpoint("/chat")
public class SocketIO {
	
	private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());
	
	@OnOpen
	public void open(Session userSession) {
		sessionUsers.add(userSession);
	}
	
	@OnMessage
	public void onMessage(String message, Session userSession) throws IOException {
		
//		JSONObject status = new JSONObject();
//		JSONArray list = new JSONArray();
//		
//		List<String> userIds = new ArrayList<>();
//		
//		sessionUsers.forEach(user -> {
//			list.add(user.getId());
//		});
//		
//		status.put("users", list);
//		
//		sessionUsers.forEach(session -> {
//			try {
//				
//				if (session == userSession) {
//					return;
//				}
//				
//				status.put("message", message);
//				
//				session.getBasicRemote().sendText(status.toJSONString());
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		sessionUsers.forEach(session -> {
		try {
			
			if (session == userSession) {
				return;
			}

			session.getBasicRemote().sendText(message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});		
	}
	
	@OnClose
	public void close(Session userSession) {
		sessionUsers.remove(userSession);
		
	}
	
	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}
	
}
