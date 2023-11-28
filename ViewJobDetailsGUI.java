import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewJobDetailsGUI extends JFrame {

    private JPanel contentPane;
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

    private void populateJobDetails(String recruiterUsername, String jobTitle) {
        try (Connection connection = DatabaseConnect.connect()) {
            String selectJobDetailsQuery = "SELECT * FROM job WHERE job_title = ? and username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobDetailsQuery)) {
                preparedStatement.setString(1, jobTitle);
                preparedStatement.setString(2, recruiterUsername);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
            
                    String jobDescription = resultSet.getString("job_description");
                    String location = resultSet.getString("location");
                    String educationRequirements = resultSet.getString("education_requirements");
                   

                    lblJobTitle.setText("Job Title: " + jobTitle);
                    lblJobDescription.setText("Job Description: " + jobDescription);
                    lblLocation.setText("Location: " + location);
                    lblEducationRequirements.setText("Education Requirements: " + educationRequirements);

                    populateRecruiterDetails(recruiterUsername);
                }

                // Close the ResultSet
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateRecruiterDetails(String recruiterUsername) {
        try (Connection connection = DatabaseConnect.connect()) {
            String selectRecruiterDetailsQuery = "SELECT * FROM recruiter WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectRecruiterDetailsQuery)) {
                preparedStatement.setString(1, recruiterUsername);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String recruiterName = resultSet.getString("name");
                    String recruiterPhone = resultSet.getString("phone");
                    String recruiterEmail = resultSet.getString("email");
                    String recruiterCompanyName = resultSet.getString("company_name");
                    String recruiterCompanyDescription = resultSet.getString("company_description");

                    lblRecruiterName.setText("Recruiter Name: " + recruiterName);
                    lblRecruiterPhone.setText("Recruiter Phone: " + recruiterPhone);
                    lblRecruiterEmail.setText("Recruiter Email: " + recruiterEmail);
                    lblRecruiterCompanyName.setText("Recruiter Company Name: " + recruiterCompanyName);
                    lblRecruiterCompanyDescription.setText("Recruiter Company Description: " + recruiterCompanyDescription);
                }

                // Close the ResultSet
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
