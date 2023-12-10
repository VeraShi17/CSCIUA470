import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
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
	private JLabel lblInstruction;
	private JLabel lblName;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JLabel lblSkills;
	private JLabel lblEducation;
	private JButton btnNext;
	private JLabel lblWork;
	private String name;
	private String phone;
	private String email;
	private String skills;
	private String education;
	private String work;
	private DatabaseConnect conn;
	
	public JobSeekerInfoGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblInstruction = new JLabel("Please enter the following information. You could also update them later.");
		lblInstruction.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblInstruction.setBounds(6, 6, 519, 16);
		contentPane.add(lblInstruction);
		
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
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				provideInfo(username);
			}
		});
		btnNext.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnNext.setBounds(61, 424, 117, 29);
		contentPane.add(btnNext);
		
		txtEducation = new JTextArea();
		txtEducation.setBounds(257, 230, 245, 88);
		contentPane.add(txtEducation);
		
		lblWork = new JLabel("Work Experience");
		lblWork.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblWork.setBounds(61, 341, 157, 16);
		contentPane.add(lblWork);
		
		txtWork = new JTextArea();
		txtWork.setBounds(257, 342, 245, 88);
		contentPane.add(txtWork);
	}
	
	public boolean areTextFieldsEmpty() {
        return txtName.getText().trim().isEmpty() ||
               txtPhone.getText().trim().isEmpty() ||
               txtEmail.getText().trim().isEmpty() ||
               txtSkills.getText().trim().isEmpty() ||
               txtEducation.getText().trim().isEmpty()||
               txtWork.getText().trim().isEmpty();
    }
	public void provideInfo(String username) {
		if (areTextFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
        	conn = new DatabaseConnect();
        	name = txtName.getText().trim();
            phone = txtPhone.getText().trim();
            email = txtEmail.getText().trim();
            skills = txtSkills.getText().trim();
            education = txtEducation.getText().trim();
            work = txtWork.getText().trim();
            conn.saveJobSeekerInformation(username, name, phone, email, skills, education, work);
        }
		dispose();
	}
	
}
