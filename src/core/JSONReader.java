package core;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public final class JSONReader {
	
	public String mFileName;
	private File mFile;
	
	public JSONReader(String filename) {
		mFileName = filename;
		
		mFile = new File(mFileName);
		
		if(mFile.exists()) {
			read();
		}
	}
	
	private JSONArray read() {
		JSONParser parser = new JSONParser();
		JSONArray ret = null;
		try {
			JSONArray raw = (JSONArray)parser.parse(new FileReader(mFileName));
			ret = raw;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
