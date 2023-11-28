import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class RecruiterMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblWelcome;
	private JButton btnPost;
	private JButton btnManage;
	private JButton btnReview;
	private JButton btnUpdate;

	
	public RecruiterMainGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblWelcome = new JLabel("Welcome, " + username +" !");
		lblWelcome.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblWelcome.setBounds(21, 6, 397, 23);
		contentPane.add(lblWelcome);
		
		btnPost = new JButton("Post a New Job");
		btnPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postanewjob_show(username);
			}
		});
		btnPost.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnPost.setBounds(137, 34, 152, 29);
		contentPane.add(btnPost);
		
		btnManage = new JButton("Manage Posted Jobs");
		btnManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managepostedjobs_show(username);
			}
		});
		btnManage.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnManage.setBounds(125, 94, 178, 29);
		contentPane.add(btnManage);
		
		btnReview = new JButton("Review Job Applications");
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reviewapplications_show(username);
			}
		});
		btnReview.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnReview.setBounds(112, 154, 214, 29);
		contentPane.add(btnReview);
		
		btnUpdate = new JButton("Update Profile");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recruiterinfoshow(username);
			}
		});
		btnUpdate.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnUpdate.setBounds(140, 214, 152, 29);
		contentPane.add(btnUpdate);
	}
	
	public void postanewjob_show(String username) {
		PostANewJobGUI newjobgui = new PostANewJobGUI(username);
		newjobgui.show();
	}
	
	public void managepostedjobs_show(String username) {
		ManagePostedJobsGUI postedjobsgui = new ManagePostedJobsGUI(username);
		postedjobsgui.show();
	}
	
	public void recruiterinfoshow(String username) {
		try (Connection connection = DatabaseConnect.connect()) {
	        String selectRecruiterQuery = "SELECT * FROM recruiter WHERE username = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectRecruiterQuery)) {
	            preparedStatement.setString(1, username);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                String name = resultSet.getString("name");
	                String phone = resultSet.getString("phone");
	                String email = resultSet.getString("email");
	                String companyName = resultSet.getString("company_name");
	                String companyDescription = resultSet.getString("company_description");
	                EditRecruiterInfoGUI editrecruiterinfogui = new EditRecruiterInfoGUI(username, name, phone, email, companyName, companyDescription);
	        		editrecruiterinfogui.show();
	            } else {
	                JOptionPane.showMessageDialog(null, "Recruiter info not found.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving recruiter information.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
		
	}
	
	public void reviewapplications_show(String username) {
		ReviewApplicationsGUI reviewapplicationsgui = new ReviewApplicationsGUI(username);
		reviewapplicationsgui.show();
	}

}
