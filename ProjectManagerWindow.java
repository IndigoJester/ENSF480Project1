import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProjectManagerWindow {
	private static Connection myConn1, myConn2, myConn3;
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
                display.assignBugToDev();
            } else if (e.getSource() == addDeveloper) {
                display.addDev();
            } else if (e.getSource() == removeDeveloper) {
                try {
					display.removeDev();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == updateDeveloper) {
                display.updateDev();
            } else if (e.getSource() == addProduct) {
                display.addNewProduct();
            } else if (e.getSource() == removeProduct) {
                try {
					display.removeAProduct();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            } else if (e.getSource() == updateProduct) {
                display.updateAProduct();
            }
        }
    }
	public ProjectManagerWindow (String username) throws SQLException {
		myConn1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bug?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/product?autoReconnect=true&useSSL=false", "root", "Bigpapi");
    	myConn3 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/developer?autoReconnect=true&useSSL=false", "root", "Bigpapi");
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
                    int index = developerList.locationToIndex(evt.getPoint());
                    Developer get = developers.get(index);
                    viewDeveloper(get);
                }
            }
        });
        developerList.setFont(new Font("Courier", Font.BOLD, 14));
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
  					  rs.getDate("created"), rs.getBoolean("approved"),rs.getString("details"), 
  					  rs.getInt("status"), rs.getString("assignedDev"));
  			bugs.add(bug);
  			
  			DefaultListModel<String> model = new DefaultListModel<String>();
  		    for(Bug b : bugs){
  		         model.addElement(b.getName().toString());
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
			         model.addElement(p.getName().toString());
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
			         model.addElement(d.getName().toString());
			    }    
			    developerList.setModel(model);     
			    developerList.setSelectedIndex(0);
			}
    }

    protected void showBugsFrom(Product activeProduct) throws SQLException {
   	 // Display the bugs from the product argument
		bugs.removeAllElements();
	
   	PreparedStatement stmt = myConn1.prepareStatement("SELECT * FROM bugs WHERE fromProduct = ?");
   	stmt.setString(1, activeProduct.getName());
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Bug bug = new Bug(rs.getString("name"), rs.getString("fromProduct"),
					  rs.getDate("created"), rs.getBoolean("approved"),rs.getString("details"), 
					  rs.getInt("status"), rs.getString("assignedDev"));
			bugs.add(bug);
			
			DefaultListModel<String> model = new DefaultListModel<String>();
		    for(Bug b : bugs){
		         model.addElement(b.getName().toString());
		    }    
		    bugList.setModel(model);     
		    bugList.setSelectedIndex(0);
			}
   }
    protected void viewProduct(Product theProduct) {
        // View the Product
    	generateProductInfoWindow();
    	System.out.println(theProduct.getName());
    }
    
    private void generateProductInfoWindow() {
    	ProductInfoWindow newWin = new ProductInfoWindow();
	}
    
    protected void viewBug(Bug theBug) {
        // View the Bug.
    	generateBugInfoWindow();
    	System.out.println(theBug.getName());	
    }
    
    private void generateBugInfoWindow() {
    	BugInfoWindow newWin = new BugInfoWindow();
	}
    
    protected void viewDeveloper(Developer theDeveloper) {
        // View the Product
    	generateDeveloperInfoWindow();
    	System.out.println(theDeveloper.getName());
    }
    
    private void generateDeveloperInfoWindow() {
    	DeveloperInfoWindow newWin = new DeveloperInfoWindow();
	}
    
    private void removeBug () throws SQLException {
    	int index = bugList.getSelectedIndex();
    	
        PreparedStatement stmt = myConn1.prepareStatement("DELETE FROM bugs Where name = ?");
      	stmt.setString(1, bugs.get(index).getName());
    	int rs = stmt.executeUpdate();
  		refresh();
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
    		  PreparedStatement stmt = myConn1.prepareStatement("UPDATE bugs set status = ? where first_name = ?");
    	      stmt.setInt(1, 1);
    		  stmt.setString(2, bugs.get(index).getName());
    	  	  ResultSet rs = stmt.executeQuery();
    	  	  bugs.get(index).setStatus(1);
    	  	  refresh();
    	}else {
    		JOptionPane.showMessageDialog(null, "Cannot approve a already approved or fixed bug!", "Error",
                    JOptionPane.ERROR_MESSAGE);
    	}
    }

    private void assignBugToDev () {
    	
    }
    
	private void addDev () {
		generateAddDevWindow();
    }

    private void generateAddDevWindow() {
		AddDeveloperWindow addDevWindow = new AddDeveloperWindow();
	}

	private void removeDev () throws SQLException {
		int index = developerList.getSelectedIndex();
    	
        PreparedStatement stmt = myConn3.prepareStatement("DELETE FROM developers Where name = ?");
      	stmt.setString(1, developers.get(index).getName());
      	int rs = stmt.executeUpdate();
  		refresh();	
    }

    private void updateDev () {
    	generateUpdateDevWindow();
    }

    private void generateUpdateDevWindow() {
		UpdateDeveloperWindow updateDevWindow = new UpdateDeveloperWindow();
	}

	private void addNewProduct () {
		generateAddNewProductWindow();
    }

    private void generateAddNewProductWindow() {
		AddProductWindow addProductWindow = new AddProductWindow();
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

    private void updateAProduct () {
    	generateUpdateProductWindow();
    }

    private void generateUpdateProductWindow() {
		UpdateProductWindow updateProductWindow = new UpdateProductWindow();
		
	}

	public static void main (String[] args) throws SQLException {
        ProjectManagerWindow temp = new ProjectManagerWindow("ManagerName");
    }
}
