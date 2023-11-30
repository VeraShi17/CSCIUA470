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
                // Handle view applicants button click
                int selectedRow = jobsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
                    viewApplicants(recruiterUsername, jobTitle);
                }
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
        // Define the column names
        String[] columnNames = {"Job Title", "Job Status"};

        // Create a DefaultTableModel with no data
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectJobsQuery = "SELECT job_title, status FROM job WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobsQuery)) {
                preparedStatement.setString(1, recruiterUsername);
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

    private void viewApplicants(String recruiterUsername, String jobTitle) {
        ViewApplicantsGUI viewapplicantsgui = new ViewApplicantsGUI(recruiterUsername, jobTitle);
        viewapplicantsgui.show();
    }

}
