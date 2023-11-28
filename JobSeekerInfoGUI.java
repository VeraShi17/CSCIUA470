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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JobSeekerInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtSkills;
	private JTextArea txtEducation;
	private JTextArea txtWork;

	
	public JobSeekerInfoGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInstruction = new JLabel("Please enter the following information. You could also update them later.");
		lblInstruction.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblInstruction.setBounds(6, 6, 519, 16);
		contentPane.add(lblInstruction);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblName.setBounds(61, 38, 44, 16);
		contentPane.add(lblName);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPhone.setBounds(61, 86, 44, 16);
		contentPane.add(lblPhone);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEmail.setBounds(61, 134, 44, 16);
		contentPane.add(lblEmail);
		
		JLabel lblSkills = new JLabel("Skills");
		lblSkills.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblSkills.setBounds(61, 182, 122, 16);
		contentPane.add(lblSkills);
		
		JLabel lblEducation = new JLabel("Education Experience");
		lblEducation.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEducation.setBounds(61, 230, 157, 16);
		contentPane.add(lblEducation);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtName.setBounds(250, 34, 252, 26);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtPhone.setBounds(250, 82, 252, 26);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtEmail.setBounds(250, 130, 252, 26);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtSkills = new JTextField();
		txtSkills.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtSkills.setBounds(250, 178, 252, 26);
		contentPane.add(txtSkills);
		txtSkills.setColumns(10);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (areTextFieldsEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    saveJobSeekerInformation(username);
                }
				dispose();
			}
		});
		btnNext.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnNext.setBounds(61, 424, 117, 29);
		contentPane.add(btnNext);
		
		txtEducation = new JTextArea();
		txtEducation.setBounds(257, 230, 245, 88);
		contentPane.add(txtEducation);
		
		JLabel lblWork = new JLabel("Work Experience");
		lblWork.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblWork.setBounds(61, 341, 157, 16);
		contentPane.add(lblWork);
		
		txtWork = new JTextArea();
		txtWork.setBounds(257, 342, 245, 88);
		contentPane.add(txtWork);
	}
	
	private boolean areTextFieldsEmpty() {
        return txtName.getText().trim().isEmpty() ||
               txtPhone.getText().trim().isEmpty() ||
               txtEmail.getText().trim().isEmpty() ||
               txtSkills.getText().trim().isEmpty() ||
               txtEducation.getText().trim().isEmpty()||
               txtWork.getText().trim().isEmpty();
    }
	
	private void saveJobSeekerInformation(String username) {
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String skills = txtSkills.getText().trim();
        String education = txtEducation.getText().trim();
        String work = txtWork.getText().trim();
        try (Connection connection = DatabaseConnect.connect()) {
            String updateJobSeekerInfoQuery = "UPDATE jobseeker SET name=?, phone=?, email=?, skills=?, education=?, work_experience=? WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateJobSeekerInfoQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, skills);
                preparedStatement.setString(5, education);
                preparedStatement.setString(6, work);
                preparedStatement.setString(7, username);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Job Seeker information saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving job seeker information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
