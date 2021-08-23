package Stonks;

public class UserInfo {
	private String username = null;
	private String password = null;
	
	public UserInfo(String username, String password) {
		this.password = password;
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
}