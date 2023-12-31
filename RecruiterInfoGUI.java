import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecruiterInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtCompanyName;
	private JTextArea txtCompanyDescription;
	private JLabel lblInstruction;
	private JLabel lblName;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JLabel lblCompanyName;
	private JLabel lblCompanyDescription;
	private JButton btnNext;
	private DatabaseConnect conn;
	private String name;
	private String phone;
	private String email;
	private String companyName;
	private String companyDescription;

	
	public RecruiterInfoGUI(String username) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 400);
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
		
		lblCompanyName = new JLabel("Company Name");
		lblCompanyName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCompanyName.setBounds(61, 182, 122, 16);
		contentPane.add(lblCompanyName);
		
		lblCompanyDescription = new JLabel("Company Description");
		lblCompanyDescription.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCompanyDescription.setBounds(61, 230, 157, 16);
		contentPane.add(lblCompanyDescription);
		
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
		
		txtCompanyName = new JTextField();
		txtCompanyName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txtCompanyName.setBounds(250, 178, 252, 26);
		contentPane.add(txtCompanyName);
		txtCompanyName.setColumns(10);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				provideRecruiterInfo(username);
			}
		});
		btnNext.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnNext.setBounds(61, 297, 117, 29);
		contentPane.add(btnNext);
		
		txtCompanyDescription = new JTextArea();
		txtCompanyDescription.setBounds(257, 230, 245, 108);
		contentPane.add(txtCompanyDescription);
	}
	
	public boolean areTextFieldsEmpty() {
        return txtName.getText().trim().isEmpty() ||
               txtPhone.getText().trim().isEmpty() ||
               txtEmail.getText().trim().isEmpty() ||
               txtCompanyName.getText().trim().isEmpty() ||
               txtCompanyDescription.getText().trim().isEmpty();
    }	
	
	public void provideRecruiterInfo(String username) {
		if (areTextFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
        	conn = new DatabaseConnect();
            name = txtName.getText().trim();
            phone = txtPhone.getText().trim();
            email = txtEmail.getText().trim();
            companyName = txtCompanyName.getText().trim();
            companyDescription = txtCompanyDescription.getText().trim();
            conn.saveRecruiterInformation(username, name, phone, email, companyName, companyDescription);
        }
		dispose();
	}
}
