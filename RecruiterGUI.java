import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RecruiterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;

	
	public RecruiterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Welcome to CareerLinker!");
		lblTitle.setBounds(115, 20, 218, 26);
		lblTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		contentPane.add(lblTitle);
		
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
		
		JButton btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enteredUsername = txtUsername.getText().trim();
		        String enteredPassword = txtPassword.getText();
		        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
		        boolean isValidLogin = checkRecruiterLogin(enteredUsername, enteredPassword);
		        
		        if (isValidLogin) {
		            JOptionPane.showMessageDialog(null, "Login successful!");
		            openRecruiterMainGUI(enteredUsername);
		        } else {
		            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnLogin.setBounds(80, 201, 117, 29);
		contentPane.add(btnLogin);
		
		JButton btnSignup = new JButton("Sign up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recruitersignupshow();
			}
		});
		btnSignup.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnSignup.setBounds(255, 201, 117, 29);
		contentPane.add(btnSignup);
	}
	
	public void recruitersignupshow() {
		RecruiterSignupGUI resignupgui = new RecruiterSignupGUI();
		resignupgui.show();
	}
	
	public boolean checkRecruiterLogin(String username, String password) {
	    try (Connection connection = DatabaseConnect.connect()) {
	        String query = "SELECT * FROM recruiter WHERE username=? AND password=?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            return resultSet.next();
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
//	        JOptionPane.showMessageDialog(null, "Error checking recruiter login.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false; 
	    }
	}

	public void openRecruiterMainGUI(String username) {
	     RecruiterMainGUI recruiterMainGUI = new RecruiterMainGUI(username);
	     recruiterMainGUI.show();
	}
}
