import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReviewApplicationsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
    private JTable jobsTable;
    private JButton btnViewApplicants;
    private JButton btnCancel;
    private JPanel buttonPanel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String[] columnNames = {"Job Title", "Job Status"};
    private DefaultTableModel model;
    private int selectedRow;
    private String jobTitle;

    public ReviewApplicationsGUI(String recruiterUsername) {
        setTitle("Review Applications");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        jobsTable = new JTable();
        btnViewApplicants = new JButton("View Applicants");
        btnCancel = new JButton("Cancel");

        // Add action listeners
        btnViewApplicants.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewselectedapplicant(recruiterUsername);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.add(btnViewApplicants);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the table with data from the database
        populateJobsTable(jobsTable, recruiterUsername);
    }

    public void populateJobsTable(JTable jobsTable, String recruiterUsername) {
    	// Create a DefaultTableModel with no data
        model = new DefaultTableModel(null, columnNames);
        conn = new DatabaseConnect();
        resultSet = conn.retrieveJobInfo(recruiterUsername);
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

    public void viewselectedapplicant(String recruiterUsername) {
    	// Handle view applicants button click
        selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            ViewApplicantsGUI viewapplicantsgui = new ViewApplicantsGUI(recruiterUsername, jobTitle);
            viewapplicantsgui.show();
        }
    }

}
