
public class Developer {
	private String name;
	private String username;
	private String details;
	
	public Developer(String name, String username, String details) {
		this.name = name;
		this.username = username;
		this.details = details;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setDetails( String details) {
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
}

