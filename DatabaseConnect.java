import java.sql.*;
import javax.swing.JOptionPane;

public class DatabaseConnect {

	private String url = "jdbc:mysql://localhost:3306/CareerLinker";
    private String user = "project_admin";
    private String password = "oopfinalproject";
    private Connection connection;
    private Statement statement;
    private String query;
//    private String insertUserQuery;
//    private String updateJobSeekerInfoQuery;
//    private String updateRecruiterInfoQuery;
//    private String selectRecruiterQuery;
//    private String selectJobSeekerQuery;
//    private String insertJobQuery;
//    private String selectJobsQuery;
//    private String selectJobQuery;
//    private String closeJobQuery;
//    private String reopenJobQuery;
//    private String updateJobQuery;
//    private String selectApplicantsQuery;
//    private String updateApplicationQuery;
//    private String selectJsProfileQuery;
    
    
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void close(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String querySQL) throws SQLException {
        connection = connect();
        statement = connection.createStatement();
        return statement.executeQuery(querySQL);
    }

    public void executeUpdate(String updateSQL) throws SQLException {
        connection = connect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateSQL);
        } finally {
            close(connection);
        }
    }
    
    public boolean checkRecruiterUsernameExists(String username) {
        try (Connection connection = connect()) {
            query = "SELECT username FROM recruiter WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    public boolean checkJobSeekerUsernameExists(String username) {
        try (Connection connection = connect()) {
            query = "SELECT username FROM jobseeker WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    // RecruiterGUI
    public boolean checkRecruiterLogin(String username, String password) {
	    try (Connection connection = connect()) {
	        query = "SELECT * FROM recruiter WHERE username=? AND password=?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            return resultSet.next();
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error checking recruiter login.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false; 
	    }
	}
    
    // JobSeekerGUI
	public boolean checkJobSeekerLogin(String username, String password) {
	    try (Connection connection = connect()) {
	        query = "SELECT * FROM jobseeker WHERE username=? AND password=?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            return resultSet.next();
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        return false; 
	    }
	}
	
	// JobSeekerSignupGUI
	public void registerUsernameAndPasswordJobSeeker(String username, String password) {
        try (Connection connection = connect()) {
        	query = "INSERT INTO jobseeker (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Username and password registered successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error registering username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	// RecruiterSignupGUI
	public void registerUsernameAndPassword(String username, String password) {
        try (Connection connection = connect()) {
        	query = "INSERT INTO recruiter (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Username and password registered successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error registering username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	// JobSeekerInfoGUI
	public void saveJobSeekerInformation(String username, String name, String phone, String email, String skills, String education, String work) {
        try (Connection connection = connect()) {
        	query = "UPDATE jobseeker SET name=?, phone=?, email=?, skills=?, education=?, work_experience=? WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
	
	// RecruiterInfoGUI
	public void saveRecruiterInformation(String username, String name, String phone, String email, String companyName, String companyDescription) {
        try (Connection connection = connect()) {
        	query = "UPDATE recruiter SET name=?, phone=?, email=?, company_name=?, company_description=? WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, companyName);
                preparedStatement.setString(5, companyDescription);
                preparedStatement.setString(6, username);
                preparedStatement.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Recruiter information saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving recruiter information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	// RecruiterMainGUI
	public ResultSet retrieveRecruiterInfo(String username) {
	    try {
	        connection = connect();
	        query = "SELECT * FROM recruiter WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        return preparedStatement.executeQuery();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving recruiter information.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	// JobSeekerMainGUI
	public ResultSet retrieveJobSeekerInfo(String username) {
	    try {
	        connection = connect();
	        query = "SELECT * FROM jobseeker WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        return preparedStatement.executeQuery();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job seeker information.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	// EditRecruiterInfoGUI
	public void updateRecruiterInfo(String name, String phone, String email, String company_name, String company_description, String username) {
		try {
            connection = connect();
            query = "UPDATE recruiter SET name=?, phone=?, email=?, company_name=?, company_description=? WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
	
	// EditJobSeekerInfoGUI
	public void updateJobSeekerInfo(String name, String phone, String email, String skills, String education, String work, String username) {
		try (Connection connection = connect()) {
			query = "UPDATE jobseeker SET name=?, phone=?, email=?, skills=?, education=?, work_experience=? WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
	
	// PostANewJobGUI
	public void addanewjob(String username, String jobTitle, String jobDescription, String location, String educationRequirements) {
		try {
            connection = connect();
            query = "INSERT INTO job (username, job_title, job_description, location, education_requirements, status) VALUES (?, ?, ?, ?, ?, 'open')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, jobTitle);
                preparedStatement.setString(3, jobDescription);
                preparedStatement.setString(4, location);
                preparedStatement.setString(5, educationRequirements);
                preparedStatement.executeUpdate();
            }
            // Display success message
            JOptionPane.showMessageDialog(null, "Posted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error posting job.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	// ManagePostedJobsGUI, ReviewApplicationsGUI
	public ResultSet retrieveJobInfo(String username) {
	    try {
	        connection = connect();
	        query = "SELECT job_title, status FROM job WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        return preparedStatement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job information.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}

	// ManagedPostedJobsGUI
	public ResultSet editJob(String username, String jobTitle) {
	    try {
	        connection = connect();
	        query = "SELECT * FROM job WHERE username = ? AND job_title = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, jobTitle);
	        return preparedStatement.executeQuery();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job information.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	public void closeJob(String username, String jobTitle) {
    	try (Connection connection = connect()) {
    		query = "UPDATE job SET status = 'closed' WHERE username = ? AND job_title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, jobTitle);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Job closed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Job not found or already closed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error closing job.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	public void reopenJob(String username, String jobTitle) {
    	try (Connection connection = connect()) {
    		query = "UPDATE job SET status = 'open' WHERE username = ? AND job_title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, jobTitle);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Job reopened successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Job not found or already open.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reopening job.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	// EditJobGUI
	public void updateJob(String new_jobTitle, String new_jobDescription, String new_location, String new_educationRequirements, String username, String jobTitle) {
		try {
            connection = connect();
            query = "UPDATE job SET job_title = ?, job_description = ?, location = ?, education_requirements = ? WHERE username = ? AND job_title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, new_jobTitle);
                preparedStatement.setString(2, new_jobDescription);
                preparedStatement.setString(3, new_location);
                preparedStatement.setString(4, new_educationRequirements);
                preparedStatement.setString(5, username);
                preparedStatement.setString(6, jobTitle);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Job updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Job not found or no changes made.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating job information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	// ViewApplicantsGUI
	public ResultSet retrieveApplicantInfo(String recruiterUsername, String jobTitle) {
	    try {
	        connection = connect();
	        query = "SELECT jobseeker_username, application_status FROM applicant WHERE recruiter_username = ? AND job_title = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, recruiterUsername);
	        preparedStatement.setString(2, jobTitle);
	        return preparedStatement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving applicant information.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}

	
	public void acceptApplication(String recruiterUsername, String jobTitle, String jobSeekerUsername) {
    	try (Connection connection = connect()) {
            // Update the application status to "accept" in the applicant table
    		query = "UPDATE applicant SET application_status = 'accept' " +
                    "WHERE recruiter_username = ? AND job_title = ? AND jobseeker_username = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, recruiterUsername);
                preparedStatement.setString(2, jobTitle);
                preparedStatement.setString(3, jobSeekerUsername);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Application status updated successfully
                    System.out.println("Application accepted successfully!");
                } else {
                    // No rows were affected, application not found or not updated
                    System.out.println("Application not found or not updated.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void rejectApplication(String recruiterUsername, String jobTitle, String jobSeekerUsername) {
    	try (Connection connection = connect()) {
            // Update the application status to "reject" in the applicant table
    		query = "UPDATE applicant SET application_status = 'reject' " +
                    "WHERE recruiter_username = ? AND job_title = ? AND jobseeker_username = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, recruiterUsername);
                preparedStatement.setString(2, jobTitle);
                preparedStatement.setString(3, jobSeekerUsername);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Application status updated successfully
                    System.out.println("Application rejected successfully!");
                } else {
                    // No rows were affected, application not found or not updated
                    System.out.println("Application not found or not updated.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// ViewJSProfileGUI
	public ResultSet retrieveJSProfile(String jsUsername) {
	    try {
	        connection = connect();
	        query = "SELECT * FROM jobseeker WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, jsUsername);
	        return preparedStatement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job seeker profile.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	// ViewJobsGUI
	public ResultSet retrieveJobs() {
	    try {
	        connection = connect();
	        query = "SELECT job_title, username FROM job WHERE status = 'open'";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        return preparedStatement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving available jobs.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	// ViewJobsGUI, SearchJobsGUI
	public void applyJob(String jobseeker_username, String recruiter_username, String jobTitle) {
		try (Connection connection = connect()) {
			query = "INSERT INTO applicant (recruiter_username, job_title, jobseeker_username, application_status) VALUES (?, ?, ?, 'Pending')";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, recruiter_username);
	        preparedStatement.setString(2, jobTitle);
	        preparedStatement.setString(3, jobseeker_username);

	        preparedStatement.executeUpdate();
	        
	        JOptionPane.showMessageDialog(null, "Application submitted successfully!");
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error submitting application.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	// ViewJobDetailsGUI
	public ResultSet retrieveJobDetails(String jobTitle, String recruiterUsername) {
	    try {
	        connection = connect();
	        query = "SELECT * FROM job WHERE job_title = ? AND username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, jobTitle);
	        preparedStatement.setString(2, recruiterUsername);
	        return preparedStatement.executeQuery();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving job details.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	public ResultSet retrieveRecruiterDetails(String recruiterUsername) {
	    try {
	        connection = connect();
	        query = "SELECT * FROM recruiter WHERE username = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, recruiterUsername);
	        return preparedStatement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving recruiter details.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	// SearchJobsGUI
	public ResultSet retrieveSearchedJobs(String searchTerm) {
	    try {
	        connection = connect();
	        query = "SELECT job_title, username FROM job WHERE status = 'open' AND job_title LIKE ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, "%" + searchTerm + "%");
	        return preparedStatement.executeQuery();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving searched jobs.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}
	
	// AppliedJobsGUI
	public ResultSet retrieveAppliedJobs(String jobseekerUsername) {
	    try {
	        connection = connect();
	        query = "SELECT a.job_title, j.status, a.application_status " +
	                                "FROM applicant a " +
	                                "JOIN job j ON a.recruiter_username = j.username AND a.job_title = j.job_title " +
	                                "WHERE a.jobseeker_username = ?";

	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, jobseekerUsername);
	        return preparedStatement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error retrieving applied jobs.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
	}

}
