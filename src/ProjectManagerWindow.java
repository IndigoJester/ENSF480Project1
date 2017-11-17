import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProjectManagerWindow {
	private static Connection myConn1, myConn2, myConn3, myConn4;
	private JButton removeFixedBug;
	private JButton rejectBug;
	private JButton approveBug;
	private JButton assignBug;
	private JButton addDeveloper;
	private JButton removeDeveloper;
	private JButton updateDeveloper;
	private JButton addProduct;
	private JButton removeProduct;
	private JButton updateProduct;
	private Vector<Developer> developers;
	private JList developerList;
	private String managerName;
    protected JList<String> productList, bugList;
    protected Vector<Bug> bugs;
    protected Vector<Product> products;
    protected JButton submitBug, refresh;
    protected MouseAdapter mouseEventListener;
    protected ActionListener buttonEventListener;
    protected Product activeProduct;

    private class EventListener implements ActionListener {
        ProjectManagerWindow display;
        public EventListener (ProjectManagerWindow d) {
            display = d;
        }
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == submitBug) {
                try {
					display.submitNewBug();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == refresh) {
                try {
					display.refresh();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == removeFixedBug) {
                try {
					display.removeBug();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == rejectBug) {
                try {
					display.rejectBugReport();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == approveBug) {
                try {
					display.approveBugReport();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == assignBug) {
                try {
					display.assignBugToDev();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == addDeveloper) {
                try {
					display.addDev();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == removeDeveloper) {
                try {
					display.removeDev();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == updateDeveloper) {
                try {
					display.updateDev();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == addProduct) {
                try {
					display.addNewProduct();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == removeProduct) {
                try {
					display.removeAProduct();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == updateProduct) {
                try {
					display.updateAProduct();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        }
    }
	public ProjectManagerWindow (String username) throws SQLException {
		myConn1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bug?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn3 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/developer?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn4 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/credentials?autoReconnect=true&useSSL=false", "root", "Bigpapi");
        buttonEventListener = new EventListener(this);
        managerName = username;
        this.display();
        refresh();
	}
	
	public void display () {
        // Set up display defaults
        Font defaultFont = new Font("Candara", Font.BOLD, 20);
        Color defaultColor = new Color (54, 51, 51);

        // Create Frame
        JFrame main = new JFrame();
        main.setTitle("RAID Bug Tracking System: Developer");
        main.setIconImage(new ImageIcon("Images/Logo.png").getImage());
        main.setSize(800, 1000);
        main.setLocationRelativeTo(null);
        main.setResizable(false);
        main.setLayout(new BorderLayout());
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set background image
        BackgroundPanel background = new BackgroundPanel(new BorderLayout(), "Images/Manager.png");

        // Panel to control the display
        JPanel controlBox = new JPanel();
        controlBox.setLayout(new BorderLayout());
        controlBox.setOpaque(false);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userPanel.setOpaque(false);
        JLabel username = new JLabel("  " + managerName);
        username.setBorder(new EmptyBorder (9, 0, 80, 0));
        username.setForeground(new Color(0, 0, 0));
        username.setFont(new Font("Britannic Bold", Font.BOLD, 26));
        userPanel.add(username);

        JPanel contentDiv = new JPanel();
        contentDiv.setLayout(new GridLayout(0, 1));
        contentDiv.setOpaque(false);

        // Developer stuff
        JPanel developerControl = new JPanel();
        developerControl.setLayout(new BorderLayout());
        developerControl.setOpaque(false);

        JPanel devDiv = new JPanel();
        devDiv.setLayout(new BoxLayout(devDiv, BoxLayout.Y_AXIS));
        devDiv.setOpaque(false);
        devDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        JLabel label = new JLabel("Developers:                                                                              ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        devDiv.add(label);
        developerList = new JList();
        developerList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList developerList = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    try {
						viewDeveloper();
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
            }
        });
        developerList.setFont(new Font("Courier", Font.BOLD, 20));
        JScrollPane tempScroll = new JScrollPane(developerList);
        developerList.setBackground(new Color (162, 154, 154));
        devDiv.add(tempScroll);
        developerControl.add("Center", devDiv);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (10, 0, 10, 0));
        addDeveloper = new CustomJButton(21, 38, "Add New Developer");
        addDeveloper.addActionListener(buttonEventListener);
        buttonPanel.add(addDeveloper);
        removeDeveloper = new CustomJButton(21, 38, "Remove Developer");
        removeDeveloper.addActionListener(buttonEventListener);
        buttonPanel.add(removeDeveloper);
        updateDeveloper = new CustomJButton(21, 38, "Update Developer");
        updateDeveloper.addActionListener(buttonEventListener);
        buttonPanel.add(updateDeveloper);
        developerControl.add("South", buttonPanel);
        contentDiv.add(developerControl);

        // Product Stuff
        JPanel productControl = new JPanel();
        productControl.setLayout(new BorderLayout());
        productControl.setOpaque(false);

        JPanel productDiv = new JPanel();
        productDiv.setLayout(new BoxLayout(productDiv, BoxLayout.Y_AXIS));
        productDiv.setOpaque(false);
        productDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        label = new JLabel("Products:                                                                         ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        productDiv.add(label);
        productList = new JList();
        productList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList productList = (JList)evt.getSource();
                if(evt.getClickCount() == 1) {
                	int index = productList.locationToIndex(evt.getPoint());
                	try {
						showBugsFrom(products.get(index));
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = productList.locationToIndex(evt.getPoint());
                    Product get = products.get(index);
                    viewProduct(get);
                }
            }
        });
        productList.setFont(new Font("Courier", Font.BOLD, 20));
        tempScroll = new JScrollPane(productList);
        productList.setBackground(new Color (162, 154, 154));
        productDiv.add(tempScroll);
        productControl.add("Center", productDiv);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (10, 0, 10, 0));
        addProduct = new CustomJButton(20, 38, "Add New Product");
        addProduct.addActionListener(buttonEventListener);
        buttonPanel.add(addProduct);
        removeProduct = new CustomJButton(20, 38, "Remove Product");
        removeProduct.addActionListener(buttonEventListener);
        buttonPanel.add(removeProduct);
        updateProduct = new CustomJButton(20, 38, "Update Product");
        updateProduct.addActionListener(buttonEventListener);
        buttonPanel.add(updateProduct);
        productControl.add("South", buttonPanel);
        contentDiv.add(productControl);

        // Bug stuff
        JPanel bugDiv = new JPanel();
        bugDiv.setLayout(new BoxLayout(bugDiv, BoxLayout.Y_AXIS));
        bugDiv.setOpaque(false);
        bugDiv.setBorder(new EmptyBorder (0, 25, 0, 25));
        label = new JLabel("Bugs:                                                                                    ");
        label.setBorder(new EmptyBorder (5, 0, 5, 0));
        label.setForeground(defaultColor);
        label.setFont(defaultFont);
        bugDiv.add(label);
        bugList = new JList();
        bugList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList bugList = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = bugList.locationToIndex(evt.getPoint());
                    Bug get = bugs.get(index);
                    viewBug(get);
                }
            }
        });
        bugList.setFont(new Font("Courier", Font.BOLD, 20));
        tempScroll = new JScrollPane(bugList);
        bugList.setBackground(new Color (162, 154, 154));
        bugDiv.add(tempScroll);
        contentDiv.add(bugDiv);

        // Buttons
        JPanel manyButtons = new JPanel();
        manyButtons.setLayout(new GridLayout(0, 1));
        manyButtons.setOpaque(false);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (5, 0, 5, 0));
        submitBug = new CustomJButton(19, 38, "Submit New Bug");
        submitBug.addActionListener(buttonEventListener);
        buttonPanel.add(submitBug);
        assignBug = new CustomJButton(27, 38, "Assign Bug To A Developer");
        assignBug.addActionListener(buttonEventListener);
        buttonPanel.add(assignBug);
        removeFixedBug = new CustomJButton(21, 38, "Remove A Fixed Bug");
        removeFixedBug.addActionListener(buttonEventListener);
        buttonPanel.add(removeFixedBug);
        manyButtons.add(buttonPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout (FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder (5, 0, 5, 0));
        rejectBug = new CustomJButton(23, 38, "Reject A Reported Bug");
        rejectBug.addActionListener(buttonEventListener);
        buttonPanel.add(rejectBug);
        approveBug = new CustomJButton(24, 38, "Accept A Reported Bug");
        approveBug.addActionListener(buttonEventListener);
        buttonPanel.add(approveBug);
        refresh = new CustomJButton(19, 38, "Refresh Results");
        refresh.addActionListener(buttonEventListener);
        buttonPanel.add(refresh);
        manyButtons.add(buttonPanel);

        controlBox.add("South", manyButtons);
        controlBox.add("Center", contentDiv);
        background.add("North", userPanel);
        background.add("Center", controlBox);
        main.add(background);
        main.setVisible(true);
	}

    protected void submitNewBug() throws SQLException {
        generateBugSubmissionWindow();
    }
    
    private void generateBugSubmissionWindow() throws SQLException {
    	String uType = "ProjectManager";
    	int index = productList.getSelectedIndex();
  		BugSubmissionWindow bugSubWindow = new BugSubmissionWindow(products.get(index), uType);
  		refresh();
  	}

    protected void refresh() throws SQLException {
        // Refresh.
    	  getBugs();
          getProducts();
          getDevelopers();
    }

    protected void getBugs() throws SQLException {
    	bugs = new Vector<Bug>();
      	// Get Bugs from the Database
      	PreparedStatement stmt = myConn1.prepareStatement("SELECT * FROM bugs");
  		ResultSet rs = stmt.executeQuery();
  		while(rs.next()) {
  			Bug bug = new Bug(rs.getString("name"), rs.getString("fromProduct"),
  					  rs.getDate("created"),rs.getString("details"), 
  					  rs.getInt("status"), rs.getString("assignedDev"), rs.getDate("dateFixed"));
  			bugs.add(bug);
  			
  			DefaultListModel<String> model = new DefaultListModel<String>();
  		    for(Bug b : bugs){
  		  	String list= b.getName();
	    	list += "  |  ";
	    	if(b.getStatus() == 0) {
	    		list += "REPORTED";
	    	}else if(b.getStatus() == 1) {
	    		list += "APPROVED";
	    	}else {
	    		list += "FIXED";
	    	}
	    	list += "  |  ";
	    	list += b.getProduct();
	         model.addElement(list);
  		    }    
  		    bugList.setModel(model);     
  		    bugList.setSelectedIndex(0);
  		}
    }
    
    protected void getProducts() throws SQLException {
    	products = new Vector<Product>();
        // Get Products from the Database
    	PreparedStatement stmt = myConn2.prepareStatement("SELECT * FROM products");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Product product = new Product(rs.getString("name"), rs.getDate("created"), 
					rs.getInt("numberOfBugs"),rs.getString("details"));
			products.add(product);
			
			DefaultListModel<String> model = new DefaultListModel<String>();
			    for(Product p : products){
			    	String list= p.getName();
			    	list += "  |  CREATED: ";
			    	list += p.getCreated();
			        model.addElement(list);
			    }    
			    productList.setModel(model);     
			    productList.setSelectedIndex(0);
			}
    }
    
    protected void getDevelopers() throws SQLException {
    	developers = new Vector<Developer>();
        // Get Products from the Database
    	PreparedStatement stmt = myConn3.prepareStatement("SELECT * FROM developers");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Developer developer = new Developer(rs.getString("name"), rs.getString("username"), 
			rs.getString("details"));
			
			developers.add(developer);
			
			DefaultListModel<String> model = new DefaultListModel<String>();
			    for(Developer d : developers){
			    	String list= d.getName();
			    	list += "  |  USERNAME: ";
			    	list += d.getUsername();
			        model.addElement(list);
			    }    
			    developerList.setModel(model);     
			    developerList.setSelectedIndex(0);
			}
    }

    protected void showBugsFrom(Product activeProduct) throws SQLException {
   	 // Display the bugs from the product argument
   		bugs.removeAllElements();
   	
       	PreparedStatement stmt = myConn1.prepareStatement("SELECT * FROM bugs WHERE fromProduct = ? AND status != ?");
       	stmt.setString(1, activeProduct.getName());
       	stmt.setInt(2, 0);
   		ResultSet rs = stmt.executeQuery();
   		DefaultListModel<String> model = new DefaultListModel<String>();
   		while(rs.next()) {
   			Bug bug = new Bug(rs.getString("name"), rs.getString("fromProduct"),
   					  rs.getDate("created"),rs.getString("details"),rs.getInt("status"), 
   					  rs.getString("assignedDev"), rs.getDate("dateFixed"));
   			bugs.add(bug);
   		}
   		    for(Bug b : bugs){
   		 	String list= b.getName();
	    	list += "  |  ";
	    	if(b.getStatus() == 0) {
	    		list += "REPORTED";
	    	}else if(b.getStatus() == 1) {
	    		list += "APPROVED";
	    	}else {
	    		list += "FIXED";
	    	}
	    	list += "  |  ";
	    	list += b.getProduct();
	         model.addElement(list);
   		    }    
   		bugList.setModel(model);     
   	    bugList.setSelectedIndex(0);
       }
    protected void viewBug(Bug theBug) {
        // View the Bug.
    	int index = bugList.getSelectedIndex();
    	generateBugInfoWindow(bugs.get(index));
    }
    
    private void generateBugInfoWindow(Bug aBug) {
    	BugInfoWindow newWin = new BugInfoWindow(aBug);
	}
    
    protected void viewProduct(Product theProduct) {
        // View the Product
    	int index = productList.getSelectedIndex();
    	generateProductInfoWindow(products.get(index));
    }
    
    private void generateProductInfoWindow(Product aProduct) {
    	ProductInfoWindow newWin = new ProductInfoWindow(aProduct);
	}

    protected void viewDeveloper() throws SQLException {
        // View the Product
    	int index = developerList.getSelectedIndex();
    	generateDeveloperInfoWindow(developers.get(index));
    }
    
    private void generateDeveloperInfoWindow(Developer aDeveloper) throws SQLException {
    	DeveloperInfoWindow newWin = new DeveloperInfoWindow(aDeveloper);
	}
    
    private void removeBug () throws SQLException {
    	int index = bugList.getSelectedIndex();
    	generateBugReport(bugs.get(index));
    	
    	if(bugs.get(index).getStatus() == 2) {
    		
        PreparedStatement stmt = myConn1.prepareStatement("DELETE FROM bugs Where name = ?");
      	stmt.setString(1, bugs.get(index).getName());
    	int rs = stmt.executeUpdate();
    	}else {
    		JOptionPane.showMessageDialog(null, "Selected bug has not been fixed!", "Error",
                    JOptionPane.ERROR_MESSAGE);
    	}
  		refresh();
    }

    private void generateBugReport(Bug bug) {
    	PrintWriter writer;
    	long time = System.currentTimeMillis();
    	java.sql.Date date = new java.sql.Date(time);
    	
		try {
			writer = new PrintWriter(bug.getName() + "FixReport.txt", "UTF-8");
			writer.println("Bug Report:");
	    	writer.println("Product bug is in: " + bug.getProduct());
	    	writer.println("The Assigned developer: " + bug.getAssignedDev());
	    	writer.println("Date bug was created: " + bug.getCreated());
	    	writer.println("Date the bug was fixed: " +date);
	    	writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}

	private void rejectBugReport () throws SQLException {
    	int index = bugList.getSelectedIndex();
    	if(bugs.get(index).getStatus() == 0) {
    		  PreparedStatement stmt = myConn1.prepareStatement("DELETE FROM bugs Where name = ?");
    	      stmt.setString(1, bugs.get(index).getName());
    	  	int rs = stmt.executeUpdate();
    	  	refresh();
    	}else {
    		JOptionPane.showMessageDialog(null, "Cannot reject a already approved or fixed bug!", "Error",
                    JOptionPane.ERROR_MESSAGE);
    	}
    }

    private void approveBugReport () throws SQLException {
    	int index = bugList.getSelectedIndex();
    	if(bugs.get(index).getStatus() == 0) {
    		  PreparedStatement stmt = myConn1.prepareStatement("UPDATE bugs set status = ? where name = ?");
    	      stmt.setInt(1, 1);
    		  stmt.setString(2, bugs.get(index).getName());
    	  	  int rs = stmt.executeUpdate();
    	  	  bugs.get(index).setStatus(1);
    	  	  refresh();
    	}else {
    		JOptionPane.showMessageDialog(null, "Cannot approve a already approved or fixed bug!", "Error",
                    JOptionPane.ERROR_MESSAGE);
    	}
    }

    private void assignBugToDev () throws SQLException {
    	int index1 = bugList.getSelectedIndex();
    	int index2 = developerList.getSelectedIndex();
    	
    	if(bugs.get(index1).getStatus() == 1 || bugs.get(index1).getStatus() == 2) {
    		  PreparedStatement stmt = myConn1.prepareStatement("UPDATE bugs set assignedDev = ? where name = ?");
    	      stmt.setString(1, developers.get(index2).getUsername());
    		  stmt.setString(2, bugs.get(index1).getName());
    	  	  int rs = stmt.executeUpdate();
    	  	  refresh();
    	}else {
    		JOptionPane.showMessageDialog(null, "Cannot assign a dev to a already assigned or fixed bug!", "Error",
                    JOptionPane.ERROR_MESSAGE);
    	}	
    }
    
	private void addDev () throws SQLException {
		generateAddDevWindow();
    }

    private void generateAddDevWindow() throws SQLException {
		AddDeveloperWindow addDevWindow = new AddDeveloperWindow();
		refresh();
	}

	private void removeDev () throws SQLException {
		int index = developerList.getSelectedIndex();
    	
        PreparedStatement stmt = myConn3.prepareStatement("DELETE FROM developers Where name = ?");
      	stmt.setString(1, developers.get(index).getName());
      	int rs = stmt.executeUpdate();
      		
      	PreparedStatement stmt2 = myConn4.prepareStatement("DELETE FROM credentials Where username = ?");
       	stmt2.setString(1, developers.get(index).getUsername());
       	int rs2 = stmt2.executeUpdate();
  		
  		PreparedStatement stmt3 = myConn1.prepareStatement("UPDATE bugs SET assignedDev = ? WHERE assignedDev = ?");
       	stmt3.setString(1, null);
       	stmt3.setString(2, developers.get(index).getUsername());
       	int rs3 = stmt3.executeUpdate();
  		refresh();	
  		
    }

    private void updateDev () throws SQLException {
    	int index = developerList.getSelectedIndex();
    	generateUpdateDevWindow(developers.get(index));
    }

    private void generateUpdateDevWindow(Developer aDeveloper) throws SQLException {
		UpdateDeveloperWindow updateDevWindow = new UpdateDeveloperWindow(aDeveloper);
		refresh();
	}

	private void addNewProduct () throws SQLException {
		generateAddNewProductWindow();
    }

    private void generateAddNewProductWindow() throws SQLException {
		AddProductWindow addProductWindow = new AddProductWindow();
		refresh();
	}

	private void removeAProduct () throws SQLException {
		int index = productList.getSelectedIndex();
    	
        PreparedStatement stmt = myConn2.prepareStatement("DELETE FROM products Where name = ?");
      	stmt.setString(1, products.get(index).getName());
  		int rs = stmt.executeUpdate();
  		
  		PreparedStatement stmt2 = myConn1.prepareStatement("DELETE FROM bugs Where fromProduct = ?");
      	stmt2.setString(1, products.get(index).getName());
  		int rs2 = stmt2.executeUpdate();
  		refresh();
    }

    private void updateAProduct () throws SQLException {
    	int index = productList.getSelectedIndex();
    	generateUpdateProductWindow(products.get(index));
    }

    private void generateUpdateProductWindow(Product aProduct) throws SQLException {
		UpdateProductWindow updateProductWindow = new UpdateProductWindow(aProduct);
		refresh();
	}
    
    /*
	public static void main (String[] args) throws SQLException {
        ProjectManagerWindow temp = new ProjectManagerWindow("ManagerName");
    }
    */
}
