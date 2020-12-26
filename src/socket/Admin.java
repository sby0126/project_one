package socket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Admin  {
	
	private static Session admin = null;

	@OnOpen
	public void open(Session userSession) {
		if (admin != null) {
			try {
				admin.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		admin = userSession;
		
		for(String key : Client.getKeys()) {
			this.visit(key);
		}
	}
	
	public void onMessage(String message, Session userSession) throws IOException {
		
		JSONParser parser = new JSONParser();
		
		try {
			Object raw = parser.parse(message);
			JSONObject data = (JSONObject)raw;
			
			String key = (String)data.get("key");
			String msg = (String)data.get("message");
			
			Client.sendMessage(key, msg);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@OnClose
	public void close() {
		admin = null;
	}
	
	private static void send(String message) {
		if (admin != null) {
			try {
				admin.getBasicRemote().sendText(message);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	  public static void visit(String key) {
	    send("{\"status\":\"visit\", \"key\":\"" + key + "\"}");
	  }
	 
	  public static void sendMessage(String key, String message) {
	    send("{\"status\":\"message\", \"key\":\"" + key + "\", \"message\":\"" + message + "\"}");
	  }
	  
	  public static void bye(String key) {
	    send("{\"status\":\"bye\", \"key\":\"" + key + "\"}");
	  }
}
