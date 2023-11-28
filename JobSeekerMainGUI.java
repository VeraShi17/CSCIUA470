import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JobSeekerMainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblWelcome;
	private JButton btnView;
	private JButton btnSearch;
	private JButton btnReview;
	private JButton btnUpdate;

	
	public JobSeekerMainGUI(String username) {
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
		
		btnView = new JButton("View Available Jobs");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewjobs_show(username);
			}
		});
		btnView.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnView.setBounds(125, 41, 178, 29);
		contentPane.add(btnView);
		
		btnSearch = new JButton("Search Jobs");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchjobs_show(username);
			}
		});
		btnSearch.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnSearch.setBounds(125, 94, 178, 29);
		contentPane.add(btnSearch);
		
		btnReview = new JButton("View Applied Jobs");
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewappliedjobs_show(username);
			}
		});
		btnReview.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnReview.setBounds(112, 154, 214, 29);
		contentPane.add(btnReview);
		
		btnUpdate = new JButton("Update Profile");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jobseekerinfoshow(username);
			}
		});
		btnUpdate.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnUpdate.setBounds(140, 214, 152, 29);
		contentPane.add(btnUpdate);
	}
	
	public void viewjobs_show(String username) {
		ViewJobsGUI viewjobgui = new ViewJobsGUI(username);
		viewjobgui.show();
	}
	
	public void searchjobs_show(String username) {
		SearchJobsGUI searchjobsgui = new SearchJobsGUI(username);
		searchjobsgui.show();
	}
	
	public void jobseekerinfoshow(String username) {
		try (Connection connection = DatabaseConnect.connect()) {
	        String selectJobSeekerQuery = "SELECT * FROM jobseeker WHERE username = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectJobSeekerQuery)) {
	            preparedStatement.setString(1, username);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                String name = resultSet.getString("name");
	                String phone = resultSet.getString("phone");
	                String email = resultSet.getString("email");
	                String skills = resultSet.getString("skills");
	                String education = resultSet.getString("education");
	                String work = resultSet.getString("work_experience");
	                EditJobSeekerInfoGUI editjsinfogui = new EditJobSeekerInfoGUI(username, name, phone, email, skills, education, work);
	        		editjsinfogui.show();
	            } else {
	                JOptionPane.showMessageDialog(null, "Job Seeker info not found.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job seeker information.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void viewappliedjobs_show(String username) {
		AppliedJobsGUI appliedjobsgui = new AppliedJobsGUI(username);
		appliedjobsgui.show();
	}

}
