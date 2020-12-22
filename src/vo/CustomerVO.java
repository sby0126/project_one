package vo;

import utils.SHA256Util;

/**
 * 아래 변수 명명법은 엄격히 말하면 잘못되었습니다.
 * 
 * TODO: 변수명은 테이블에 정의된 축약형 컬럼명과 정확히 일치해야 합니다.
 * 
 */
public class CustomerVO {

	private String id; // CTMID
	private String password; // CTMPW
	private int no; // CTMNO
	private String name; // CTMNM
	private String address; // ADDR
	private String tel; // TEL
	private String email; // EMAIL
	private String zipCode;
	private String isAdmin; // IS_ADMIN
	private String joinDate; // JoinDate
	private String salt; 

	private String lastLogin;
	private String failedLoginCount;
	private String isLock;	
	
	private String ctmtype;

		
	public String getId() {
		return id;
	}
	
	public CustomerVO setId(String id) {
		this.id = id;
		
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param password 암호화가 안된 비밀번호 문자열을 전달합니다.
	 * @return
	 */
	public CustomerVO setPassword(String password) {		
		// Salt를 생성합니다.
		salt = SHA256Util.generateSalt();
		
		// Salt 값과 비밀번호를 통해 단방향 암호화를 한 후, 이 값을 DB에 저장합니다.
		String hashedPassword = SHA256Util.getEncrypt(password, salt);
		
		this.password = hashedPassword;
		
		return this;
	}
	
	public int getNo() {
		return no;
	}
	
	public CustomerVO setNo(int no) {
		this.no = no;
		
		return this;
	}	
	
	public String getName() {
		return name;
	}
	
	public CustomerVO setName(String name) {
		this.name = name;
		
		return this;
	}
	
	public String getAddress() {
		return address;
	}
	
	public CustomerVO setAddress(String address) {
		this.address = address;
		
		return this;
	}
	
	public String getTel() {
		return tel;
	}
	
	public CustomerVO setTel(String tel) {
		this.tel = tel;
		
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	
	public CustomerVO setEmail(String email) {
		this.email = email;
		
		return this;
	}	
	
	public String getZipCode() {
		return zipCode;
	}

	public CustomerVO setZipCode(String zipCode) {
		this.zipCode = zipCode;
		
		return this;
	}
	
	
	public String getIsAdmin() {
		return isAdmin;
	}
	
	public CustomerVO setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin.equals("Y") ? "Y" : "N";
		
		return this;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public CustomerVO setJoinDate(String joinDate2) {
		this.joinDate = joinDate2;
		
		return this;
	}

	
	public String getSalt() {
		return salt;
	}

	public CustomerVO setSalt(String salt) {
		this.salt = salt;
		
		return this;
	}	
	
	public String getLastLogin() {
		return lastLogin;
	}

	public CustomerVO setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
		
		return this;
	}

	public String getFailedLoginCount() {
		return failedLoginCount;
	}

	public CustomerVO setFailedLoginCount(String failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
		
		return this;
	}

	public String getIsLock() {
		return isLock;
	}

	public CustomerVO setIsLock(String isLock) {
		this.isLock = isLock;
		
		return this;
	}

	public String getCtmtype() {
		return ctmtype;
	}

	public void setCtmtype(String ctmtype) {
		this.ctmtype = ctmtype;
	}

}
