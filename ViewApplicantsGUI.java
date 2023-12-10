import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewApplicantsGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable applicantsTable;
    private JButton btnViewProfile;
    private JButton btnAccept;
    private JButton btnReject;
    private JButton btnCancel;
    private JPanel buttonPanel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String[] columnNames = {"JobSeeker Username", "Application Status"};
    private DefaultTableModel model;
    private int selectedRow;
    private String jobSeekerUsername;

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
                viewProfile();
            }
        });

        btnAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acceptSelectedApplicant(recruiterUsername, jobTitle);
            }
        });

        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rejectSelectedApplicant(recruiterUsername, jobTitle);
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

        buttonPanel = new JPanel();
        buttonPanel.add(btnViewProfile);
        buttonPanel.add(btnAccept);
        buttonPanel.add(btnReject);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the table with data from the database
        populateApplicantsTable(applicantsTable, recruiterUsername, jobTitle);
    }

    public void populateApplicantsTable(JTable applicantsTable, String recruiterUsername, String jobTitle) {
        model = new DefaultTableModel(null, columnNames);

     // Iterate through the ResultSet and add rows to the model
        conn = new DatabaseConnect();
        resultSet = conn.retrieveApplicantInfo(recruiterUsername, jobTitle);
        try {
			while (resultSet.next()) {
			    Object[] rowData = {resultSet.getString("jobseeker_username"), resultSet.getString("application_status")};
			    model.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        // Set the model to the JTable
        applicantsTable.setModel(model);

        // Close the ResultSet
        try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void viewProfile() {
    	// Handle view profile button click
        selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobSeekerUsername = (String) applicantsTable.getValueAt(selectedRow, 0);
            ViewJSProfileGUI viewjsprofilegui = new ViewJSProfileGUI(jobSeekerUsername);
            viewjsprofilegui.show();
        }
    }

    public void acceptSelectedApplicant(String recruiterUsername, String jobTitle) {
    	// Handle accept button click
        selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobSeekerUsername = (String) applicantsTable.getValueAt(selectedRow, 0);
            conn.acceptApplication(recruiterUsername, jobTitle, jobSeekerUsername);
        }
    }
    
    public void rejectSelectedApplicant(String recruiterUsername, String jobTitle) {
    	// Handle reject button click
        selectedRow = applicantsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobSeekerUsername = (String) applicantsTable.getValueAt(selectedRow, 0);
            conn.rejectApplication(recruiterUsername, jobTitle, jobSeekerUsername);
        }
    }

    
}
