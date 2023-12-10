import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManagePostedJobsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable jobsTable;
    private JButton btnEdit;
    private JButton btnClose;
    private JButton btnReopen;
    private JButton btnCancel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String[] columnNames = {"Job Title", "Job Status"};
    private DefaultTableModel model;
    private String jobTitle;
    private String jobDescription;
    private String location;
    private String educationRequirements;
    private int selectedRow;
    private JPanel buttonPanel;

	public ManagePostedJobsGUI(String username) {
		setTitle("Manage Posted Jobs");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        jobsTable = new JTable();
        btnEdit = new JButton("Edit");
        btnClose = new JButton("Close");
        btnReopen = new JButton("Reopen");
        
     // Add action listeners
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle edit button click
                editselectedjob(username);
            }
        });
        
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle close button click
                closeselectedjob(username);
            }
        });

        btnReopen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle reopen button click
                reopenselectedjob(username);
            }
        });
        
        // Set layout
        getContentPane().setLayout(new BorderLayout());

        // Add components to the frame
        getContentPane().add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnClose);
        buttonPanel.add(btnReopen);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        buttonPanel.add(btnCancel);

        // Populate the table with data from the database
        populateJobsTable(jobsTable, username);
	}
	
	public void populateJobsTable(JTable jobsTable, String username) {
        // Create a DefaultTableModel with no data
        model = new DefaultTableModel(null, columnNames);
        
        conn = new DatabaseConnect();
        resultSet = conn.retrieveJobInfo(username);
        // Iterate through the ResultSet and add rows to the model
        try {
			while (resultSet.next()) {
			    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("status")};
			    model.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        // Set the model to the JTable
        jobsTable.setModel(model);
        // Close the ResultSet
        try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	public void editselectedjob(String username) {
		selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            resultSet = conn.editJob(username, jobTitle);
            try {
				if (resultSet.next()) {
				    jobDescription = resultSet.getString("job_description");
				    location = resultSet.getString("location");
				    educationRequirements = resultSet.getString("education_requirements");
				    EditJobGUI editjobgui = new EditJobGUI(username, jobTitle, jobDescription, location, educationRequirements);
			    	editjobgui.show();
				} else {
				    JOptionPane.showMessageDialog(null, "Job not found.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }
	}


    public void closeselectedjob(String username) {
    	selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            conn.closeJob(username, jobTitle);
        }
    }

    public void reopenselectedjob(String username) {
    	selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            conn.reopenJob(username, jobTitle);
        }
    }
    
}
