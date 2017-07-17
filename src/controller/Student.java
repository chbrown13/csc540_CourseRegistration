package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBBuilder;

import java.sql.*;

public class Student {
	private String email;
	private int level; // 1 for undergrad, 2 for graduate
	private String status;
	private String username;
	private String password;
	private float gpa = -1;
	private String deptID;
	private String studentID;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String DOB;
	private Map<Course, String> courses;
	private List<Course> pendingCourses;
	private float bill;

	public static final int GRAD = 2;
	public static final int UNDERGRAD = 1;
	public static final String IN_STATE = "in";
	public static final String OUT_OF_STATE = "out";
	public static final String INTERNATIONAL = "inter";
	
	public Student() {
		
	}
	
	/**
	 * @return the bill
	 */
	public float getBill() {
		return bill;
	}

	/**
	 * @param bill
	 *            the bill to set
	 */
	public void setBill(float bill) {
		this.bill = bill;
	}
	
	public void setBill(String bill) {
		try {
			this.bill = Float.parseFloat(bill);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Invalid bill");
		}
	}
	
	/**
	 * @return the dOB
	 */
	public String getDOB() {
		return DOB;
	}

	/**
	 * @param dOB
	 *            the dOB to set
	 */
	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public void editProfile(String Address, String PhoneNumber) {
		if (Address != null) {
			setAddress(Address);
		}
		if (PhoneNumber != null) {
			setPhoneNumber(PhoneNumber);
		}
	}

	/**
	 * @return the courses
	 */
	public Map<Course,String> getCourses() {
		return courses;
	}

	/**
	 * @param courses
	 *            the courses to set
	 */
	public void setCourses(Map<Course,String> courses) {
		this.courses = courses;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the pendingCourses
	 */
	public List<Course> getPendingCourses() {
		return pendingCourses;
	}

	/**
	 * @param pendingCourses
	 *            the pendingCourses to set
	 */
	public void setPendingCourses(List<Course> pendingCourses) {
		this.pendingCourses = pendingCourses;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Sets level based on string
	 * 
	 * @param s
	 */
	public void setLevel(String s) {
		String level = s.toLowerCase();
		if (level.equals("graduate") || level.equals("grad")) {
			this.level = GRAD;
		} else {
			this.level = UNDERGRAD;
		}
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param set
	 *            username
	 */
	public void setUsername(String user) {
		this.username = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @returns student's gpa
	 */
	public float getGpa() {
		if(gpa < 0) {
			return Admin.calculateGPA(this.studentID);
		}
		return gpa;
	}

	/**
	 * @param the gpa to set
	 */
	public void setGpa(float gpa) {
		this.gpa = gpa;
	}

	/**
	 * @return the deptID
	 */
	public String getDeptID() {
		return deptID;
	}

	/**
	 * @param deptID
	 *            the deptID to set
	 */
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	/**
	 * @return the studentID
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * @param studentID
	 *            the studentID to set
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param the
	 *            level
	 */
	public int getLevel() {
		return level;
	}

	public String viewProfile() {
		String output = "My Profile" + "\n First Name: " + this.firstName + "\n Last Name: " + this.lastName
				+ "\n Email: " + this.email + "\n Phone: " + this.phoneNumber + "\n Level: " + this.level
				+ "\n Status: " + this.status;
		return output;
	}
	
	public void editProfile(String first, String last, String email, String phone) {
		Connection conn = DBBuilder.getConnection();
		PreparedStatement pstmt = null;
		this.setFirstName(first);
		this.setLastName(last);
		this.setEmail(email);
		this.setPhoneNumber(phone);
		try {
			pstmt = conn.prepareStatement("UPDATE STUDENTS SET first_name=?,last_name=?,email=?,phone=? WHERE student_id=?");
			pstmt.setString(1, first);
			pstmt.setString(2, last);
			pstmt.setString(3, email);
			pstmt.setString(4, phone);
			pstmt.setString(5, this.studentID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(pstmt);
		DBBuilder.close();
	}

	public String viewLetterGrades() {
		String output = "Letter Grades";
		for(Course c: courses.keySet()) {
			output += " \n"+c.getCourseID()+": "+courses.get(c);
		}
		return output;
	}
	
	public String viewGrades() {
		String output = "Grade Point Average";
		output += "\n " + getGpa();
		return output;
	}
	
	public String viewCourses() {
		String output = "My Courses";
		return output;
	}
	public String viewPending(String sem, String year) {
		String output = "All Classes";
		List<Semester> courses = Admin.findSemester(sem,year);
		if(!courses.isEmpty()) {
			for(Semester s: courses) {
				if(this.pendingCourses.contains(Admin.findCourse(s.getCourseID()))) {
					output += "\n "+s.getCourseID()+"- PENDING";
				}
				else {
					output += " \n" + s.getStudentStatus(this);
				}
			}
		}
		return output;
	}
	
	/**
	 * Enroll a student in a course
	 * @param course
	 * @param sem
	 * @param year
	 */
	public void enrollCourses(String course, String sem, String year) {
		boolean check = Admin.checkEnroll(this, course, sem, year);
		if(check) {
			System.out.println("You have been enrolled in " + course);
		}
	}
	
	/**
	 * Drop a student from a course
	 * @param course
	 * @param sem
	 * @param year
	 */
	public void dropCourses(String course, String sem, String year) {
		boolean check = Admin.checkDrop(this, course, sem, year);
		if(check) {
			System.out.println("You have dropped " + course);
		}
	}
	
	/**
	 * Returns current number of credits the student is currently enrolled in
	 * @return
	 */
	public int getCredits() {
		int credits = 0;
		for(Course c: this.pendingCourses) {
			credits += c.getCredits();
		}
		return credits;
	}
	
	public float payBill(float amount) {
		this.bill -= amount;
		Connection conn = DBBuilder.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("UPDATE STUDENTS SET bill=? WHERE student_id=?");
			pstmt.setFloat(1, this.bill);
			pstmt.setString(2, this.studentID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(pstmt);
		DBBuilder.close();
		return this.bill;
	}
	
	/**
	 * Convert classification level to string representation
	 * @param level
	 * @return
	 */
	public static String displayLevel(int level) {
		if(level == GRAD) {
			return "Graduate Student";
		}
		else if (level == UNDERGRAD) {
			return "Undergraduate Student";
		}
		else {
			return "Invalid Level";
		}
	}
	
	/**
	 * Connect to database to find student
	 * @param id Student ID
	 * @return Student details
	 */
	public static Student findStudent(String id) {
		Student s = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM STUDENTS WHERE student_id=?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				s = new Student();
				s.setStudentID(rs.getString("student_id"));
				s.setFirstName(rs.getString("first_name"));
				s.setLastName(rs.getString("last_name"));
				s.setUsername(rs.getString("user_name"));
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
	
	//testing
	public static void main(String[] args) {
		Student s = new Student();
		s = findStudent("101");
		System.out.println(s.viewProfile());
		s.editProfile(s.getFirstName(),s.getLastName(),"harry@ncsu.edu","1234567");
		System.out.println(s.viewProfile());
		//System.out.println(s.getBill());
		//s.payBill(100);
		//System.out.println(s.getBill());
		System.out.println(s.viewLetterGrades());
		//s.dropCourses("CE420");
		System.out.println(Course.enrolledCoursesString(s.getPendingCourses()));
	}
	
	/**
	 * Sets student from database based off of given id
	 * @param id ID of student
	 * @return whether or not student is found
	 */
	public boolean setStudent(String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = DBBuilder.getConnection();
		try {
			stmt = conn.prepareStatement("SELECT * FROM STUDENTS WHERE user_name=?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				this.setStudentID(rs.getString("student_id"));
				this.setFirstName(rs.getString("first_name"));
				this.setLastName(rs.getString("last_name"));
				this.setUsername(rs.getString("user_name"));
				this.setPassword(rs.getString("password"));
				this.setDOB(rs.getString("birth_date"));
				this.setLevel(rs.getInt("grade_level"));
				this.setStatus(rs.getString("status"));
				this.setBill(rs.getInt("bill"));
				this.setDeptID(rs.getString("dept_id"));
				String taken = rs.getString("courses");
				String current = rs.getString("enrolled_courses");
				this.setPendingCourses(Course.enrolledCoursesList(current));
				this.setCourses(Course.coursesList(taken));
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
}
