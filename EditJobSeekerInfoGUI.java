import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditJobSeekerInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtSkills;
	private JTextArea txtEducation;
	private JTextArea txtWork;
	private JButton btnSave;
	private JLabel lblName;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JLabel lblSkills;
	private JLabel lblEducation;
	private JLabel lblWork;
	private JButton btnCancel;
	private String Name;
	private String Phone;
	private String Email;
	private String Skills;
	private String Education;
	private String Work;
	private DatabaseConnect conn;

	
	public EditJobSeekerInfoGUI(String username, String name, String phone, String email, String skills, String education, String work) {
		setBounds(200, 200, 550, 500);
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
		
		lblSkills = new JLabel("Skills");
		lblSkills.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblSkills.setBounds(61, 182, 122, 16);
		contentPane.add(lblSkills);
		
		lblEducation = new JLabel("Education Experience");
		lblEducation.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEducation.setBounds(61, 230, 157, 16);
		contentPane.add(lblEducation);
		
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
		
		txtSkills = new JTextField(skills);
		txtSkills.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtSkills.setBounds(250, 178, 252, 26);
		contentPane.add(txtSkills);
		txtSkills.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveJobSeekerInfo(username);
			}
		});
		btnSave.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSave.setBounds(61, 379, 117, 29);
		contentPane.add(btnSave);
		
		txtEducation = new JTextArea(education);
		txtEducation.setBounds(257, 230, 245, 88);
		contentPane.add(txtEducation);
		
		lblWork = new JLabel("Work Experience");
		lblWork.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblWork.setBounds(61, 341, 157, 16);
		contentPane.add(lblWork);
		
		txtWork = new JTextArea(work);
		txtWork.setBounds(257, 342, 245, 88);
		contentPane.add(txtWork);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(61, 423, 117, 29);
		contentPane.add(btnCancel);
	}
	
	public boolean areTextFieldsEmpty() {
        return txtName.getText().trim().isEmpty() ||
               txtPhone.getText().trim().isEmpty() ||
               txtEmail.getText().trim().isEmpty() ||
               txtSkills.getText().trim().isEmpty() ||
               txtEducation.getText().trim().isEmpty()||
               txtWork.getText().trim().isEmpty();
    }
	
	public void saveJobSeekerInfo(String username) {
		if (areTextFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
        	Name = txtName.getText().trim();
            Phone = txtPhone.getText().trim();
            Email = txtEmail.getText().trim();
            Skills = txtSkills.getText().trim();
            Education = txtEducation.getText().trim();
            Work = txtWork.getText().trim();
            conn = new DatabaseConnect();
            conn.updateJobSeekerInfo(Name, Phone, Email, Skills, Education, Work, username);
        } 
        
    }
}
