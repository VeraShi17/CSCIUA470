import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;


public class ViewJSProfileGUI extends JFrame {

	private static final long serialVersionUID = 1L;
    private JLabel lblName;
    private JLabel lblPhone;
    private JLabel lblEmail;
    private JLabel lblSkills;
    private JLabel lblEducation;
    private JLabel lblWork;
    private JButton btnCancel;
    private DatabaseConnect conn;
    private ResultSet resultSet;
    private String name;
    private String phone;
    private String email;
    private String skills;
    private String education;
    private String work;

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

    public void populateJsProfile(String jsUsername) {
    	conn = new DatabaseConnect();
    	resultSet = conn.retrieveJSProfile(jsUsername);
    	try {
			if (resultSet.next()) {
			    
			    name = resultSet.getString("name");
			    phone = resultSet.getString("phone");
			    email = resultSet.getString("email");
			    skills = resultSet.getString("skills");
			    education = resultSet.getString("education");
			    work = resultSet.getString("work_experience");
			   
			    lblName.setText("Name: " + name);
			    lblPhone.setText("Phone: " + phone);
			    lblEmail.setText("Email: " + email);
			    lblSkills.setText("Skills: " + skills);
			    lblEducation.setText("Education Experience: " + education);
			    lblWork.setText("Work Experience: " + work);

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
