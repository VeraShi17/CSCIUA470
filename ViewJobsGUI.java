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
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private DefaultTableModel model;
    private String[] columnNames = {"Job Title", "Recruiter Username"};
    private int selectedRow;
    private String jobTitle;
    private String recruiter_username;

	
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
                viewDetailJob();
            }
        });
        
        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applySelectedJob(username);
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
	
	public void populateAvailableJobsTable(JTable jobsTable) {
        model = new DefaultTableModel(null, columnNames);
        conn = new DatabaseConnect();
    	resultSet = conn.retrieveJobs();
    	
        try {
			while (resultSet.next()) {
			    Object[] rowData = {resultSet.getString("job_title"), resultSet.getString("username")};
			    model.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        jobsTable.setModel(model);
        
        try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public void viewDetailJob() {
		selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            recruiter_username = (String) jobsTable.getValueAt(selectedRow, 1);
            ViewJobDetailsGUI viewJobDetailsGUI = new ViewJobDetailsGUI(recruiter_username, jobTitle);
    		viewJobDetailsGUI.show();
        }
	}
	
	public void applySelectedJob(String username) {
		selectedRow = jobsTable.getSelectedRow();
        if (selectedRow != -1) {
            jobTitle = (String) jobsTable.getValueAt(selectedRow, 0);
            recruiter_username = (String) jobsTable.getValueAt(selectedRow, 1);
            conn.applyJob(username, recruiter_username, jobTitle);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a job to apply.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
	}
}
