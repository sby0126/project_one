package vo.board.free;

public class Board_file {
	private String file_real_name; 
	private String file_server_name;
	private int file_board_num;
	private int file_seq;
	
	public Board_file(){
	}
	
	
	public Board_file(String file_real_name, String file_server_name) {
		this.file_real_name = file_real_name;
		this.file_server_name = file_server_name;
	}
	public String getFile_real_name() {
		return file_real_name;
	}
	public void setFile_real_name(String file_real_name) {
		this.file_real_name = file_real_name;
	}
	public String getFile_server_name() {
		return file_server_name;
	}
	public void setFile_server_name(String file_server_name) {
		this.file_server_name = file_server_name;
	}
	public int getFile_board_num() {
		return file_board_num;
	}
	public void setFile_board_num(int file_board_num) {
		this.file_board_num = file_board_num;
	}
	public int getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}
	
	
}
