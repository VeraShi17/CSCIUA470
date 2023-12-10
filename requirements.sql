CREATE DATABASE CareerLinker;
CREATE USER 'project_admin'@'localhost' IDENTIFIED BY 'oopfinalproject';
GRANT ALL PRIVILEGES ON CareerLinker.* TO 'project_admin'@'localhost';
FLUSH PRIVILEGES;

USE CareerLinker;

CREATE TABLE IF NOT EXISTS recruiter (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(100),
    company_name VARCHAR(100),
    company_description VARCHAR(400)
);
CREATE TABLE IF NOT EXISTS jobseeker (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(100),
    skills VARCHAR(255),
    education VARCHAR(255),
    work_experience VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS job (
    username VARCHAR(50),
    job_title VARCHAR(255),
    job_description VARCHAR(400),
    location VARCHAR(255),
    education_requirements VARCHAR(255),
    status VARCHAR(10),
    PRIMARY KEY (username, job_title)
);
CREATE TABLE IF NOT EXISTS applicant (
    recruiter_username VARCHAR(50),
    job_title VARCHAR(255),
    jobseeker_username VARCHAR(50),
    application_status VARCHAR(10),
    PRIMARY KEY (recruiter_username, job_title, jobseeker_username)
);
