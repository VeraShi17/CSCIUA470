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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SearchJobsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSearch;
    private JTable jobsTable;
    private JButton btnSearch;
    private JButton btnViewDetails;
    private JButton btnApply;
    private JButton btnCancel;

    private String selectedJobTitle;
    private String selectedRecruiterUsername;

    public SearchJobsGUI(String jobSeekerUsername) {
        setTitle("Search Jobs");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        txtSearch = new JTextField();
        txtSearch.setColumns(15);
        jobsTable = new JTable();
        btnSearch = new JButton("Search");
        btnViewDetails = new JButton("View Details");
        btnApply = new JButton("Apply");
        btnCancel = new JButton("Cancel");

        // Add action listeners
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchJobs();
            }
        });

        btnViewDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewJobDetails(jobSeekerUsername);
            }
        });

        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyForJob(jobSeekerUsername);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Set layout
        getContentPane().setLayout(new BorderLayout());

        // Add components to the frame
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by Title:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        getContentPane().add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnApply);
        buttonPanel.add(btnCancel);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void searchJobs() {
        String searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            populateJobsTable(searchTerm);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a search term.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void populateJobsTable(String searchTerm) {
        // Define the column names
        String[] columnNames = {"Job Title", "Recruiter Username"};

        // Create a DefaultTableModel with no data
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectJobsQuery = "SELECT job_title, username FROM job WHERE status = 'open' AND job_title LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobsQuery)) {
                preparedStatement.setString(1, "%" + searchTerm + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the ResultSet and add rows to the model
                while (resultSet.next()) {
                    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("username")};
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

    private void viewJobDetails(String jobSeekerUsername) {
        int selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedJobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            selectedRecruiterUsername = (String) jobsTable.getValueAt(selectedRow, 1);
            ViewJobDetailsGUI viewJobDetailsGUI = new ViewJobDetailsGUI(selectedRecruiterUsername, selectedJobTitle);
    		viewJobDetailsGUI.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a job to view details.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void applyForJob(String jobSeekerUsername) {
        int selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedJobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            selectedRecruiterUsername = (String) jobsTable.getValueAt(selectedRow, 1);
            applyJob(jobSeekerUsername, selectedRecruiterUsername, selectedJobTitle);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a job to apply.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void applyJob(String jobseeker_username, String recruiter_username, String jobTitle) {
		try (Connection connection = DatabaseConnect.connect()) {
	        String insertApplicationQuery = "INSERT INTO applicant (recruiter_username, job_title, jobseeker_username, application_status) VALUES (?, ?, ?, 'Pending')";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertApplicationQuery)) {
	            preparedStatement.setString(1, recruiter_username);
	            preparedStatement.setString(2, jobTitle);
	            preparedStatement.setString(3, jobseeker_username);

	            preparedStatement.executeUpdate();
	        }
	        JOptionPane.showMessageDialog(null, "Application submitted successfully!");
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error submitting application.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

}