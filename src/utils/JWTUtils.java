package utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 */
public class JWTUtils {
	private static final String secretKey = "aaaeeesss";
	public static final Long MINUTE = 1000 * 60L;
	public static final Long HOUR = 1000 * 60L * 60L;
	
	/**
	 * 
	 * @param data
	 * @param expiredTime
	 * @return
	 */
	public static String createToken(JSONObject data, Long expiredTime) {
		
		// 헤더 생성
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		
		// 토큰 만료 시간
		Date ext = new Date();
		ext.setTime(ext.getTime() + expiredTime);
				
		
		Map<String, Object> payloads = new HashMap<>();
		payloads.put("exp", ext);
		payloads.put("data", data.toJSONString());
		
		// Long expiredTime = 1000 * 60L * 60L * 2L; // 2시간
		
		if(expiredTime == null) {
			throw new Error("만료 시간이 설정되지 않았습니다");
		}

        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성
        
       
        return jwt;
	}
	
	/**
	 * 
	 * @param jwt
	 * @return
	 */
	public static JSONObject verifyJWT(String jwt) {
		Map<String, Object> decodedMap = null;
		JSONObject data = null;
		
		try {
			
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey.getBytes("UTF-8"))
					.parseClaimsJws(jwt)
					.getBody();
			
			decodedMap = claims;
			JSONParser parser = new JSONParser();
					
			Object raw = parser.parse((String)decodedMap.get("data"));
			data = (JSONObject)raw;
			
		} catch(ExpiredJwtException e) { // 토큰 만료
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONObject data = new JSONObject();
		data.put("id", "biud436");
		
		String token = JWTUtils.createToken(data, JWTUtils.MINUTE * 2L);
		
		System.out.println(token);
		
		JSONObject decodedData = JWTUtils.verifyJWT(token);
		System.out.println(decodedData.get("id"));	

	}
}
