package service.payments;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	@SuppressWarnings("deprecation")
	public boolean orderKakaoPay(String imp_uid, String customerId, int productId, int paid_amount, String access_token) throws Exception {
		
		boolean isValid = false;
		
		if(access_token != null) {
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create("https://api.iamport.kr/payments/" + imp_uid))
					.setHeader("Authorization", access_token)
					.setHeader("accept", "application/json")
					.GET()
					.build();
			
			HttpClient httpClient = HttpClient.newHttpClient();
			
			try {
				HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
				if(response.statusCode() == HttpURLConnection.HTTP_OK) {
					
					String contents = response.body();
					
					JsonParser parser = new JsonParser();
					JsonElement root = parser.parse(contents);
					JsonObject res = root.getAsJsonObject().get("response").getAsJsonObject();
					
					int amount = res.get("amount").getAsInt();
					long cancelAmount = res.get("cancel_amount").getAsInt(); 
					String status = res.get("status").getAsString();
					
					PaymentDAO paymentDAO = PaymentDAO.getInstance();
					PaymentVO vo = paymentDAO.getPayment(imp_uid);
					
					// 아임포트로부터 받은 결제 금액과 실제 DB에 저장된 결제 금액과 일치하면 정상적으로 결제된 것임 (검증 완료)
					if(status.equals("paid") && vo.getPaidAmount() == amount) {
						isValid = true;
					} else {
						isValid = false;
					}							
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
								
		}
			
		return isValid;
	}
	
	public static void main(String[] args) {
		
		String access_token = null;
		
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
				access_token = (String)response.get("access_token");
				
				System.out.println(access_token);
					
			} else {
				System.out.println("연결에 실패하였습니다.");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
						
	}
}
