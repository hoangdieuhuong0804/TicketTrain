package Model;

public class User {
	private String username;
	private String password;
	private String fullname;
	private String cccd;
	private String phonenumber;
	private String email;
	public User(String username, String password, String fullname, String cccd, String phonenumber, String email) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.cccd = cccd;
		this.phonenumber = phonenumber;
		this.email = email;
	}
	
	public User() {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.cccd = cccd;
		this.phonenumber = phonenumber;
		this.email = email;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", fullname=" + fullname + ", cccd=" + cccd
				+ ", phonenumber=" + phonenumber + ", email=" + email + "]";
	}
	
	
	

		
}
