package service.payments;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 아임포트 REST API 호출 (결제 모듈)
 * 
 * @author 어진석
 *
 */
public class PaymentService {
	
	/**
	 * 액세스 토큰 획득
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken() throws Exception {
		
		URL url = new URL("https://api.iamport.kr/users/getToken");

		Map<String,Object> params = new LinkedHashMap<>();
		params.put("imp_key", "6251988666504846");
		params.put("imp_secret", "CJX18hVNywYtlR90xH41AwDdFVIGO0lrKisTznH8UFCfwMg4tk64RKiwF0du0yVLQoEqLlzG0cWxYcCr");
		
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");		
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		connection.setDoOutput(true);
		connection.getOutputStream().write(postDataBytes); // POST 호출
		
		connection.setRequestProperty("accept", "application/json");
			
		InputStream responseStream = connection.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(responseStream);
		
		int code = node.get("code").asInt();
		String message = node.get("message").asText();
		String access_token = node.get("response").get("access_token").asText();
		int expired_at = node.get("response").get("expired_at").asInt();
		int now = node.get("response").get("now").asInt();
		
		return access_token;
	}
	
	/**
	 * 
	 * @param imp_uid 고유 결제 코드
	 * @param customerId 고객 ID
	 * @param productId 상품 코드
	 * @param paid_amount 결제 요청 금액
	 * @param access_token REST API 액세스 토큰
	 * @return
	 * @throws Exception
	 */
	public boolean orderKakaoPay(String imp_uid, String customerId, String productId, int paid_amount, String access_token) throws Exception {
		
		// GET 방식의 호출입니다.
		// 주소로 전달합니다.
		URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod("GET");
		
		// 액세스 토큰을 같이 전달해야 합니다.
		connection.setRequestProperty("Authorization", access_token);
		connection.setRequestProperty("accept", "application/json");
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 401) {
			// 인증 Token이 전달되지 않았거나 유효하지 않은 경우
			return false;
		} else if(responseCode == 404) {
			// 거래건이 존재하지 않는 경우
			return false;
		}
		
		InputStream responseStream = connection.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(responseStream);
	
//		{
//			  "code": 0,
//			  "message": "string",
//			  "response": {
//			    "imp_uid": "string",
//			    "merchant_uid": "string",
//			    "pay_method": "string",
//			    "channel": "pc",
//			    "pg_provider": "string",
//			    "pg_tid": "string",
//			    "pg_id": "string",
//			    "escrow": true,
//			    "apply_num": "string",
//			    "bank_code": "string",
//			    "bank_name": "string",
//			    "card_code": "string",
//			    "card_name": "string",
//			    "card_quota": 0,
//			    "card_number": "string",
//			    "card_type": "null",
//			    "vbank_code": "string",
//			    "vbank_name": "string",
//			    "vbank_num": "string",
//			    "vbank_holder": "string",
//			    "vbank_date": 0,
//			    "vbank_issued_at": 0,
//			    "name": "string",
//			    "amount": 0,
//			    "cancel_amount": 0,
//			    "currency": "string",
//			    "buyer_name": "string",
//			    "buyer_email": "string",
//			    "buyer_tel": "string",
//			    "buyer_addr": "string",
//			    "buyer_postcode": "string",
//			    "custom_data": "string",
//			    "user_agent": "string",
//			    "status": "ready",
//			    "started_at": 0,
//			    "paid_at": 0,
//			    "failed_at": 0,
//			    "cancelled_at": 0,
//			    "fail_reason": "string",
//			    "cancel_reason": "string",
//			    "receipt_url": "string",
//			    "cancel_history": [
//			      {
//			        "pg_tid": "string",
//			        "amount": 0,
//			        "cancelled_at": 0,
//			        "reason": "string",
//			        "receipt_url": "string"
//			      }
//			    ],
//			    "cancel_receipt_urls": [
//			      "string"
//			    ],
//			    "cash_receipt_issued": true,
//			    "customer_uid": "string",
//			    "customer_uid_usage": "issue"
//			  }
//			}		
		
		JsonNode response = node.get("response");
		int amount = response.get("amount").asInt();
		int cancelAmount = response.get("cancel_amount").asInt(); 
		
		// 구매 시 DB에 값을 전달, 
		// 아임포트로부터 받은 실제 결제 금액과 일치하면 검증 완료
		boolean isValid = false;
		
		// isValid가 true면 구매 처리
		
		return isValid;
	}
}
