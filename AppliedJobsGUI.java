import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppliedJobsGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable jobsTable;
//    private JButton btnCancelApplication;
    private JButton btnCancel;
    private JPanel buttonPanel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String[] columnNames = {"Job Title", "Job Status", "Application Status"};
    private DefaultTableModel model;

    public AppliedJobsGUI(String jobseekerUsername) {
        setTitle("Applied Jobs");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        jobsTable = new JTable();
//        btnCancelApplication = new JButton("Cancel Application");
        btnCancel = new JButton("Cancel");

        // Add action listeners
//        btnCancelApplication.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                // Handle cancel application button click
//                int selectedRow = jobsTable.getSelectedRow();
//                if (selectedRow != -1) {
//                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
//                    cancelApplication(jobseekerUsername, jobTitle);
//                }
//            }
//        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle cancel button click
                dispose();
            }
        });

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        buttonPanel = new JPanel();
//        buttonPanel.add(btnCancelApplication);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the table with data from the database
        populateAppliedJobsTable(jobsTable, jobseekerUsername);
    }

    public void populateAppliedJobsTable(JTable jobsTable, String jobseekerUsername) {
        // Create a DefaultTableModel with no data
        model = new DefaultTableModel(null, columnNames);

        // Iterate through the ResultSet and add rows to the model
        conn = new DatabaseConnect();
        resultSet = conn.retrieveAppliedJobs(jobseekerUsername);
        try {
			while (resultSet.next()) {
			    Object[] rowData = {
			        resultSet.getString("job_title"),
			        resultSet.getString("status"),
			        resultSet.getString("application_status")
			    };
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

//    private void cancelApplication(String jobseekerUsername, String jobTitle) {
//    	try (Connection connection = DatabaseConnect.connect()) {
//            // Update the application status to "Canceled" in the database
//            String updateApplicationStatusQuery = "UPDATE applicant SET application_status = 'Canceled' " +
//                    "WHERE jobseeker_username = ? AND job_title = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(updateApplicationStatusQuery)) {
//                preparedStatement.setString(1, jobseekerUsername);
//                preparedStatement.setString(2, jobTitle);
//                preparedStatement.executeUpdate();
//            }
//
//            // Refresh the table to reflect the updated data
//            populateAppliedJobsTable(jobsTable, jobseekerUsername);
//
//            JOptionPane.showMessageDialog(null, "Application canceled successfully!");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error canceling application.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

}
