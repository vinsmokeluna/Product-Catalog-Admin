package admin;

public class data {
	
	private String name;
	private String username;
	private String password;
	private String contact;
	private String address;
	private String email;
	private Integer ID;
	private String role;
	
	public data(Integer ID, String name, String username, String password, String contact, String address,
			String email, String role) {
		
		this.ID = ID;
		this.name = name;
		this.username = username;
		this.password = password;
		this.contact = contact;
		this.address = address;
		this.email = email;
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}

