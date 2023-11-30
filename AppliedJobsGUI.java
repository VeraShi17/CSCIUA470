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

    private void populateAppliedJobsTable(JTable jobsTable, String jobseekerUsername) {
        // Define the column names
        String[] columnNames = {"Job Title", "Job Status", "Application Status"};

        // Create a DefaultTableModel with no data
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectJobsQuery = "SELECT a.job_title, j.status, a.application_status " +
                                    "FROM applicant a " +
                                    "JOIN job j ON a.recruiter_username = j.username AND a.job_title = j.job_title " +
                                    "WHERE a.jobseeker_username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobsQuery)) {
                preparedStatement.setString(1, jobseekerUsername);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the ResultSet and add rows to the model
                while (resultSet.next()) {
                    Object[] rowData = {
                        resultSet.getString("job_title"),
                        resultSet.getString("status"),
                        resultSet.getString("application_status")
                    };
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
