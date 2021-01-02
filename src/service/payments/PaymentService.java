package service.payments;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dao.PaymentDAO;
import vo.PaymentVO;

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
		
		String access_token = "";
		
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
		connection.setRequestProperty("accept", "application/json");
		connection.setDoOutput(true);
		connection.getOutputStream().write(postDataBytes); // POST 호출
					
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			
			InputStream responseStream = connection.getInputStream();
			
			// 읽기
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
			
			JSONParser parser = new JSONParser();
			JSONObject raw = (JSONObject)parser.parse(sb.toString());
			
			JSONObject response = (JSONObject)raw.get("response");
			access_token = (String)response.get("access_token");
			
			System.out.println(access_token);
			
		} else {
			System.out.println("연결에 실패하였습니다.");
		}
		
		return access_token;
	}
	
	public boolean processKakaoPay(PaymentVO vo) {
		 PaymentDAO paymentDAO = PaymentDAO.getInstance(); 
		 return paymentDAO.insertPayment(vo);
	}
	
	public boolean setStatus(String imp_uid, String status) {
		 PaymentDAO paymentDAO = PaymentDAO.getInstance(); 
		 return paymentDAO.updatePayment(imp_uid, status);
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
	public boolean orderKakaoPay(String imp_uid, String customerId, int productId, int paid_amount, String access_token) throws Exception {
		
		boolean isValid = false;
		
		URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(1000 * 8);
		
		// 액세스 토큰을 같이 전달해야 합니다.
		connection.setRequestProperty("Authorization", access_token);
		connection.setRequestProperty("accept", "application/json");
		
		connection.setDoOutput(false); 
		
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			
			InputStream responseStream = connection.getInputStream();
			
			// 읽기
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
			
			JSONParser parser = new JSONParser();
			
			System.out.println(sb.toString());
			
			JSONObject raw = (JSONObject)parser.parse(sb.toString());
			JSONObject response = (JSONObject)raw.get("response");
		
//			{
//				  "code": 0,
//				  "message": "string",
//				  "response": {
//				    "imp_uid": "string",
//				    "merchant_uid": "string",
//				    "pay_method": "string",
//				    "channel": "pc",
//				    "pg_provider": "string",
//				    "pg_tid": "string",
//				    "pg_id": "string",
//				    "escrow": true,
//				    "apply_num": "string",
//				    "bank_code": "string",
//				    "bank_name": "string",
//				    "card_code": "string",
//				    "card_name": "string",
//				    "card_quota": 0,
//				    "card_number": "string",
//				    "card_type": "null",
//				    "vbank_code": "string",
//				    "vbank_name": "string",
//				    "vbank_num": "string",
//				    "vbank_holder": "string",
//				    "vbank_date": 0,
//				    "vbank_issued_at": 0,
//				    "name": "string",
//				    "amount": 0,
//				    "cancel_amount": 0,
//				    "currency": "string",
//				    "buyer_name": "string",
//				    "buyer_email": "string",
//				    "buyer_tel": "string",
//				    "buyer_addr": "string",
//				    "buyer_postcode": "string",
//				    "custom_data": "string",
//				    "user_agent": "string",
//				    "status": "ready",
//				    "started_at": 0,
//				    "paid_at": 0,
//				    "failed_at": 0,
//				    "cancelled_at": 0,
//				    "fail_reason": "string",
//				    "cancel_reason": "string",
//				    "receipt_url": "string",
//				    "cancel_history": [
//				      {
//				        "pg_tid": "string",
//				        "amount": 0,
//				        "cancelled_at": 0,
//				        "reason": "string",
//				        "receipt_url": "string"
//				      }
//				    ],
//				    "cancel_receipt_urls": [
//				      "string"
//				    ],
//				    "cash_receipt_issued": true,
//				    "customer_uid": "string",
//				    "customer_uid_usage": "issue"
//				  }
//				}		
			
			int amount = Integer.parseInt((String)response.get("amount"));
			long cancelAmount = Integer.parseInt((String)response.get("cancel_amount")); 
			String status = (String)response.get("status");
			
			PaymentDAO paymentDAO = PaymentDAO.getInstance();
			PaymentVO vo = paymentDAO.getPayment(imp_uid);
			
			// 아임포트로부터 받은 결제 금액과 실제 DB에 저장된 결제 금액과 일치하면 정상적으로 결제된 것임 (검증 완료)
			if(status.equals("paid") && vo.getPaidAmount() == amount) {
				isValid = true;
			} else {
				isValid = false;
			}			
		} else {
			System.out.println("액세스 토큰이 잘못되었거나 현재 코드가 유효하지 않습니다.");
			isValid = false;
		}
						
		return isValid;
	}
	
	public static void main(String[] args) {
		
		try {
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
			connection.setRequestProperty("accept", "application/json");
			connection.setDoOutput(true);
			connection.getOutputStream().write(postDataBytes); // POST 호출
						
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				InputStream responseStream = connection.getInputStream();
				
				// 읽기
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				
				JSONParser parser = new JSONParser();
				JSONObject raw = (JSONObject)parser.parse(sb.toString());
				
				JSONObject response = (JSONObject)raw.get("response");
				String access_token = (String)response.get("access_token");
				
				System.out.println(access_token);
					
			} else {
				System.out.println("연결에 실패하였습니다.");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}
}
