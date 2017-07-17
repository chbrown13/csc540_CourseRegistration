package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Console;

/**
 * Text GUI of project
 *
 */
public class TextGUI {

	private Scanner scanner = new Scanner(System.in);

	private final static String START_MENU = "----------\nSTART MENU\n----------\n1. Login\n2. Exit\n";
	private final static String LOGIN = "-----\nLOGIN\n-----\n";
	private final static String ADMIN_MAIN = "----------\nADMIN MAIN\n----------\n1. View Profile\n2. Enroll A New Student\n3. View Student's Details\n4. View/Add Courses\n5. View/Add Course Offering\n6. View/Approve Special Enrollment Request\n7. Enforce Add/Drop Deadline\n8. Logout\n";
	private final static String STUDENT_MAIN = "------------\nSTUDENT MAIN\n------------\n1. View/Edit Profile\n2. View Course/Enroll/Drop courses\n 3. View Pending courses(Pending, Rejected, Waitlisted)\n4. View Grades\n5. View/Pay Bill(Bill does not contain previous semester bills - just current semester bills)\n7. Logout\n";
	private final static String EXIT = "Exited Program\n";
	private Object user;
	private Admin admin;
	private Student student;
	private boolean userType; // True for admin, false for student;

	/**
	 * starts the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TextGUI textGUI = new TextGUI();
		textGUI.startMenu();
	}

	/**
	 * Where the program starts
	 */
	private void startMenu() {

		System.out.print(START_MENU);

		switch (checkNumericalInput(1, 2)) {
		case -1:
			startMenu();
			break;
		case 1:
			login();
			break;
		case 2:
			exit();
			break;
		default:
			break;
		}

	}

	/**
	 * Checks if input is numerical and within provided range inclusively
	 * 
	 * @param startNum
	 *            Starting number
	 * @param endNum
	 *            Ending Number
	 * @return Whether or not the input is a number and within the given ranges
	 */
	private int checkNumericalInput(int startNum, int endNum) {
		String input = scanner.nextLine();
		try {
			int inputNum = Integer.parseInt(input);
			if (inputNum >= startNum && endNum <= endNum) {

				return inputNum;
			}
		} catch (Exception e) {
			System.out.printf("Please input a value between %d and %d inclusive.\n", startNum, endNum);
			return -1;
		}
		System.out.printf("Please input a value between %d and %d inclusive.\n", startNum, endNum);
		return -1;
	}

	/**
	 * Log in part of Text GUI
	 */
	private void login() {
		System.out.print(LOGIN);
		System.out.print("Username: ");
		String username = scanner.nextLine();
		if (username.equals("q")) {
			exit();
		}
		System.out.print("Password: ");
		String password = scanner.nextLine();

		user = checkCredentials(username, password);

		if (user == null) {
			System.out.print("Credentials invalid. Please try again. Type 'q' to quit.");
			login();
		} else if (user instanceof Student) {
			student = (Student) user;
			userType = false;
			StudentLogin();
		} else {
			admin = (Admin) user;
			userType = true;
			AdminLogin();
		}
	}

	/**
	 * Method made for testing purposes, real method would verify with sql
	 * database
	 * 
	 * @param username
	 *            Username of user
	 * @param password
	 *            Password of user
	 * @return Whether or not the credentials check out -1 for invalid user 0
	 *         for student 1 for admin
	 */
	private Object checkCredentials(String username, String password) {
		if (username.equals("alby") && password.equals("hogwarts")) {
			admin = new Admin();
			if (admin.setAdmin(username)) {
				return admin;
			}
		} else if (password.equals("password")) {
			student = new Student();
			if (student.setStudent(username)) {
				return student;
			}
		}
		return null;
	}

	/**
	 * Student Login Page
	 */
	private void StudentLogin() {
		System.out.print(STUDENT_MAIN);

		int input = this.checkNumericalInput(1, 5);

		if (input == 1) {
			studentProfile();
		} else if (input == 2) {
			viewEnrollDropCourses();
		} else if (input == 3) {
			viewPending();
		} else if (input == 4) {
			viewGrades();
		} else if (input == 5) {
			viewPayBill();
		} else {
			logout();
		}
	}

	/**
	 * Student Profile page, he/she can view or edit this page.
	 */
	private void studentProfile() {
		System.out.println("Press 0 To Go Back\nPress 1 To View Profile\nPress 2 to Edit Profile");

		int input = this.checkNumericalInput(0, 3);

		if (input == -1) {
			studentProfile();
		} else if (input == 0) {
			StudentLogin();
		} else if (input == 1) {
			viewStudentProfile();
		} else {
			editStudent();
		}
	}

	/**
	 * View the student profile as admin
	 */
	private void viewStudentProfile() {
		System.out.println(student.viewProfile());
		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			viewStudentProfile();
		}
		studentProfile();
	}

	/**
	 * This is where the student will edit his/her profile
	 */
	private void editStudent() {
		System.out.print("1. Enter First Name : ");
		String firstName = scanner.nextLine();
		System.out.print("2. Enter Last Name : ");
		String lastName = scanner.nextLine();
		System.out.print("3. Enter Email : ");
		String email = scanner.nextLine();
		System.out.print("4. Enter Phone Number : ");
		String phone = scanner.nextLine();
		student.editProfile(firstName, lastName, email, phone);

		studentProfile();
	}

	/**
	 * This is where the student can choose to view, enroll, or drop courses
	 */
	private void viewEnrollDropCourses() {
		System.out.println("Select Appropriate Menu Option:\n1. View Courses\n2. Enroll Courses\n3. Drop Courses");

		int input = this.checkNumericalInput(0, 3);

		if (input == 1) {
			viewCourses();
		} else if (input == 2) {
			enrollCourses();
		} else if (input == 3) {
			dropCourses();
		} else if (input == 0) {
			StudentLogin();
		} else {
			viewEnrollDropCourses();
		}
	}

	/**
	 * This is where student will view his/her courses.
	 */
	private void viewCourses() {
		System.out.println("Press 0  To Go Back To Previous Menu");
		System.out.println(student.viewCourses());

		if (!scanner.nextLine().equals("0")) {
			viewCourses();
		}
		viewEnrollDropCourses();
	}

	/**
	 * This is where the student will enroll into courses
	 */
	private void enrollCourses() {
		System.out.print("Course: ");
		String course = scanner.nextLine();
		System.out.print("Semester (Fall or Spring): ");
		String semester = scanner.nextLine();
		while (!semester.toLowerCase().equals("fall") || !semester.toLowerCase().equals("spring")) {
			semester = scanner.nextLine();
			System.out.print("Semester (Fall or Spring): ");
		}
		System.out.print("Year (YYYY): ");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Year (YYYY): ");
		}
		String year = scanner.nextLine();

		student.enrollCourses(course, semester, year);

		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			enrollCourses();
		}
		viewEnrollDropCourses();
	}

	/**
	 * This is where the student will choose to drop his/her courses
	 */
	private void dropCourses() {
		System.out.print("Course: ");
		String course = scanner.nextLine();
		System.out.print("Semester (Fall or Spring): ");
		String semester = scanner.nextLine();
		while (!semester.toLowerCase().equals("fall") || !semester.equals("spring")) {
			semester = scanner.nextLine();
			System.out.print("Semester (Fall or Spring): ");
		}
		System.out.print("Year (YYYY): ");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Year (YYYY): ");
		}
		String year = scanner.nextLine();

		student.dropCourses(course, semester, year);
		System.out.println("Press 0  To Go Back To Previous Menu");

		if (!scanner.nextLine().equals("0")) {
			dropCourses();
		}
		viewEnrollDropCourses();
	}

	/**
	 * This is where the student can view his/her pending courses
	 */
	private void viewPending() {
		System.out.print("Semester (Fall or Spring): ");
		String semester = scanner.nextLine();
		while (!semester.toLowerCase().equals("fall") || !semester.toLowerCase().equals("spring")) {
			semester = scanner.nextLine();
			System.out.print("Semester (Fall or Spring): ");
		}
		System.out.print("Year (YYYY): ");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Year (YYYY): ");
		}
		String year = scanner.nextLine();
		System.out.println(student.viewPending(semester,year));
		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			viewPending();
		}
		StudentLogin();
	}

	/**
	 * This is where the student can view his/her grades. They can choose to see
	 * letter grades of each course or overall gpa.
	 */
	private void viewGrades() {
		System.out.println("Select Appropriate Menu Option:\n1. Display Letter Grades\n2. Display GPA\n");

		int input = this.checkNumericalInput(0, 2);

		if (input == 1) {
			viewLetterGrades();
		} else if (input == 2) {
			viewGPA();
		} else if (input == 0) {
			StudentLogin();
		} else {
			viewGrades();
		}
	}

	/**
	 * This is the method that will present the letter grades for each course
	 * the student has taken.
	 */
	private void viewLetterGrades() {
		System.out.println(student.viewLetterGrades());
		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			viewLetterGrades();
		}
		viewGrades();
	}

	/**
	 * This is where the student can view his/her gpa.
	 */
	private void viewGPA() {
		System.out.println(student.viewGrades());
		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			viewGPA();
		}
		viewGrades();
	}

	/**
	 * This is where the student can view or pay his/her bills.
	 */
	private void viewPayBill() {
		System.out.println("Select Appropriate Menu Option:\n1. Display Student Balance\n2. Pay Bills\n");

		int input = this.checkNumericalInput(0, 2);

		if (input == 1) {
			viewBills();
		} else if (input == 2) {
			payBills();
		} else if (input == 0) {
			StudentLogin();
		} else {
			viewPayBill();
		}
	}

	/**
	 * This is where the student can view his/her bills.
	 */
	private void viewBills() {
		System.out.println("Bill: " + student.getBill());
		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			viewCourse();
		}
		viewPayBill();
	}

	/**
	 * This is where the student can pay his/her bills.
	 */
	private void payBills() {
		System.out.println("Input Payment Amount: ");
		student.payBill(scanner.nextFloat());
		viewPayBill();
	}

	/**
	 * This is the Admin's Main login page.
	 */
	private void AdminLogin() {
		System.out.print(ADMIN_MAIN);

		int input = this.checkNumericalInput(1, 8);

		if (input == 1) {
			adminProfile();
		} else if (input == 2) {
			enrollStudent();
		} else if (input == 3) {
			viewStudent();
		} else if (input == 4) {
			viewAddCourse();
		} else if (input == 5) {
			viewAddOffering();
		} else if (input == 6) {
			viewApproveRequest();
		} else if (input == 7) {
			enforceDeadlines();
		} else if (input == 8) {
			logout();
		} else {
			AdminLogin();
		}
	}

	/**
	 * This is where the admin can view or approve requests NEED TO ADD STUFF
	 * HERE
	 */
	private void viewApproveRequest() {
		System.out.println("List Of Pending Requests: ");
		String output = admin.viewRequests();
		System.out.println(output);

		System.out.println("Press 0  To Go Back To Previous Menu");
		if (!scanner.hasNextInt()) {
			System.out.println("Please input a positive integer value.");
			viewApproveRequest();
		}
		int input = scanner.nextInt();
		if (input == 0) {
			AdminLogin();
		} else {
			int index1 = output.indexOf("" + input + ")");
			int index2 = output.indexOf("" + (input + 1) + ")");

			output = output.substring(index1 + 2, index2);
			Scanner temp = new Scanner(output);
			temp.next();

			String course = temp.next();
			String student = temp.next();

			System.out.println("Reject (0) or Approve (1) request?");

			if (scanner.nextInt() == 0) {
				admin.rejectRequest(student, course);
			} else {
				admin.approveRequest(student, course);
			}
			scanner.close();
		}
		AdminLogin();

	}

	/**
	 * This enforces the deadlines, so that all students who have no paid will
	 * lose all their enrolled classes and waitlists
	 */
	public void enforceDeadlines() {
		System.out.println("Are you sure you want to enforce deadlines: \n1) Yes\n2) No\n");

		int input = this.checkNumericalInput(1, 2);

		if (input == -1) {
			enforceDeadlines();
		} else if (input == 1) {
			System.out.print("Semester: ");
			String semester = scanner.nextLine();
			System.out.print("Year: ");
			String year = scanner.nextLine();

			admin.enforceDeadline(semester, year);
		}
		AdminLogin();
	}

	/**
	 * Logs out user
	 */
	public void logout() {
		// Since user cannot get back to other methods when in login, this
		// should work fine
		login();
	}

	/**
	 * Allows admin to view and add course offerings. The way we made this
	 * program, course offerings is semesters.
	 */
	private void viewAddOffering() {
		System.out.println(
				"Select Appropriate Menu Option:\n0. Go Back To Previous Menu\n1. View Course Offerings\n2. Add New Course Offering\n");
		int input = this.checkNumericalInput(0, 2);

		if (input == -1) {
			viewAddOffering();
		} else if (input == 0) {
			AdminLogin();
		} else if (input == 1) {
			this.viewOffering();
		} else {
			this.addOffering();
		}
	}

	/**
	 * Allows admin to view course offerings.
	 */
	private void viewOffering() {
		System.out.print("Please Enter Course Offering Id: ");
		String id = scanner.nextLine();
		System.out.print("Please Enter Semester: ");
		String semester = scanner.nextLine();
		System.out.print("Please Enter Year: ");
		String year = scanner.nextLine();

		System.out.println(admin.viewCourseOffering(id, semester, year));
		if (!scanner.nextLine().equals("0")) {
			viewOffering();
		}
		viewAddOffering();
	}

	/**
	 * Allows admin to add course offerings
	 */
	private void addOffering() {
		Semester semester = new Semester();
		System.out.print("1. Enter Course Id : ");
		semester.setCourseID(scanner.nextLine());
		System.out.print("2. Enter Semester : ");
		semester.setSemester(scanner.nextLine());
		System.out.print("3. Enter Faculty Name (q to quit) : ");
		String temp = scanner.nextLine();
		List<Instructor> instructors = new ArrayList<Instructor>();
		while (!temp.equals("q")) {
			Instructor inst = new Instructor();
			inst.setName(temp);
			instructors.add(inst);
			temp = scanner.nextLine();
		}
		semester.setInstructors(instructors);
		System.out.print("4. Enter Days of the Week : ");
		semester.setDays(scanner.nextLine());
		System.out.print("5. Enter Class Start Time : ");
		semester.setStartTime(scanner.nextLine());
		System.out.print("6. Enter Class End Time : ");
		semester.setEndTime(scanner.nextLine());
		System.out.print("7. Class Size: ");
		semester.setMaxSeats(Integer.parseInt(scanner.nextLine()));
		System.out.print("8. Wait list Size: ");
		semester.setMaxWait(Integer.parseInt(scanner.nextLine()));

		admin.addCourseOffering(semester);
		System.out.println("Press 0 To Go Back To Previous Menu");
		if (!scanner.nextLine().equals("0")) {
			addOffering();
		}

		viewAddOffering();
	}

	/**
	 * Lets admin view his profile
	 */
	private void adminProfile() {
		System.out.print(admin.viewProfile());
		System.out.printf("\nType 0 to Go Back\n");

		if (!scanner.nextLine().equals("0")) {
			adminProfile();
		}

		AdminLogin();
	}

	/**
	 * Lets admin enroll his/her students
	 */
	private void enrollStudent() {
		Student student = new Student();
		String input;
		System.out.print("Enter Student Id: ");
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print("Please enter an positive integer value. \nEnter Student Id: ");
		}
		student.setStudentID(scanner.nextLine());
		System.out.print("Enter Student's First Name: ");
		student.setFirstName(scanner.nextLine());
		System.out.print("Enter Student's Last Name: ");
		student.setLastName(scanner.nextLine());
		System.out.print("Enter Student's D. O. B.(MM-DD-YYYY): ");
		student.setDOB(scanner.nextLine());
		System.out.print("Enter Student's Level (0 for undergrad, 1 for graduate): ");
		student.setLevel(scanner.nextLine());
		System.out.print("Enter Student's Residency Status (In-State, Out of State, International): ");
		student.setStatus(scanner.nextLine());
		System.out.print("Enter Amount Owed(if any): ");
		while (!scanner.hasNextFloat()) {
			scanner.nextLine();
			System.out.print("Please enter an positive numerical value. Enter Amount Owed(if any): ");
		}
		student.setBill(scanner.nextLine());

		admin.enrollStudent(student);
		System.out.println("Successfully created student!");

		AdminLogin();
	}

	/**
	 * Lets admin view students
	 */
	private void viewStudent() {
		System.out.print("Please enter Student Id: ");
		int id = Integer.parseInt(scanner.nextLine());

		System.out.println(admin.viewStudent(Integer.toString(id)));

		System.out.println("\nPress 0 To Go Back To Previous Menu\nPress 1 To View Grades\nPress 2 To Enter Grades");

		int input = this.checkNumericalInput(0, 2);
		if (input == -1) {
			viewStudent();
		} else if (input == 0) {
			AdminLogin();
		} else if (input == 1) {
			viewGrade(Integer.toString(id));
		} else {
			enterGrade(Integer.toString(id));
		}
	}

	public void viewGrade(String id) {
		Student temp = new Student();
		temp.setStudent(id);
		try {
			System.out.print(temp.viewGrades());
		} catch (NullPointerException e) {
			System.out.println("No grades recorded");
		}
		System.out.println("\nPress 0 To Go Back To Previous Menu");

		if (!scanner.nextLine().equals("0")) {
			viewGrade(id);
		}

		viewStudent();
	}

	public void enterGrade(String id) {
		System.out.print("Please enter Course: ");
		String course = scanner.nextLine();
		System.out.print("Please enter Grade: ");
		String grade = scanner.nextLine();

		admin.enterGrade(id, course, grade);

		viewStudent();
	}

	/**
	 * Lets admin choose between viewing or adding courses
	 */
	private void viewAddCourse() {
		System.out.print(
				"Select Appropriate Menu Option: \n0. Go Back to Previous Menu\n1. View Course\n2. Add New Course\n");
		int input = this.checkNumericalInput(0, 2);
		if (input == -1) {
			viewStudent();
		} else if (input == 0) {
			AdminLogin();
		} else if (input == 1) {
			viewCourse();
		} else {
			addCourse();
		}
	}

	/**
	 * Lets admin view courses
	 */
	private void viewCourse() {
		System.out.print("Please Enter Course Id: ");
		String id = scanner.nextLine();

		System.out.println(admin.viewCourse(id));
		System.out.printf("Type 0 to Go Back\n");

		if (!scanner.nextLine().equals("0")) {
			viewCourse();
		}

		viewAddCourse();
	}

	/**
	 * Lets admin add courses
	 */
	private void addCourse() {
		Course course = new Course();
		System.out.print("1. Enter Course Id : ");
		course.setCourseID(scanner.nextLine());
		System.out.print("2. Enter Course Name : ");
		course.setCourseName(scanner.nextLine());
		System.out.print("3. Enter Department Name : ");
		course.setDeptID(scanner.nextLine());
		System.out.print("4. Enter Course Level (Under Graduate or Graduate): ");
		course.setLevel(scanner.nextInt());
		Prerequisite p = new Prerequisite();
		System.out.print("5. Enter GPA Requirement(if any) : ");
		p.setGPA(scanner.nextFloat());
		System.out.print("6. Enter prerequisite courses : ");
		p.setCourseID(scanner.nextLine());
		course.setPrereq(p);
		System.out.print("7. Special Approval Required(Y/N): ");
		if (scanner.nextLine().toLowerCase().equals("y")) {
			course.setApproval(true);
		}

		System.out.print("8. Enter Number Of Credits: ");
		course.setCourseID(scanner.nextLine());

		admin.addCourse(course);

		System.out.printf("Type 0 to Go Back\n");
		if (!scanner.nextLine().equals("0")) {
			addCourse();
		}

		viewAddCourse();
	}

	/**
	 * Exits the program.
	 */
	private void exit() {
		System.out.print(EXIT);
		scanner.close();
		System.exit(0);
	}
}
