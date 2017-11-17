// Developer Class
public class Developer {
    // Name of the Developer
	private String name;
    // Username of the Developer
	private String username;
    // Details of the Developer
	private String details;

    // Developer Constructor
	public Developer(String name, String username, String details) {
		this.name = name;
		this.username = username;
		this.details = details;
	}

    // Sets the Name of the Developer
	public void setName(String name) {
		this.name = name;
	}

    // Returns the Name of the Developer
	public String getName() {
		return name;
	}

    // Sets the Username of the Developer
	public void setUsername(String username) {
		this.username = username;
	}

    // Returns the Username of the Developer
	public String getUsername() {
		return username;
	}

    // Sets the Details of the Developer
	public void setDetails( String details) {
		this.details = details;
	}

    // Returns the Details of the Developer
	public String getDetails() {
		return details;
	}
}
