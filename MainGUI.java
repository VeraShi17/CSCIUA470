import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitle;
	private JButton btnJobseeker;
	private JLabel lblIM;
	private JButton btnRecruiter;


	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitle = new JLabel("Welcome to CareerLinker!");
		lblTitle.setBounds(115, 20, 218, 26);
		lblTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		contentPane.add(lblTitle);
		
		btnJobseeker = new JButton("Job Seeker");
		btnJobseeker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open jobseekerGUI
				jobseekershow();
			}
		});
		btnJobseeker.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnJobseeker.setBounds(162, 108, 117, 29);
		contentPane.add(btnJobseeker);
		
		lblIM = new JLabel("I'm ...");
		lblIM.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblIM.setBounds(37, 75, 61, 16);
		contentPane.add(lblIM);
		
		btnRecruiter = new JButton("Recruiter");
		btnRecruiter.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnRecruiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open recruiterGUI
				recruitershow();
			}
		});
		btnRecruiter.setBounds(162, 174, 117, 29);
		contentPane.add(btnRecruiter);
	}
	
	public void recruitershow() {
		RecruiterGUI recruitergui = new RecruiterGUI();
		recruitergui.show();
	}
	public void jobseekershow() {
		JobseekerGUI jobseekergui = new JobseekerGUI();
		jobseekergui.show();
	}
}
