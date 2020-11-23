package manager;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	}
	
	public void setMainApplication(ServletContext context) {
		mContext = context;
		if(mContext != null) {
			isReady = true;
		}
	}
	
	public void makeConfigFile() {
		String root = getApplicationPath();
		Path outputPath = Paths.get(root, "output.json");
		
		System.out.println(outputPath);
		
		try (FileWriter writer = new FileWriter(outputPath.toString())) {
			writer.write(mConfig.toJSONString());	
		} catch(Exception ex) {
			
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
