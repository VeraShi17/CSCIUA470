import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditJobGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextArea txtDescription;
	private JTextField txtLocation;
	private JTextArea txtEducation;
	private JButton btnSave;
	private JButton btnCancel;
	private JLabel lblTitle;
	private JLabel lblDescription;
	private JLabel lblEducation;
	private JLabel lblLocation;
	private DatabaseConnect conn;
	private String new_jobTitle;
	private String new_jobDescription;
	private String new_location;
	private String new_educationRequirements;

	
	public EditJobGUI(String username, String jobTitle, String jobDescription, String location, String educationRequirements) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitle = new JLabel("Job Title");
		lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblTitle.setBounds(23, 18, 62, 16);
		contentPane.add(lblTitle);
		
		lblDescription = new JLabel("Job Description");
		lblDescription.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDescription.setBounds(23, 61, 105, 16);
		contentPane.add(lblDescription);
		
		lblEducation = new JLabel("Education Requirements");
		lblEducation.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEducation.setBounds(23, 278, 170, 16);
		contentPane.add(lblEducation);
		
		lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblLocation.setBounds(23, 235, 61, 16);
		contentPane.add(lblLocation);
		
		txtTitle = new JTextField(jobTitle);
		txtTitle.setBounds(204, 14, 228, 26);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		txtDescription = new JTextArea(jobDescription);
		txtDescription.setBounds(204, 61, 228, 158);
		contentPane.add(txtDescription);
		txtDescription.setColumns(10);
		
		txtLocation = new JTextField(location);
		txtLocation.setBounds(204, 231, 228, 26);
		contentPane.add(txtLocation);
		txtLocation.setColumns(10);
		
		txtEducation = new JTextArea(educationRequirements);
		txtEducation.setBounds(204, 274, 228, 50);
		contentPane.add(txtEducation);
		txtEducation.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveJobInfo(username, jobTitle);
			}
		});
		btnSave.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSave.setBounds(48, 337, 117, 29);
		contentPane.add(btnSave);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnCancel.setBounds(258, 336, 117, 29);
		contentPane.add(btnCancel);
	}
	
	public boolean isFieldsNotEmpty() {
	    return !txtTitle.getText().isEmpty() &&
	           !txtDescription.getText().isEmpty() &&
	           !txtLocation.getText().isEmpty() &&
	           !txtEducation.getText().isEmpty();
	}
	
	public void saveJobInfo(String username, String jobTitle) {
		// Check if all text fields are non-empty
        if (isFieldsNotEmpty()) {
            // Get values from text fields
            new_jobTitle = txtTitle.getText();
            new_jobDescription = txtDescription.getText();
            new_location = txtLocation.getText();
            new_educationRequirements = txtEducation.getText();
            // Insert values into the job table
            conn = new DatabaseConnect();
            conn.updateJob(new_jobTitle, new_jobDescription, new_location, new_educationRequirements, username, jobTitle);
            
        }
	}

}
