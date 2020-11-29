package core;

import java.lang.reflect.Field;

import org.json.simple.JSONObject;

public interface VO {
	@SuppressWarnings("unchecked")
	default JSONObject toJson() throws IllegalArgumentException, IllegalAccessException  {
		JSONObject data = new JSONObject();
		
		Field[] fields = this.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			data.put(field.getName(), field.get(this));
		}
		
		return data;		
	}
}
