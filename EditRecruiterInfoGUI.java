import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditRecruiterInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtCompanyName;
	private JTextArea txtCompanyDescription;
	private JButton btnSave;
	private JButton btnCancel;
	private JLabel lblName;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JLabel lblCompanyName;
	private JLabel lblCompanyDescription;

	
	public EditRecruiterInfoGUI(String username, String name, String phone, String email, String companyName, String companyDescription) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblName.setBounds(61, 38, 44, 16);
		contentPane.add(lblName);
		
		lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPhone.setBounds(61, 86, 44, 16);
		contentPane.add(lblPhone);
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEmail.setBounds(61, 134, 44, 16);
		contentPane.add(lblEmail);
		
		lblCompanyName = new JLabel("Company Name");
		lblCompanyName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCompanyName.setBounds(61, 182, 122, 16);
		contentPane.add(lblCompanyName);
		
		lblCompanyDescription = new JLabel("Company Description");
		lblCompanyDescription.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCompanyDescription.setBounds(61, 230, 157, 16);
		contentPane.add(lblCompanyDescription);
		
		txtName = new JTextField(name);
		txtName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtName.setBounds(250, 34, 252, 26);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtPhone = new JTextField(phone);
		txtPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtPhone.setBounds(250, 82, 252, 26);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtEmail = new JTextField(email);
		txtEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtEmail.setBounds(250, 130, 252, 26);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtCompanyName = new JTextField(companyName);
		txtCompanyName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtCompanyName.setBounds(250, 178, 252, 26);
		contentPane.add(txtCompanyName);
		txtCompanyName.setColumns(10);
		
		txtCompanyDescription = new JTextArea(companyDescription);
		txtCompanyDescription.setBounds(257, 230, 245, 108);
		contentPane.add(txtCompanyDescription);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnCancel.setBounds(66, 325, 117, 29);
		contentPane.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check if all text fields are non-empty
		        if (isFieldsNotEmpty()) {
		            // Get values from text fields
		            String name = txtName.getText();
		            String phone = txtPhone.getText();
		            String email = txtEmail.getText();
		            String company_name = txtCompanyName.getText();
		            String company_description = txtCompanyDescription.getText();

		            // Insert values into the job table
		            try {
		                Connection connection = DatabaseConnect.connect();
		                String updateRecruiterInfoQuery = "UPDATE recruiter SET name=?, phone=?, email=?, company_name=?, company_description=? WHERE username=?";
		                try (PreparedStatement preparedStatement = connection.prepareStatement(updateRecruiterInfoQuery)) {
		                	preparedStatement.setString(1, name);
		                    preparedStatement.setString(2, phone);
		                    preparedStatement.setString(3, email);
		                    preparedStatement.setString(4, company_name);
		                    preparedStatement.setString(5, company_description);
		                    preparedStatement.setString(6, username);
		                    preparedStatement.executeUpdate();

		                    int rowsAffected = preparedStatement.executeUpdate();

		                    if (rowsAffected > 0) {
		                        JOptionPane.showMessageDialog(null, "Recruiter Info updated successfully!");
		                    } else {
		                        JOptionPane.showMessageDialog(null, "Recruiter Info not found or no changes made.", "Error", JOptionPane.ERROR_MESSAGE);
		                    }
		                }
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error updating recruiter information.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        }
			}
		});
		btnSave.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSave.setBounds(66, 270, 117, 29);
		contentPane.add(btnSave);
	}
	
	private boolean isFieldsNotEmpty() {
	    return !txtName.getText().isEmpty() &&
	           !txtPhone.getText().isEmpty() &&
	           !txtEmail.getText().isEmpty() &&
	           !txtCompanyName.getText().isEmpty() &&
	           !txtCompanyDescription.getText().isEmpty();
	}

}
