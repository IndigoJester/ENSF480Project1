import java.util.Date;

//Bug Class
public class Bug {
    // Local String Variables:
	private String name, product, details, assignedDev;
    // Local Date Variable, stores time created:
	private Date created;
    // Local Boolean Variable, shows if Bug is approved or not
	private Boolean approved;
    // Local Int Variable, shows status of the Bug
	private int status;

    // Bug Constructor
	public Bug(String name, String fromProduct, Date created, Boolean approved,
			   String details, int status,String assignedDev) {
		this.name = name;
		this.product = fromProduct;
		this.created = created;
		this.approved = approved;
		this.details = details;
		this.status = status;
		this.assignedDev = assignedDev;
	}

    // Function used to set the name of a Bug
	public void setName(String name) {
		this.name = name;
	}

    // Function that returns the current name of the Bug
	public String getName() {
		return name;
	}

    // Function that sets the product the Bug is from
	public void setProduct(String fromProduct) {
		this.product = fromProduct;
	}

    // Returns the product with which the bug is from
	public String getProduct() {
		return product;
	}

    // Sets the date for when the Bug was created
	public void setCreated(Date created) {
		this.created = created;
	}

    // Returns the Date the Bug was Created
	public Date getCreated() {
		return created;
	}

    // Sets the Approved status of the Bug
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

    // Returns whether the Bug has been approved or not
	public Boolean getApproved() {
		return approved;
	}

    // Sets the Details of the Bug
	public void setDetails(String details) {
		this.details = details;
	}

    // Returns the Details of the Bug
	public String getDetails() {
		return details;
	}

    // Sets the Status of the Bug
	public void setStatus(int status) {
		this.status = status;
	}

    // Returns the Status of the Bug
	public int getStatus() {
		return status;
	}

    // Sets the Assigned Dev for the Bug
	public void setAssignedDev(String assignedDev) {
		this.assignedDev = assignedDev;
	}

    // Returns the Developed for which the Bug is Assigned To
	public String getAssignedDev() {
		return assignedDev;
	}

}
