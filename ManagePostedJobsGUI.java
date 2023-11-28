import java.awt.BorderLayout;
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
                int selectedRow = jobsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
                    editJob(username, jobTitle);
                }
            }
        });
        
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle close button click
                int selectedRow = jobsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
                    closeJob(username, jobTitle);
                }
            }
        });

        btnReopen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle reopen button click
                int selectedRow = jobsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
                    reopenJob(username, jobTitle);
                }
            }
        });
        
     // Set layout
        getContentPane().setLayout(new BorderLayout());

        // Add components to the frame
        getContentPane().add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
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
        // Define the column names
        String[] columnNames = {"Job Title", "Job Status"};

        // Create a DefaultTableModel with no data
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectJobsQuery = "SELECT job_title, status FROM job WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobsQuery)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the ResultSet and add rows to the model
                while (resultSet.next()) {
                    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("status")};
                    model.addRow(rowData);
                }
                
             // Set the model to the JTable
                jobsTable.setModel(model);

                // Close the ResultSet
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	private void editJob(String username, String jobTitle) {
		try (Connection connection = DatabaseConnect.connect()) {
	        String selectJobQuery = "SELECT * FROM job WHERE username = ? AND job_title = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobQuery)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, jobTitle);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                String jobDescription = resultSet.getString("job_description");
	                String location = resultSet.getString("location");
	                String educationRequirements = resultSet.getString("education_requirements");
	                editjobgui_show(username, jobTitle, jobDescription, location, educationRequirements);
	            } else {
	                JOptionPane.showMessageDialog(null, "Job not found.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job information.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
    }

    private void closeJob(String username, String jobTitle) {
    	try (Connection connection = DatabaseConnect.connect()) {
            String closeJobQuery = "UPDATE job SET status = 'closed' WHERE username = ? AND job_title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(closeJobQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, jobTitle);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Job closed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Job not found or already closed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error closing job.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reopenJob(String username, String jobTitle) {
    	try (Connection connection = DatabaseConnect.connect()) {
            String reopenJobQuery = "UPDATE job SET status = 'open' WHERE username = ? AND job_title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(reopenJobQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, jobTitle);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Job reopened successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Job not found or already open.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reopening job.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editjobgui_show(String username, String jobTitle, String jobDescription, String location, String educationRequirements) {
    	EditJobGUI editjobgui = new EditJobGUI(username, jobTitle, jobDescription, location, educationRequirements);
    	editjobgui.show();
    }
}
