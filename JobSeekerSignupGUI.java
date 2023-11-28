import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JobSeekerSignupGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private String username;
	
	public JobSeekerSignupGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignup = new JLabel("Job Seeker Sign Up");
		lblSignup.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		lblSignup.setBounds(134, 6, 192, 26);
		contentPane.add(lblSignup);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblUsername.setBounds(80, 81, 84, 16);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblPassword.setBounds(80, 141, 84, 16);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtUsername.setBounds(230, 76, 142, 26);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtPassword.setBounds(230, 136, 142, 26);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnSign = new JButton("Sign up");
		btnSign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verify whether 1. username exists 2. txt empty
				// 1 username exists
		        String enteredUsername = txtUsername.getText().trim();
		        boolean usernameExists = checkUsernameExists(enteredUsername);

		        // 2 txt empty
		        boolean usernameIsEmpty = enteredUsername.isEmpty();
		        boolean passwordIsEmpty = txtPassword.getText().isEmpty();
		        
		        // warning message
		        if (usernameExists || usernameIsEmpty || passwordIsEmpty) {
		            StringBuilder message = new StringBuilder("Please correct the following issues:\n");

		            if (usernameExists) {
		                message.append("- Username already exists.\n");
		            }

		            if (usernameIsEmpty) {
		                message.append("- Username is required.\n");
		            }

		            if (passwordIsEmpty) {
		                message.append("- Password is required.\n");
		            }

		            JOptionPane.showMessageDialog(null, message.toString(), "Warning", JOptionPane.WARNING_MESSAGE);
		        } else {
		            // pass the verification, add information into table recruiter
		        	String username = txtUsername.getText().trim();
		        	String password = txtPassword.getText();
		        	
		        	registerUsernameAndPasswordJobSeeker(username, password);
		        	jobseekerinfoshow(username);
		        	dispose();
		        }
		    }
		});
		btnSign.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnSign.setBounds(80, 201, 117, 29);
		contentPane.add(btnSign);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnCancel.setBounds(255, 201, 117, 29);
		contentPane.add(btnCancel);
	}
	
	public void registerUsernameAndPasswordJobSeeker(String username, String password) {
        try (Connection connection = DatabaseConnect.connect()) {
            String insertUserQuery = "INSERT INTO jobseeker (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Username and password registered successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error registering username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkUsernameExists(String username) {
        return DatabaseConnect.checkRecruiterUsernameExists(username) || DatabaseConnect.checkJobSeekerUsernameExists(username);
    }
    
    public void jobseekerinfoshow(String username) {
		JobSeekerInfoGUI jobseekerinfogui = new JobSeekerInfoGUI(username);
		jobseekerinfogui.show();
	}

}
