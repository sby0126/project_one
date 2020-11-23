package core;

import utils.SHA256Util;

public class CustomerVO {
	private String id; // CTMID
	private String password; // CTMPW
	private String no; // CTMNO
	private String name; // CTMNM
	private String address; // ADDR
	private String tel; // TEL
	private String email; // EMAIL
	private String isAdmin; // IS_ADMIN
	private String joinDate; // JoinDate
	private String salt; 
		
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
	
	public CustomerVO setPassword(String password) {		
		salt = SHA256Util.generateSalt();
		String hashedPassword = SHA256Util.getEncrypt(password, salt);
		
		this.password = hashedPassword;
		
		return this;
	}
	
	public String getNo() {
		return no;
	}
	
	public CustomerVO setNo(String no) {
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

	public CustomerVO setJoinDate(String joinDate) {
		this.joinDate = joinDate;
		
		return this;
	}

	
	public String getSalt() {
		return salt;
	}

	public CustomerVO setSalt(String salt) {
		this.salt = salt;
		
		return this;
	}	
	
}
