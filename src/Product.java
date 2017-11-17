import java.sql.Date;

public class Product {
	private String name;
	private Date created;
	private int numberOfBugs;
	private String details;
	
	public Product(String name, Date created, int numberOfBugs, String details) {
		this.name = name;
		this.created = created;
		this.numberOfBugs = numberOfBugs;
		this.details = details;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void serCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setNumberOfBugs(int numberOfBugs) {
		this.numberOfBugs = numberOfBugs;
	}
	
	public int getNumberOfBugs() {
		return numberOfBugs;
	}
	
	public void setDetails( String details) {
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
}
