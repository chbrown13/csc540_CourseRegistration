CREATE TABLE ADMINS (
	emp_id VARCHAR(9),
	ssn VARCHAR(9),
	first_name VARCHAR(20),
	last_name VARCHAR(20),
	user_name VARCHAR(20),
	password VARCHAR(20),
	birth_date VARCHAR(10),
	PRIMARY KEY (emp_id)
);

CREATE TABLE STUDENTS (
	student_id VARCHAR(9),
	first_name VARCHAR(20),
	last_name VARCHAR(20),
	user_name VARCHAR(20),
	password VARCHAR(20),
	email VARCHAR(20),
	birth_date VARCHAR(10),
	grade_level INTEGER,
	status VARCHAR(20),
	bill FLOAT,
	dept_id VARCHAR(3),
	enrolled_courses VARCHAR(100),
	courses VARCHAR(100),
	phone VARCHAR(20),
	address VARCHAR(100),
	PRIMARY KEY (student_id)
);

CREATE TABLE COURSES (
	course_id VARCHAR(10),
	title VARCHAR(20),
	credits INTEGER,
	dept_id VARCHAR(3),
	grade_level INTEGER,
	PRIMARY KEY (course_id)
);

CREATE TABLE SEMESTERS (
	course_id VARCHAR(10),
	semester VARCHAR(10),
	year INTEGER,
	instructors VARCHAR(100),
	class_size INTEGER,
	waitlist INTEGER,
	schedule VARCHAR(100),
	PRIMARY KEY (course_id,semester,year)
);

CREATE TABLE GRADING_SYSTEM (
	grade VARCHAR(2),
	points FLOAT,
	PRIMARY KEY (grade)
);

CREATE TABLE COSTS (
	grade_level integer,
	residency VARCHAR(10), 
	cost INTEGER,
	PRIMARY KEY (grade_level,residency)
);

CREATE TABLE CREDIT_LIMITS (
	grade_level integer, 
	residency VARCHAR(10), 
	min INTEGER, 
	max INTEGER,
	PRIMARY KEY (grade_level,residency)
);

CREATE TABLE PREREQUISITES (
	course_id VARCHAR(10), 
	gpa FLOAT, 
	prereq_id VARCHAR(10), 
	special VARCHAR(1),
	PRIMARY KEY (course_id)
);

CREATE TABLE REQUESTS (
	student_id VARCHAR(10),
	course_id VARCHAR(10),
	submit_date VARCHAR(10),
	unit INTEGER,
	status VARCHAR(10),
	PRIMARY KEY (student_id, course_id)
);