import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewApplicantsGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable applicantsTable;
    private JButton btnViewProfile;
    private JButton btnAccept;
    private JButton btnReject;
    private JButton btnCancel;

    public ViewApplicantsGUI(String recruiterUsername, String jobTitle) {
        setTitle(jobTitle + " - View Applicants");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        applicantsTable = new JTable();
        btnViewProfile = new JButton("View Profile");
        btnAccept = new JButton("Accept");
        btnReject = new JButton("Reject");
        btnCancel = new JButton("Cancel");

        // Add action listeners
        btnViewProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle view profile button click
                int selectedRow = applicantsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobSeekerUsername = (String) applicantsTable.getValueAt(selectedRow, 0);
                    viewProfile(jobSeekerUsername);
                }
            }
        });

        btnAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle accept button click
                int selectedRow = applicantsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobSeekerUsername = (String) applicantsTable.getValueAt(selectedRow, 0);
                    acceptApplication(recruiterUsername, jobTitle, jobSeekerUsername);
                }
            }
        });

        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle reject button click
                int selectedRow = applicantsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobSeekerUsername = (String) applicantsTable.getValueAt(selectedRow, 0);
                    rejectApplication(recruiterUsername, jobTitle, jobSeekerUsername);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle cancel button click
                dispose();
            }
        });

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(applicantsTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnViewProfile);
        buttonPanel.add(btnAccept);
        buttonPanel.add(btnReject);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the table with data from the database
        populateApplicantsTable(applicantsTable, recruiterUsername, jobTitle);
    }

    public void populateApplicantsTable(JTable applicantsTable, String recruiterUsername, String jobTitle) {
        // Define the column names
        String[] columnNames = {"JobSeeker Username", "Application Status"};

        // Create a DefaultTableModel with no data
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectApplicantsQuery = "SELECT jobseeker_username, application_status FROM applicant WHERE recruiter_username = ? AND job_title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectApplicantsQuery)) {
                preparedStatement.setString(1, recruiterUsername);
                preparedStatement.setString(2, jobTitle);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the ResultSet and add rows to the model
                while (resultSet.next()) {
                    Object[] rowData = {resultSet.getString("jobseeker_username"), resultSet.getString("application_status")};
                    model.addRow(rowData);
                }

                // Set the model to the JTable
                applicantsTable.setModel(model);

                // Close the ResultSet
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewProfile(String jobSeekerUsername) {
        ViewJSProfileGUI viewjsprofilegui = new ViewJSProfileGUI(jobSeekerUsername);
        viewjsprofilegui.show();
    }

    private void acceptApplication(String recruiterUsername, String jobTitle, String jobSeekerUsername) {
    	try (Connection connection = DatabaseConnect.connect()) {
            // Update the application status to "accept" in the applicant table
            String updateApplicationQuery = "UPDATE applicant SET application_status = 'accept' " +
                    "WHERE recruiter_username = ? AND job_title = ? AND jobseeker_username = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateApplicationQuery)) {
                preparedStatement.setString(1, recruiterUsername);
                preparedStatement.setString(2, jobTitle);
                preparedStatement.setString(3, jobSeekerUsername);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Application status updated successfully
                    System.out.println("Application accepted successfully!");
                } else {
                    // No rows were affected, application not found or not updated
                    System.out.println("Application not found or not updated.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rejectApplication(String recruiterUsername, String jobTitle, String jobSeekerUsername) {
    	try (Connection connection = DatabaseConnect.connect()) {
            // Update the application status to "reject" in the applicant table
            String updateApplicationQuery = "UPDATE applicant SET application_status = 'reject' " +
                    "WHERE recruiter_username = ? AND job_title = ? AND jobseeker_username = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateApplicationQuery)) {
                preparedStatement.setString(1, recruiterUsername);
                preparedStatement.setString(2, jobTitle);
                preparedStatement.setString(3, jobSeekerUsername);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Application status updated successfully
                    System.out.println("Application rejected successfully!");
                } else {
                    // No rows were affected, application not found or not updated
                    System.out.println("Application not found or not updated.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
