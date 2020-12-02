package core;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/***
 * 데이터베이스의 로우 이미지 문자열을 파싱하여 이미지 이름 배열로 만듭니다.
 */
public class ImageFile {
	private String raw;
	private List<String> images;
	private boolean isOK;
	
	public ImageFile(String raw) {
		this.raw = raw;
	}
	
	public void start() {
		if(raw == null) return;
		images =  Arrays.asList( raw.split(",") );
	}
	
	public boolean isOK() {
		return isOK;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}
	
	public void forEach(Consumer<String> action) {
		images.forEach(action);
	}
	
}
