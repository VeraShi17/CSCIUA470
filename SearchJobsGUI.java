import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchJobsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
    private JTextField txtSearch;
    private JTable jobsTable;
    private JButton btnSearch;
    private JButton btnViewDetails;
    private JButton btnApply;
    private JButton btnCancel;
    private String selectedJobTitle;
    private String selectedRecruiterUsername;
    private JPanel searchPanel;
    private JPanel buttonPanel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String[] columnNames = {"Job Title", "Recruiter Username"};
    private DefaultTableModel model;
    private int selectedRow;
    private String searchTerm;

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
        searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by Title:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        getContentPane().add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnApply);
        buttonPanel.add(btnCancel);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void searchJobs() {
        searchTerm = txtSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            populateJobsTable(searchTerm);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a search term.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void populateJobsTable(String searchTerm) {
        // Create a DefaultTableModel with no data
        model = new DefaultTableModel(null, columnNames);

        // Iterate through the ResultSet and add rows to the model
        conn = new DatabaseConnect();
    	resultSet = conn.retrieveSearchedJobs(searchTerm);
        try {
			while (resultSet.next()) {
			    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("username")};
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

    public void viewJobDetails(String jobSeekerUsername) {
        selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedJobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            selectedRecruiterUsername = (String) jobsTable.getValueAt(selectedRow, 1);
            ViewJobDetailsGUI viewJobDetailsGUI = new ViewJobDetailsGUI(selectedRecruiterUsername, selectedJobTitle);
    		viewJobDetailsGUI.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a job to view details.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void applyForJob(String jobSeekerUsername) {
        selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            selectedJobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            selectedRecruiterUsername = (String) jobsTable.getValueAt(selectedRow, 1);
            conn.applyJob(jobSeekerUsername, selectedRecruiterUsername, selectedJobTitle);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a job to apply.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    

}
