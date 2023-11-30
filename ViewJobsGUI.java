import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewJobsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable jobsTable;
    private JButton btnDetail;
    private JButton btnApply;
    private JButton btnCancel;
    private JPanel buttonPanel;

	
	public ViewJobsGUI(String username) {
		setTitle("View Available Jobs");
        setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jobsTable = new JTable();
        btnDetail = new JButton("View Details");
        btnApply = new JButton("Apply");
        btnCancel = new JButton("Cancel");
        
        btnDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jobsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
                    String recruiter_username = (String) jobsTable.getValueAt(selectedRow, 1);
                    viewDetailJob(recruiter_username, jobTitle);
                }
            }
        });
        
        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jobsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
                    String recruiter_username = (String) jobsTable.getValueAt(selectedRow, 1);
                    applyJob(username, recruiter_username, jobTitle);
                }
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        
        getContentPane().setLayout(new BorderLayout());

        // Add components to the frame
        getContentPane().add(new JScrollPane(jobsTable), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.add(btnDetail);
        buttonPanel.add(btnApply);
        buttonPanel.add(btnCancel);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        populateAvailableJobsTable(jobsTable);
	}
	
	private void populateAvailableJobsTable(JTable jobsTable) {
		String[] columnNames = {"Job Title", "Recruiter Username"};

        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try (Connection connection = DatabaseConnect.connect()) {
            String selectAvailableJobsQuery = "SELECT job_title, username FROM job WHERE status = 'open'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectAvailableJobsQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("username")};
                    model.addRow(rowData);
                }

                jobsTable.setModel(model);
                
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void viewDetailJob(String recruiter_username, String jobTitle) {
		ViewJobDetailsGUI viewJobDetailsGUI = new ViewJobDetailsGUI(recruiter_username, jobTitle);
		viewJobDetailsGUI.show();
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
