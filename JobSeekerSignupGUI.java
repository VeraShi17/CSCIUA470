import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JobSeekerSignupGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel lblSignup;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnSign;
	private JButton btnCancel;
	private String username;
	private String password;
	private DatabaseConnect conn;
	private boolean usernameExists;
	
	public JobSeekerSignupGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblSignup = new JLabel("Job Seeker Sign Up");
		lblSignup.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		lblSignup.setBounds(134, 6, 192, 26);
		contentPane.add(lblSignup);
		
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
		
		btnSign = new JButton("Sign up");
		btnSign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// verify whether 1. username exists 2. txt empty
				// 1 username exists
		        username = txtUsername.getText().trim();
		        password = txtPassword.getText();
		        jobSeekerSignup();
		    }
		});
		btnSign.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnSign.setBounds(80, 201, 117, 29);
		contentPane.add(btnSign);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnCancel.setBounds(255, 201, 117, 29);
		contentPane.add(btnCancel);
	}
    
    public void jobseekerinfoshow(String username) {
		JobSeekerInfoGUI jobseekerinfogui = new JobSeekerInfoGUI(username);
		jobseekerinfogui.show();
	}
    public void jobSeekerSignup() {
    	conn = new DatabaseConnect();
    	usernameExists =  conn.checkRecruiterUsernameExists(username) || conn.checkJobSeekerUsernameExists(username);
        
        // warning message
        if (usernameExists) {
            JOptionPane.showMessageDialog(null, "Username already exists.", "Warning", JOptionPane.WARNING_MESSAGE);   
        } else if (areTextFieldsEmpty()){
        	JOptionPane.showMessageDialog(null, " Username and Password are required.", "Warning", JOptionPane.WARNING_MESSAGE);
        }else {
            // pass the verification, add information into table recruiter	      
        	conn.registerUsernameAndPasswordJobSeeker(username, password);
        	jobseekerinfoshow(username);
        	dispose();
        }
    }
    public boolean areTextFieldsEmpty(){
		return username.isEmpty() || password.isEmpty();
	}

}
