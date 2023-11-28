import java.sql.*;

public class DatabaseConnect {
	private static final String url = "jdbc:mysql://localhost:3306/CareerLinker";
    private static final String user = "admin";
    private static final String password = "oopfinalproject";
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ResultSet executeQuery(String querySQL) throws SQLException {
        Connection connection = connect();
        Statement statement = connection.createStatement();
        return statement.executeQuery(querySQL);
    }

    public static void executeUpdate(String updateSQL) throws SQLException {
        Connection connection = connect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateSQL);
        } finally {
            close(connection);
        }
    }
    
    public static void createTables() {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
        	// check whether recruiter table exists
            ResultSet resultSet = connection.getMetaData().getTables(null, null, "recruiter", null);
            if (!resultSet.next()) {
                // create recruiter table
                String createRecruiterTable = "CREATE TABLE IF NOT EXISTS recruiter (" +
                        "username VARCHAR(50) PRIMARY KEY," +
                        "password VARCHAR(255)," +
                        "name VARCHAR(100)," +
                        "phone VARCHAR(30)," +
                        "email VARCHAR(100)," +
                        "company_name VARCHAR(100)," +
                        "company_description VARCHAR(400)" +
                        ");";
                statement.executeUpdate(createRecruiterTable);
            }

            // check whether job seeker table exists
            resultSet = connection.getMetaData().getTables(null, null, "jobseeker", null);
            if (!resultSet.next()) {
                // create job seeker table
                String createJobSeekerTable = "CREATE TABLE IF NOT EXISTS jobseeker (" +
                		"username VARCHAR(50) PRIMARY KEY," +
                		"password VARCHAR(255)," +
                		"name VARCHAR(100)," +
                		"phone VARCHAR(30)," +
                        "email VARCHAR(100)," +
                        "skills VARCHAR(255)," +
                        "education VARCHAR(255)," +
                        "work_experience VARCHAR(255)" +
                        ");";
                statement.executeUpdate(createJobSeekerTable);
            }

         // check whether job table exists
            resultSet = connection.getMetaData().getTables(null, null, "job", null);
            if (!resultSet.next()) {
                // create job table
                String createJobTable = "CREATE TABLE IF NOT EXISTS job (" +
                		"username VARCHAR(50)," +
                		"job_title VARCHAR(255)," +
                		"job_description VARCHAR(400)," +
                		"location VARCHAR(255)," +
                        "education_requirements VARCHAR(255)," +
                		"status VARCHAR(10)," +
                		"PRIMARY KEY (username, job_title)" +
                        ");";
                statement.executeUpdate(createJobTable);
            }
            
         // check whether applicant table exists
            resultSet = connection.getMetaData().getTables(null, null, "applicant", null);
            if (!resultSet.next()) {
                // create job table
                String createJobTable = "CREATE TABLE IF NOT EXISTS applicant (" +
                		"recruiter_username VARCHAR(50)," +
                		"job_title VARCHAR(255)," +
                		"jobseeker_username VARCHAR(50)," +
                		"application_status VARCHAR(10)," +
                		"PRIMARY KEY (recruiter_username, job_title, jobseeker_username)" +
                        ");";
                statement.executeUpdate(createJobTable);
            }
            
            System.out.println("Tables created or already exist.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean checkRecruiterUsernameExists(String username) {
        try (Connection connection = connect()) {
            String query = "SELECT username FROM recruiter WHERE username = ?";
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
    
    public static boolean checkJobSeekerUsernameExists(String username) {
        try (Connection connection = connect()) {
            String query = "SELECT username FROM jobseeker WHERE username = ?";
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
}
