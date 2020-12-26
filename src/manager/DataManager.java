package manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataManager {
	public static DataManager inst = null;
	public static final String EMPTY_PATH = "./";
	public static final String ADMIN_FILE = "./admin.json";
	
	private JSONObject mConfig;
	private ServletContext mContext;
	private boolean isReady;
	
	public List<String> staffMembers;
	
	private final Timer mainScheduler = new Timer();
	
	public DataManager() {
		initWithMembers();
	}
		
	/**
	 * 멤버 변수 초기화
	 */
	public void initWithMembers() {
		isReady = false;
		staffMembers = new ArrayList<String>();
	}
	
	/**
	 * 기본 데이터를 생성합니다.
	 */
	private void initWithDefaultData() {
		mConfig = new JSONObject();
		
		JSONArray ary = new JSONArray();
		ary.add("admin");
		
		mConfig.put("staffMembers", ary);		
	}
	
	@SuppressWarnings("unused")
	private void initWithScheduler() {

		// 1시간 마다 실행합니다.
		mainScheduler.schedule(new TimerTask() {
			@Override
			public void run() {
				// 파일 정리
				// 웹소켓을 통한 로깅 작업
				
			}
		}, 0L, 1000L*60L*60L*1L);	
	}
	
	/**
	 * 설정 파일에서 관리자 아이디를 가져오는 기능을 수행합니다.
	 */
	public void loadAllGlobalData() {
		mConfig = readConfigFile();
		
		if(!mConfig.isEmpty() && mConfig.containsKey("staffMembers")) {
			JSONArray jTokens = (JSONArray)mConfig.get("staffMembers");
			
			Iterator<String> iter = jTokens.iterator();
			while(iter.hasNext()) {
				staffMembers.add(iter.next());	
			}
			
			if(staffMembers != null) {
				staffMembers.forEach(System.out::println);
			}
		}
	}
	
	/**
	 * 매개변수에 해당하는 아이디가 관리자인지 알아냅니다.
	 * 
	 * @param staffId
	 * @return
	 */
	public boolean isSttaff(String staffId) {
		return staffMembers.contains(staffId);
	}
	
	/**
	 * 메인 컨텍스트를 설정합니다.
	 * @param context
	 */
	public void setMainApplication(ServletContext context) {
		mContext = context;
		if(mContext != null) {
			isReady = true;
			initWithDefaultData();
			loadAllGlobalData();			
		}
	}
	
	/**
	 * 설정 파일을 WEB_INF 폴더에 생성합니다.
	 * @throws MalformedURLException 
	 * @throws URISyntaxException 
	 */
	public void makeConfigFile() throws MalformedURLException, URISyntaxException {
		// WAS에서 WEB-INF 폴더는 통상적으로 브라우저에서 접근할 수 없기 때문에 설정 파일을 넣어둘 수 있습니다.
		// 주의해야 할 점은 getRealPath가 아니라 getResource 메소드를 사용했다는 점입니다.
		URL root = mContext.getResource("/WEB-INF/settings.json");
		Path outputPath = Paths.get(root.toURI());
		
		// 파일이 이미 있을 경우, 다시 재생성합니다.
		File file = outputPath.toFile();
		if(file.exists()) {
			file.delete();
		}
		
		// 설정 파일을 해당 경로에 생성합니다.
		try (FileWriter writer = new FileWriter(outputPath.toString())) {
			writer.write(mConfig.toJSONString());	
		} catch(Exception ex) {
			
		}		
	}
	
	/**
	 * 설정 파일을 읽습니다.
	 * @return
	 */
	public JSONObject readConfigFile() {
		JSONParser parser = new JSONParser();
		JSONObject ret = new JSONObject();
		try {
			
			// WAS에서 WEB-INF 폴더는 통상적으로 브라우저에서 접근할 수 없기 때문에 설정 파일을 넣어둘 수 있습니다.
			// 주의해야 할 점은 getRealPath가 아니라 getResource 메소드를 사용했다는 점입니다.			
			URL root = mContext.getResource("/WEB-INF/settings.json");
			Path outputPath = Paths.get(root.toURI());
			
			if(!outputPath.toFile().exists())
				makeConfigFile();
			
			JSONObject obj = (JSONObject)parser.parse(new FileReader(outputPath.toString()));
			ret = obj;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
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
