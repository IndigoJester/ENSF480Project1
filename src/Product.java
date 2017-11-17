import java.sql.Date;

public class Product {
	private String name;
	private Date created;
	private String details;
	
	public Product(String name, Date created, String details) {
		this.name = name;
		this.created = created;
		this.details = details;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setDetails( String details) {
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
}
