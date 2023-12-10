import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JobseekerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel lblTitle;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JButton btnSignup;
	private String username;
	private String password;
	private DatabaseConnect conn;
	private boolean isValidLogin;
	
	public JobseekerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitle = new JLabel("Welcome to CareerLinker!");
		lblTitle.setBounds(115, 20, 218, 26);
		lblTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		contentPane.add(lblTitle);
		
		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblUsername.setBounds(80, 81, 84, 16);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("Password");
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
		
		btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = txtUsername.getText().trim();
		        password = txtPassword.getText();
		        jobSeekerLogin();
			}
		});
		btnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnLogin.setBounds(80, 201, 117, 29);
		contentPane.add(btnLogin);
		
		btnSignup = new JButton("Sign up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jobseekersignupshow();
			}
		});
		btnSignup.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnSignup.setBounds(255, 201, 117, 29);
		contentPane.add(btnSignup);
	}
	
	public void jobseekersignupshow() {
		JobSeekerSignupGUI jobseekersignupgui = new JobSeekerSignupGUI();
		jobseekersignupgui.show();
	}

	public void openJobSeekerMainGUI(String username) {
	     JobSeekerMainGUI jobseekerMainGUI = new JobSeekerMainGUI(username);
	     jobseekerMainGUI.show();
	}
	public boolean areTextFieldsEmpty(){
		return username.isEmpty() || password.isEmpty();
	}
	
	public void jobSeekerLogin() {
		if (areTextFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        conn = new DatabaseConnect();
        isValidLogin = conn.checkJobSeekerLogin(username, password);
        
        if (isValidLogin) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            openJobSeekerMainGUI(username);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
	}

}
