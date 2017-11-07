import java.util.Date;

public class Bug {
	private String product;
	private Date created;
	private Boolean approved;
	private String details;
	private int status;
	private String assignedDev;
	
	public Bug(String fromProduct, Date created, Boolean approved, 
			   String details, int status,String assignedDev) {
		this.product = fromProduct;
		this.created = created;
		this.approved = approved;
		this.details = details;
		this.status = status;
		this.assignedDev = assignedDev;	
	}
	
	public void setProduct(String fromProduct) {
		this.product = fromProduct;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public Boolean getApproved() {
		return approved;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	public void setAssignedDev(String assignedDev) {
		this.assignedDev = assignedDev;
	}
	
	public String getAssignedDev() {
		return assignedDev;
	}
	
}
