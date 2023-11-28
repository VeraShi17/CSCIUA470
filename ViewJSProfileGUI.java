import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ViewJSProfileGUI extends JFrame {

	private JPanel contentPane;
    private JLabel lblName;
    private JLabel lblPhone;
    private JLabel lblEmail;
    private JLabel lblSkills;
    private JLabel lblEducation;
    private JLabel lblWork;
    private JButton btnCancel;

    public ViewJSProfileGUI(String jsUsername) {
        setTitle("Job Seeker Profile");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create components
        lblName = new JLabel("Name:");
        lblPhone = new JLabel("Phone:");
        lblEmail = new JLabel("Email:");
        lblSkills = new JLabel("Skills:");
        lblEducation = new JLabel("Education Experience:");
        lblWork = new JLabel("Work Experience:");
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });

        // Set layout
        getContentPane().setLayout(new GridLayout(7, 1));

        // Add components to the frame
        getContentPane().add(lblName);
        getContentPane().add(lblPhone);
        getContentPane().add(lblEmail);
        getContentPane().add(lblSkills);
        getContentPane().add(lblEducation);
        getContentPane().add(lblWork);
        getContentPane().add(btnCancel);

        // Populate the labels with jobseeker details
        populateJsProfile(jsUsername);
    }

    private void populateJsProfile(String jsUsername) {
        try (Connection connection = DatabaseConnect.connect()) {
            String selectJsProfileQuery = "SELECT * FROM jobseeker WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectJsProfileQuery)) {
                preparedStatement.setString(1, jsUsername);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
            
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String skills = resultSet.getString("skills");
                    String education = resultSet.getString("education");
                    String work = resultSet.getString("work_experience");
                   

                    lblName.setText("Name: " + name);
                    lblPhone.setText("Phone: " + phone);
                    lblEmail.setText("Email: " + email);
                    lblSkills.setText("Skills: " + skills);
                    lblEducation.setText("Education Experience: " + education);
                    lblWork.setText("Work Experience: " + work);

                }

                // Close the ResultSet
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
