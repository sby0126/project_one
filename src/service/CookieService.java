package service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 쿠키 클래스
 *
 */
public class CookieService {
	
	public final static int DAY = 60 * 60 * 24;
	
	/**
	 * 쿠키를 생성합니다.
	 * @param name
	 * @param value
	 * @param path
	 * @return
	 */
	public Cookie create(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(DAY);
		cookie.setPath("/");
		
		response.addCookie(cookie);
		
		return cookie;
	}
	
	/**
	 * 쿠키를 삭제합니다.
	 * 
	 * @param request
	 * @param response
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		List<Cookie> list = Arrays.asList(request.getCookies());
		
		list.forEach(c -> {
			if(c.getName().equals(cookieName)) {
				c.setMaxAge(0);
				response.addCookie(c);				
			}
		});
	}
	
	/**
	 * 쿠키 정보를 HashMap<String, String>으로 받아옵니다.
	 * @param request
	 * @return
	 */
	public HashMap<String, String> getKeyValue(HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			List<Cookie> list = Arrays.asList(cookies);
			
			for(Cookie c : list) {
				map.put(c.getName(), c.getValue());
			}
						
		}
		
		return map;
	}
	
	/**
	 * 쿠키에서 키 값만 받아옵니다.
	 * @param request
	 * @return
	 */
	public HashSet<String> keys(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		List<Cookie> list = Arrays.asList(cookies);
		
		HashSet<String> set = new HashSet<String>();
		
		for(Cookie c : list) {
			set.add(c.getName());
		}
		
		return set;
	}
	
	/**
	 * 쿠키 정보에서 값을 가져옵니다.
	 * 
	 * @param request
	 * @return
	 */
	public HashSet<String> values(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		List<Cookie> list = Arrays.asList(cookies);
		
		HashSet<String> set = new HashSet<String>();
		
		for(Cookie c : list) {
			set.add(c.getValue());
		}
		
		return set;
	}	
	
	
	/**
	 * 쿠키 정보를 열거합니다.
	 * 
	 * @param request
	 * @param action
	 */
	public void forEach(HttpServletRequest request, BiConsumer<String, String> action) {
		Cookie[] cookies = request.getCookies();
		List<Cookie> list = Arrays.asList(cookies);
		
		list.forEach(c -> {
			action.accept(c.getName(), c.getValue());
		});
		
	}
}
