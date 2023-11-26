import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class ManagePostedJobsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable jobsTable;
    private JButton btnEdit;
    private JButton btnClose;
    private JButton btnReopen;

	
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
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnClose);
        buttonPanel.add(btnReopen);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the table with data from the database
        populateJobsTable(jobsTable, username);
	}
	
	public void populateJobsTable(JTable jobsTable, String username) {
        // Assuming 'jobsTable' is a JTable component in your GUI

        // Define the column names
        String[] columnNames = {"Job Title", "Job Status"};

        // Create a DefaultTableModel with no data
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectJobsQuery = "SELECT job_title, job_status FROM job WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobsQuery)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the ResultSet and add rows to the model
                while (resultSet.next()) {
                    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("job_status")};
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
        // Implement the logic for editing a job
        // You can open a new GUI or perform other actions based on your requirements
    }

    private void closeJob(String username, String jobTitle) {
        // Implement the logic for closing a job
        // You can update the job status in the database and refresh the table
    }

    private void reopenJob(String username, String jobTitle) {
        // Implement the logic for reopening a job
        // You can update the job status in the database and refresh the table
    }
}
