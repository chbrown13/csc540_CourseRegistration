package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.sql.*;

import controller.Course;
import db.DBBuilder;

public class Admin {
	
	private String empID;
	private String ssn;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String DOB;
	
	public Admin(String id, String ssn, String first, String last, String dob, String user,
			String pass) {
		this.empID = id;
		this.ssn = ssn;
		this.firstName = first;
		this.lastName = last;
		this.username = user;
		this.password = pass;
		this.DOB = dob;
	}
	
	public Admin(){
		
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return DOB;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.DOB = dateOfBirth;
	}
	
	/**
	 * @return empID
	 */
	public String getempID(){
		return empID;
	}
	
	/**
	 * @return the SSN
	 */
	public String getSSN(){
		return ssn;
	}
	
	/**
	 * @return firstName
	 */
	public String getFirstName(){
		return firstName;
	}
	
	/**
	 * @return lastName
	 */
	public String getLastName(){
		return lastName;
	}
	
	/**
	 * @return userName
	 */
	public String getUserName(){
		return username;
	}
	
	/**
	 * @return password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * @return the empID
	 */
	public String getEmpID() {
		return empID;
	}
	/**
	 * @param empID the empID to set
	 */
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @param userName the username to set
	 */
	public void setUserName(String userName) {
		this.username = userName;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * The logged in administrator will be able to view his profile.
	 */
	public String viewProfile() {
		String output = "My Profile" + 
				"\n First Name: " + this.firstName + 
				"\n Last Name: " + this.lastName + 
				"\n Date of Birth: " + this.DOB +
				"\n Employee ID: " + this.empID;
		return output;
	}
	
	/**
	 * Administrator will enter the required information for creating a new student
		account.
	 */
	public void enrollStudent(Student s) {
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			float bill = 0;
			stmt = conn.prepareStatement("SELECT cost FROM COSTS WHERE grade_level=? AND residency=?");
			stmt.setInt(1, s.getLevel());
			stmt.setString(2, s.getStatus());
			rs = stmt.executeQuery();
			if(rs.next()) {
				bill = (float)rs.getInt("cost");
				s.setBill(bill);
			}
			stmt = conn.prepareStatement("INSERT INTO STUDENTS VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, s.getStudentID());
			stmt.setString(2, s.getFirstName());
			stmt.setString(3, s.getLastName());
			stmt.setString(4, s.getUsername());
			stmt.setString(5, s.getPassword());
			stmt.setString(6, s.getEmail());
			stmt.setString(7, s.getDOB());
			stmt.setInt(8, s.getLevel());
			stmt.setString(9, s.getStatus());
			stmt.setFloat(10, s.getBill());
			stmt.setString(11, s.getDeptID());
			stmt.setString(12, Course.enrolledCoursesString(s.getPendingCourses()));
			stmt.setString(13, Course.coursesString(s.getCourses()));
			stmt.setString(14, s.getPhoneNumber());
			stmt.setString(15, s.getAddress());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close();
	}
	
	/**
	 * Administrator can view the details(Name, DOB, contact details, past
courses/grades) for a particular student after entering the student id. He should not be able to view the
student's password. This will also have a submenu called � Enter Grades� . Admin will be able to
view/add grades for the student.
	 * @param id
	 */
	public String viewStudent(String id) {
		Student s = findStudent(id);
		String output = "";
		if(s != null) {
			output = "Student Details" + 
				"\n First Name: " + s.getFirstName() + 
				"\n Last Name: " + s.getLastName() + 
				"\n Date of Birth: " + s.getDOB() +
				"\n Student Level: " + s.getLevel() +
				"\n Student Residency Status: " + s.getStatus() +
				"\n Amount Owed(if any): " + s.getBill() + 
				"\n GPA: " + s.getGpa() + 
				"\n Phone: " + s.getPhoneNumber() +
				"\n Email: " + s.getEmail() +
				"\n Address: " + s.getAddress();
		}
		else {
			output = "Invalid Student";
		}
		return output;
	}
	
	/**
	 * Admin can enter grades for students when class is completed. Method moves class from pending to completed with grade
	 * @param id
	 * @param course
	 * @param grade
	 */
	public void enterGrade(String id, String course, String grade) {
		Student student = findStudent(id);
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM STUDENTS WHERE student_id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				List<Course> enrolled = Course.enrolledCoursesList(rs.getString("enrolled_courses"));
				Map<Course,String> completed = Course.coursesList(rs.getString("courses"));
				for(Course c: enrolled) {
					if (c.getCourseID().equals(course)) {
						completed.put(c, grade);
						enrolled.remove(c);
						student.setCourses(completed);
						student.setPendingCourses(enrolled);
						stmt = conn.prepareStatement("UPDATE STUDENTS SET enrolled_courses=?,courses=? WHERE student_id = ?");
						stmt.setString(1, Course.enrolledCoursesString(enrolled));
						stmt.setString(2, Course.coursesString(completed));
						stmt.setString(3, id);
						stmt.executeUpdate();
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close();
	}
	
	/**
	 * Administrator can view details of an existing course by entering the Course Id. The
administrator should also be able to view all courses and details
	 * @param id
	 */
	public String viewCourse(String id) {
		Course c = findCourse(id);
		Prerequisite p = findPrereq(id);
		String output = "";
		if(c != null) {
			output = "Course Details" +
					"\n Course ID: " + c.getCourseID() + 
					"\n Class Name: " + c.getCourseName() +
					"\n Department: " + c.getDeptID() + 
					"\n Level: " + Student.displayLevel(c.getLevel()) + 
					"\n Credits: " + c.getCredits();
				if(p != null) {
					c.setPrereq(p);
					output += "\n GPA Requirement: " + p.getGPA() +
					"\n Prerequisite Courses: " + p.getPrereqID() +
					"\n Special Approval Required: " + p.getPermission();
				}
		}
		else {
			output = "Invalid Course";
		}
		return output;
	}
	
	/**
	 * Administrator can view details of the current offering by entering the Course Id.
	 * @param id
	 */
	public String viewCourseOffering(String id, String semester, String year) {
		Semester s = findCourseOffering(id, semester, year);
		String output = "";
		if(s != null) {
			output = "Course Offering" +
					"\n Course ID: " + s.getCourseID() + 
					"\n Semester: " + s.getSemester() +
					"\n Faculty: " + Instructor.instructorsString(s.getInstructors()) +
					"\n Days of the Week: " + s.getDays() + 
					"\n Start Time: " + s.getStartTime() +
					"\n End Time: " + s.getEndTime() +
					"\n Course Size: " + s.getSize() +
					"\n Waitlist Size: " + s.getWaitlist().size();
		}
		else {
			output = "Invalid Course Offering";
		}
		return output;
	}
	
	/**
	 * Administrator will be able to add a new course using this option. Details like prerequisites,
level, department etc will need to be provided.
	 * @param p
	 * @param level
	 * @param dept
	 */
	public void addCourse(Course c) {
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO COURSES VALUES (?,?,?,?,?)");
			stmt.setString(1, c.getCourseID());
			stmt.setString(2, c.getCourseName());
			stmt.setInt(3, c.getCredits());
			stmt.setString(4, c.getDeptID());
			stmt.setInt(5, c.getLevel());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close();
	}
	
	/**
	 * Administrator will be able to add a new course offering using this option. The administrator
will have to provide details like days of the week, timing, location, names of faculty members
teaching the course etc.
	 */
	public void addCourseOffering(Semester s) { 
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO SEMESTERS VALUES (?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, s.getCourseID());
			stmt.setString(2, s.getSemester());
			stmt.setString(3, s.getYear());
			stmt.setString(4, Instructor.instructorsString(s.getInstructors()));
			stmt.setInt(5, s.getSize());
			stmt.setInt(6, s.getMaxSeats());
			stmt.setString(7, s.getWaitString());
			stmt.setInt(8, s.getMaxWait());
			stmt.setString(9, s.getSchedule());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close();
	}
	/**
	 * Administrator will be able to view a list of requests
that are waiting for approval
	 * @return
	 */
	public String viewRequests() {
		List<Request> reqs = getRequests();
		String output = "";
		int i = 1;
		if(reqs == null || reqs.isEmpty()) {
			output = "No requests";
		}
		else{
			for(Request r: reqs) {
				output += i +") "+r.getDate()+" "+r.getCourseID()+" "+r.getStudentID()+" "+r.getStatus()+"\n";
				i++;
			}
		}
		return output;
	}
	
	public void approveRequest(String student, String course) {
		updateRequest("approved",student,course);
	}
	
	public void rejectRequest(String student, String course) {
		updateRequest("rejected",student,course);
	}
	
	/**
	 * This option when selected will enforce the add/drop deadline and will
result in dropping of all the waitlisted courses, dropping courses of students with past due. This
option can only be selected once for a semester and cannot be reverted after applied. So the
application should ask for confirmation once before enforcing the deadlines.
	 */
	public void enforceDeadline(String sem, String year) {
			Connection conn = DBBuilder.getConnection();
			Statement stmt = null;
			PreparedStatement pstmt = null;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE STUDENTS SET enrolled_courses='' WHERE bill > 0");
				pstmt = conn.prepareStatement("UPDATE SEMESTERS SET waitlist=0 WHERE semester=? AND year=?");
				pstmt.setString(1, sem);
				pstmt.setString(2, year);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBBuilder.close(stmt);
			DBBuilder.close();
	}
	
	public static boolean checkDrop(Student student, String course, String sem, String year) {
		Semester s = findCourseOffering(course, sem, year);
		Course c = findCourse(course);
		if(!s.dropStudent(student,c)) {
			return false;
		}
		return true;
	}
	
	public static boolean checkEnroll(Student student, String course, String sem, String year) {
		Semester s = findCourseOffering(course, sem, year);
		Course c = findCourse(course);
		if(!s.enrollStudent(student, c)) {
			if(s.waitlistStudent(student)) {
				System.out.println("Waitlisted");
				return false;
			}
			System.out.println("Rejected");
			return false;
		}
		else if (c.getPrereq() != null) {
			Prerequisite p = c.getPrereq();
			if(p.getPermission()) {
				System.out.println("Rejected- You need special permission to take this course");
				return false;
			}
			if(student.getLevel() != c.getLevel()) {
				System.out.println("Rejected- You are not a "+Student.displayLevel(c.getLevel()));
				return false;
			}
			if(student.getGpa() < p.getGPA()) {
				System.out.println("Rejected- GPA too low");
				return false;
			}
			if(!student.getCourses().keySet().contains(p.getPrereqID())) {
				System.out.println("Rejected- You haven't taken prerequisite class");
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkCredits(Student student, Course course, boolean drop) {
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement("SELECT min,max FROM CREDIT_LIMITS WHERE grade_level=? AND residency=?");
			stmt.setInt(1,student.getLevel());
			stmt.setString(2, student.getStatus());
			rs = stmt.executeQuery();
			if(rs.next()) {
				if(drop) {
					int min = rs.getInt("min");
					return student.getCredits() - course.getCredits() >= min;
				}
				else {
					int max = rs.getInt("max");
					return student.getCredits() + course.getCredits() <= max;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static float calculateGPA(String id) {
		float gpa = 0;
		Student s = findStudent(id);
		Map<Course,String> graded = s.getCourses();
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int credits = 0;
		float points = 0;
		for(Course c: graded.keySet()) {
			try {
				stmt = conn.prepareStatement("SELECT credits FROM COURSES WHERE course_id=?");
				stmt.setString(1,c.getCourseID());
				rs = stmt.executeQuery();
				if(rs.next()) {
					credits += rs.getInt("credits");
				}
				stmt = conn.prepareStatement("SELECT points FROM GRADING_SYSTEM WHERE grade=?");
				stmt.setString(1,graded.get(c));
				rs = stmt.executeQuery();
				if(rs.next()) {
					points += rs.getFloat("points");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(credits > 0) {	gpa = (points*credits)/credits; }
		s.setGpa(gpa);
		return gpa;
	}
	
	/**
	 * Update status of a request in the database
	 * @param status
	 * @param student
	 * @param course
	 */
	private void updateRequest(String status, String student, String course) {
		Connection conn = DBBuilder.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement("UPDATE REQUESTS SET status=? WHERE student_id = ? AND course_id = ?");
			stmt.setString(1, status);
			stmt.setString(2, student);
			stmt.setString(3, course);
			stmt.executeUpdate();
			while(rs.next()) {
				Request r = new Request();
				r.setStatus(rs.getString("student_id"));
				r.setCourseID(rs.getString("course_id"));
				r.setDate(rs.getString("submit_date"));
				r.setStatus(rs.getString("status"));
				r.setNumUnits(rs.getInt("units"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close(rs);
		DBBuilder.close();
	}
	
	/**
	 * Connect to database to find student
	 * @param id Student ID
	 * @return Student details
	 */
	static Student findStudent(String id) {
		Student s = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM STUDENTS WHERE student_id = "+id);
			if(rs.next()) {
				s = new Student();
				s.setStudentID(rs.getString("student_id"));
				s.setFirstName(rs.getString("first_name"));
				s.setLastName(rs.getString("last_name"));
				s.setUsername(rs.getString("user_name"));
				s.setEmail(rs.getString("email"));
				s.setPassword(rs.getString("password"));
				s.setDOB(rs.getString("birth_date"));
				s.setLevel(rs.getInt("grade_level"));
				s.setStatus(rs.getString("status"));
				s.setBill(rs.getInt("bill"));
				s.setDeptID(rs.getString("dept_id"));
				String taken = rs.getString("courses");
				String current = rs.getString("enrolled_courses");
				s.setPendingCourses(Course.enrolledCoursesList(current));
				s.setCourses(Course.coursesList(taken));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBBuilder.close(rs);
		DBBuilder.close(stmt);
		DBBuilder.close();
		return s;
	}
	
	/**
	 * Connect to database to find course
	 * @param id Course ID
	 * @return Course information
	 */
	static Course findCourse(String id) {
		Course c = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM COURSES WHERE course_id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				c = new Course();
				c.setCourseID(rs.getString("course_id"));
				c.setCourseName(rs.getString("title"));
				c.setCredits(rs.getInt("credits"));
				c.setDeptID(rs.getString("dept_id"));
				c.setLevel(rs.getInt("grade_level"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBBuilder.close();
		DBBuilder.close(rs);
		DBBuilder.close(stmt);
		return c;
	}
	
	/**
	 * Connect to database to find course offering
	 * @param id Course ID, semester (FALL or SPRING), and year
	 * @return Course information
	 */
	static Semester findCourseOffering(String id, String sem, String year) {
		Semester s = null;
		Course c = findCourse(id);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM SEMESTERS WHERE course_id=? AND semester = ? AND year = ?");
			stmt.setString(1, id);
			stmt.setString(2, sem);
			stmt.setString(3, year);
			rs = stmt.executeQuery();
			if(rs.next()) {
				s = new Semester();
				s.setCourseID(rs.getString("course_id"));
				s.setSemester(rs.getString("semester"));
				s.setYear(rs.getString("year"));
				s.setInstructors(Instructor.instructorsList(rs.getString("instructors"),c.getDeptID()));
				s.setMaxSeats(rs.getInt("max_size"));
				s.setMaxWait(rs.getInt("wait_size"));
				s.setSchedule(rs.getString("schedule"));
				s.setSize(rs.getInt("class_size"));
				s.setWaitlist(s.studentWaitList(rs.getString("waitlist")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBBuilder.close();
		DBBuilder.close(rs);
		DBBuilder.close(stmt);
		return s;
	}
	
	/**
	 * Connect to database to find course offering
	 * @param id Course ID, semester (FALL or SPRING), and year
	 * @return Course information
	 */
	static List<Semester> findSemester(String sem, String year) {
		List<Semester> list = new ArrayList<Semester>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM SEMESTERS WHERE semester = ? AND year = ?");
			stmt.setString(1, sem);
			stmt.setString(2, year);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Semester s = new Semester();
				String cid = rs.getString("course_id");
				s.setCourseID(cid);
				s.setSemester(rs.getString("semester"));
				s.setYear(rs.getString("year"));
				s.setInstructors(Instructor.instructorsList(rs.getString("instructors"),findCourse(cid).getDeptID()));
				s.setMaxSeats(rs.getInt("max_size"));
				s.setMaxWait(rs.getInt("wait_size"));
				s.setSchedule(rs.getString("schedule"));
				s.setSize(rs.getInt("class_size"));
				s.setWaitlist(s.studentWaitList(rs.getString("waitlist")));
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBBuilder.close();
		DBBuilder.close(rs);
		DBBuilder.close(stmt);
		return list;
	}
	
	/**
	 * Connect to database to find course prerequisites
	 * @param id Course ID
	 * @return prerequisites for course
	 */
	private Prerequisite findPrereq(String id) {
		Prerequisite p = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM PREREQUISITES WHERE course_id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				p = new Prerequisite();
				p.setCourseID(rs.getString("course_id"));
				p.setGPA(rs.getFloat("gpa"));
				p.setPrereqID(rs.getString("prereq_id"));
				p.setPermission(Boolean.parseBoolean(rs.getString("special")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBBuilder.close();
		DBBuilder.close(rs);
		DBBuilder.close(stmt);
		return p;
	}
	
	/**
	 * Connect to database and get all requests
	 */
	private List<Request> getRequests() {
		List<Request> list = new ArrayList<Request>();
		Connection conn = DBBuilder.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM REQUESTS");
			while(rs.next()) {
				Request r = new Request();
				r.setStatus(rs.getString("student_id"));
				r.setCourseID(rs.getString("course_id"));
				r.setDate(rs.getString("submit_date"));
				r.setStatus(rs.getString("status"));
				r.setNumUnits(rs.getInt("units"));
				list.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close(rs);
		DBBuilder.close();
		return list;
	}
	
	/**
	 * Initalizes class instances to admin from database.
	 * @param id
	 * @return
	 */
	public boolean setAdmin(String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM ADMINS WHERE user_name=?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				this.setDateOfBirth(rs.getString("birth_date"));
				this.setFirstName(rs.getString("first_name"));
				this.setLastName(rs.getString("last_name"));
				this.setPassword(rs.getString("password"));
				this.setEmpID(rs.getString("emp_id"));
				this.setSsn(rs.getString("ssn"));
				this.setUserName(rs.getString("user_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		DBBuilder.close(rs);
		DBBuilder.close(stmt);
		DBBuilder.close();
		return true;
	}
	
	//testing
	public static void main(String[] args) {
		Admin a = new Admin("1111","123-45-6789","Albus","Dumbledore","05/06/1729","alby","hogwarts");
		String student = a.viewStudent("101");
		System.out.println(student);
		String course = a.viewCourse("CS510");
		System.out.println(course);
		Student me = new Student();
		me.setFirstName("Chris");
		me.setLastName("Brown");
		me.setLevel("graduate");
		me.setStudentID("200106762");
		me.setUsername("dcbrow10");
		me.setDOB("04/07/1991");
		Course c2 = new Course();
		c2.setCourseID("CSC540");
		c2.setCourseName("DBMS");
		c2.setLevel(2);
		//c2.setSchedule("M,W 11:45AM-1:00PM");
		List<Course> l = new ArrayList<Course>();
		l.add(c2);
		me.setPendingCourses(l);
		//a.addCourse(c2);
		//a.enrollStudent(me);
		//a.enterGrade("200106762", "CSC540", "A+");
		System.out.println(a.viewRequests());
		System.out.println(a.calculateGPA("102"));
	}

	
}
