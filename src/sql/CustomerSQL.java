package sql;

import java.util.HashMap;

public class CustomerSQL {
	private HashMap<String, String> qlNotes = new HashMap<String, String>();
	
	public CustomerSQL() {
		initWithSQL();
	}
	
	public void initWithSQL() {
		
		qlNotes.put("mysql1.selectOnce", "select * from tblCustomer where ctmid = ?");
		qlNotes.put("mysql1.selectAll", "select * from tblCustomer");
		
		qlNotes.put("mysql1.insert", "insert into tblCustomer "
				+ "(CTMID, CTMPW, CTMNM, ADDR, TEL, EMAIL, ZIPCODE, IS_ADMIN, JOINDATE, SALT, CTMTYPE)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), ?, ?)");
		
		qlNotes.put("mysql1.updateName", "update * from tblCustomer set ctmnm = ? where ctmid = ?");
		qlNotes.put("mysql1.delete", "delete from tblCustomer where ctmid = ?");
	}
	
	public String get(String command) {
		return qlNotes.get(command);
	}
		
}
