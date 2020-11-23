package manager;

import java.io.FileWriter;

import javax.servlet.ServletContext;

import org.json.simple.JSONObject;

public class DataManager {
	public static DataManager inst = null;
	public static final String EMPTY_PATH = "./";
	public static final String ADMIN_FILE = "./admin.json";
	
	private JSONObject mConfig;
	private ServletContext mContext;
	private boolean isReady;
	
	public DataManager() {
		mConfig = new JSONObject();
		mConfig.put("테스트", "10");
		
		isReady = false;
		
		try (FileWriter writer = new FileWriter("output.json")) {
			writer.write(mConfig.toJSONString());	
		} catch(Exception ex) {
			
		}
		
	}
	
	public void setMainApplication(ServletContext context) {
		mContext = context;
		if(mContext != null) {
			isReady = true;
		}
	}
	
	public String getApplicationPath() {
		if(!isReady) {
			return DataManager.EMPTY_PATH;
		}
		return mContext.getRealPath(".");
	}
	
	public static synchronized DataManager getInstance() {
		if(inst == null) {
			inst = new DataManager();
		}
		
		return inst;
	}
	
}
