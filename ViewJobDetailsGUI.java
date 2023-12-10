import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewJobDetailsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
    private JLabel lblJobTitle;
    private JLabel lblJobDescription;
    private JLabel lblLocation;
    private JLabel lblEducationRequirements;
    private JLabel lblRecruiterName;
    private JLabel lblRecruiterPhone;
    private JLabel lblRecruiterEmail;
    private JLabel lblRecruiterCompanyName;
    private JLabel lblRecruiterCompanyDescription;
    private JButton btnCancel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String jobDescription;
    private String location;
    private String educationRequirements;
    private String recruiterName;
    private String recruiterPhone;
    private String recruiterEmail;
    private String recruiterCompanyName;
    private String recruiterCompanyDescription;

    public ViewJobDetailsGUI(String recruiterUsername, String jobTitle) {
        setTitle("Job Details");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create components
        lblJobTitle = new JLabel("Job Title:");
        lblJobDescription = new JLabel("Job Description:");
        lblLocation = new JLabel("Location:");
        lblEducationRequirements = new JLabel("Education Requirements:");
        lblRecruiterName = new JLabel("Recruiter Name:");
        lblRecruiterPhone = new JLabel("Recruiter Phone:");
        lblRecruiterEmail = new JLabel("Recruiter Email:");
        lblRecruiterCompanyName = new JLabel("Recruiter Company Name:");
        lblRecruiterCompanyDescription = new JLabel("Recruiter Company Description:");
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });

        // Set layout
        getContentPane().setLayout(new GridLayout(10, 1));

        // Add components to the frame
        getContentPane().add(lblJobTitle);
        getContentPane().add(lblJobDescription);
        getContentPane().add(lblLocation);
        getContentPane().add(lblEducationRequirements);
        getContentPane().add(lblRecruiterName);
        getContentPane().add(lblRecruiterPhone);
        getContentPane().add(lblRecruiterEmail);
        getContentPane().add(lblRecruiterCompanyName);
        getContentPane().add(lblRecruiterCompanyDescription);
        getContentPane().add(btnCancel);

        // Populate the labels with job and recruiter details
        populateJobDetails(recruiterUsername, jobTitle);
    }

    
    public void populateJobDetails(String recruiterUsername, String jobTitle) {
        conn = new DatabaseConnect();
        resultSet = conn.retrieveJobDetails(jobTitle, recruiterUsername);

        try {
            if (resultSet.next()) {
                jobDescription = resultSet.getString("job_description");
                location = resultSet.getString("location");
                educationRequirements = resultSet.getString("education_requirements");

                lblJobTitle.setText("Job Title: " + jobTitle);
                lblJobDescription.setText("Job Description: " + jobDescription);
                lblLocation.setText("Location: " + location);
                lblEducationRequirements.setText("Education Requirements: " + educationRequirements);

                populateRecruiterDetails(recruiterUsername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the ResultSet, Statement, and Connection in the finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    public void populateRecruiterDetails(String recruiterUsername) {
    	resultSet = conn.retrieveRecruiterDetails(recruiterUsername);
    	try {
			if (resultSet.next()) {
			    recruiterName = resultSet.getString("name");
			    recruiterPhone = resultSet.getString("phone");
			    recruiterEmail = resultSet.getString("email");
			    recruiterCompanyName = resultSet.getString("company_name");
			    recruiterCompanyDescription = resultSet.getString("company_description");

			    lblRecruiterName.setText("Recruiter Name: " + recruiterName);
			    lblRecruiterPhone.setText("Recruiter Phone: " + recruiterPhone);
			    lblRecruiterEmail.setText("Recruiter Email: " + recruiterEmail);
			    lblRecruiterCompanyName.setText("Recruiter Company Name: " + recruiterCompanyName);
			    lblRecruiterCompanyDescription.setText("Recruiter Company Description: " + recruiterCompanyDescription);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        // Close the ResultSet
        try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }


}
