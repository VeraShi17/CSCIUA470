import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RecruiterMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public RecruiterMainGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome, " + username +" !");
		lblWelcome.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblWelcome.setBounds(21, 6, 397, 23);
		contentPane.add(lblWelcome);
		
		JButton btnPost = new JButton("Post a New Job");
		btnPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postanewjob_show(username);
			}
		});
		btnPost.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnPost.setBounds(137, 34, 152, 29);
		contentPane.add(btnPost);
		
		JButton btnManage = new JButton("Manage Posted Jobs");
		btnManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managepostedjobs_show(username);
			}
		});
		btnManage.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnManage.setBounds(125, 94, 178, 29);
		contentPane.add(btnManage);
		
		JButton btnReview = new JButton("Review Job Applications");
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reviewapplications_show(username);
			}
		});
		btnReview.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnReview.setBounds(112, 154, 214, 29);
		contentPane.add(btnReview);
		
		JButton btnNewButton = new JButton("Update Profile");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recruiterinfoshow(username);
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnNewButton.setBounds(140, 214, 152, 29);
		contentPane.add(btnNewButton);
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
