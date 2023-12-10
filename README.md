# CSCIUA470
OOP Final Project - CareerLinker
CareerLinker is envisioned as a comprehensive Job Recruitment System aimed at
simplifying the job search and recruitment process. In this scenario, job seekers and recruiters
can efficiently connect through a user-friendly platform, streamlining the hiring process for both
parties. 

To run the file, just run `Main.java`

## Requirements
Using Java Database Connectivity (JDBC). 
Please first download the MySQL Connector/J JAR file and import MySQL Connector Into Eclipse IDE. 
Then check the [requirements.sql](https://github.com/VeraShi17/CSCIUA470/blob/main/requirements.sql) file and run it in your MySQL. This file creates the database and tables.

## Functionalities
1. User Authentication:
   1. Job seekers and recruiters can sign up and log in securely to access their respective accounts.
2. Job Seeker Functionalities:
   1. Search for jobs based on keywords.
   2. Update personal information, skills, and work experience in the user’s profile.
   3. Apply for jobs with the option to track application status.
   4. View a comprehensive list of all available jobs.
3. Recruiter Functionalities:
   1. Post job openings with details like title, location, and job requirements.
   2. Manage posted jobs, including editing, closing, or reopening positions.
   3. Review and process job applications, including viewing applicants’ profiles, and accepting or rejecting applications.
   4. Update personal information and company information in the user’s profile.
4. User Database Integration (MySQL):
   1. Utilize MySQL for data storage and retrieval.
   2. Create tables for recruiters, job seekers, jobs, and applicants.
   3. Create a DatabaseConnect class to communicate between the GUI interface and the CareerLinker database.

## Future Works
Due to time constraints in the project, there are several areas for further optimization in the system:

1. Jobseeker's Applied Jobs:
   1. Add a "Cancel Application" feature when job seekers are reviewing their applied jobs.
2. Enhanced Communication:
   1. Integrate a messaging feature for job seekers viewing job details and recruiters viewing job seeker profiles.
3. Real-time Updates on GUI:
   1. Implement real-time updates for job status and application status on the graphical user interface (GUI).
4. Advanced Job Search for Jobseekers:
   1. Enhance the job search functionality for job seekers by adding options such as searching based on skills.
5. Additional Profile and Job Fields:
   1. Expand the fields that can be filled in jobseeker and recruiter profiles, as well as job listings. For instance, include fields for salary range, job application deadline, and job type.
6. Advanced Filtering for Recruiters:
   1. Provide recruiters with advanced filtering options when reviewing job applications, allowing them to filter applicants based on specific skills and other criteria.

