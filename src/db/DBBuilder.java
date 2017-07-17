package db;

import java.io.*;
import java.sql.*;
import java.util.List;

public class DBBuilder {
	
	private static String[] tables = new String[]{"ADMINS","STUDENTS","COURSES","GRADING_SYSTEM",
			"COSTS","CREDIT_LIMITS","SEMESTERS","PREREQUISITES","REQUESTS"};
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            connection = DriverManager.getConnection(
	                    "jdbc:oracle:thin:@localhost:1521/orcl", "hr", "hr");
	        } catch (ClassNotFoundException | SQLException e) {
	            System.out.println("Connection failed");
	            e.printStackTrace();
	            return null;
	        }
		}
        return connection;
	}
	
	public static void close() {
	    if(connection != null) {
	        try { 
	        	connection.close();
	        	connection = null;
	        } catch(Throwable t) {
	        	t.printStackTrace();
	        }
	    }
	}
	
	public static void close(Statement st) {
	    if(st != null) {
	        try { st.close(); } catch(Throwable whatever) {}
	    }
	}
	
	public static void close(ResultSet rs) {
	    if(rs != null) {
	        try { rs.close(); } catch(Throwable whatever) {}
	    }
	}
	
	public static void createTables() {
		getConnection();
		Statement stmt = null;
        ResultSet rs = null;
        try {	
			stmt = connection.createStatement();
			String sql = readSQL("sql/createTables.sql");
	        System.out.println(sql);
			stmt.executeUpdate(sql);
		} 
    	catch(Throwable e) {
	        e.printStackTrace();
	    }
        close(rs);
        close(stmt);
        close();
	}
	
	public static void dropTables() {
		Statement stmt = null;
        try {	
			stmt = connection.createStatement();
			String sql = "DROP TABLE ";
			for (String t: tables) {
				stmt.executeUpdate(sql+t);
				System.out.println(sql+t);
			}
		} 
    	catch(Throwable e) {
	        e.printStackTrace();
	    }
        close(stmt);
	}
	
	public static void populateTables() {
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
			
			stmt.executeUpdate("CREATE TABLE ADMINS (emp_id VARCHAR(9), ssn VARCHAR(9), first_name VARCHAR(20), last_name VARCHAR(20)," +
					" user_name VARCHAR(20), password VARCHAR(20), birth_date VARCHAR(10), PRIMARY KEY (emp_id))");
			stmt.executeUpdate("INSERT INTO admins " +
					   "VALUES ('1111', '', 'Albus', 'Dumbledore', 'alby', 'hogwarts', '05/26/1984')");
			System.out.println("Added table ADMINS");
			
			stmt.executeUpdate("CREATE TABLE STUDENTS (student_id VARCHAR(9), first_name VARCHAR(20), last_name VARCHAR(20)," +
					" user_name VARCHAR(20), password VARCHAR(20), email VARCHAR(20), birth_date VARCHAR(10), grade_level INTEGER, status VARCHAR(20)," + 
					" bill FLOAT, dept_id VARCHAR(3), enrolled_courses VARCHAR(100), courses VARCHAR(100), phone VARCHAR(20), address VARCHAR(100), PRIMARY KEY (student_id))");
			stmt.executeUpdate("INSERT INTO students " +
					   "VALUES ('101','Harry','Potter','username','password','email','01/12/1990',1,'in',1200,'ECE','','CE420(A)','phone','addr')");
			stmt.executeUpdate("INSERT INTO students " +
					   "VALUES ('102','Hermione','Granger','username','password','','04/16/1989',2,'in',0,'CS','','CS510(B+),CS515(B+)','phone','addr')");
			stmt.executeUpdate("INSERT INTO students " +
					   "VALUES ('103','Ron','Weasley','username','password','','12/19/1991',2,'out',0,'CS','','CS515(A),CS520(A-),CS530(A+)','phone','addr')");
			stmt.executeUpdate("INSERT INTO students " +
					   "VALUES ('104','Draco','Malfoy','username','password','','03/21/1992',2,'inter',0,'CS','','CS510(A),CS515(B+),CS530(A+),CS525 (3 Credits)(A+)','phone','addr')");
			System.out.println("Added table STUDENTS");
			
			stmt.executeUpdate("CREATE TABLE COURSES (course_id VARCHAR(10), title VARCHAR(20), credits INTEGER, dept_id VARCHAR(3)," +
					" grade_level INTEGER, PRIMARY KEY (course_id))");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS402','Numerical Methods',3,'CS',1)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS510','Database',3,'CS',2)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS505','Algorithms',3,'CS',2)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS521','Cloud Computing',3,'CS',2)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS525','Independent Study',2,'CS',2)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS530','Dev-Ops',3,'CS',2)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CS421','VLSI II',3,'ECE',1)");
			stmt.executeUpdate("INSERT INTO courses " +
					   "VALUES ('CE420','Wizard Computing',3,'ECE',1)");
			System.out.println("Added table COURSES");
			
			stmt.executeUpdate("CREATE TABLE SEMESTERS (course_id VARCHAR(10), semester VARCHAR(10), year INTEGER, instructors VARCHAR(100)," +
					" class_size INTEGER, max_size INTEGER, waitlist VARCHAR(100), wait_size INTEGER, schedule VARCHAR(100), PRIMARY KEY (course_id,semester,year))");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS402','Fall','2016','',2,10,'',2,'M,W 11:00AM-12:00PM')");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS510','Fall','2016','Ogan',5,10,'',2,'M,W 11:00AM-12:00PM')");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS505','Fall','2016','Stallman',2,10,'',2,'Tu,Th 1:00PM-2:00PM')");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS521','Fall','2016','Instructor1, Instructor2',3,10,'',2,'Tu,Th 1:00PM-2:00PM')");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS525','Fall','2016','Jin,Murphy-Hill',2,10,'',0,'M,W 2:00PM-3:00PM')");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS530','Fall','2016','Parnin',2,10,'',2,'M,W 11:00AM-12:00PM')");
			stmt.executeUpdate("INSERT INTO SEMESTERS " +
					   "VALUES ('CS421','Fall','2016','???',4,10,'',2,'Tu,Th 4:00PM-5:00PM')");
			System.out.println("Added table COURSES");
			
			stmt.executeUpdate("CREATE TABLE GRADING_SYSTEM (grade VARCHAR(2), points FLOAT, PRIMARY KEY (grade))");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('A+',4.33)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('A',4.00)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('A-',3.67)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('B+',3.33)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('B',3.00)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('B-',2.67)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('C+',2.33)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('C',2.00)");
			stmt.executeUpdate("INSERT INTO GRADING_SYSTEM VALUES ('C-',1.67)");
			System.out.println("Added table GRADING_SYSTEM");
			
			stmt.executeUpdate("CREATE TABLE COSTS (grade_level integer, residency VARCHAR(10), cost INTEGER," +
					" PRIMARY KEY (grade_level,residency))");
			stmt.executeUpdate("INSERT INTO COSTS VALUES (2,'in',500)");
			stmt.executeUpdate("INSERT INTO COSTS VALUES (2,'out',800)");
			stmt.executeUpdate("INSERT INTO COSTS VALUES (2,'inter',1000)");
			stmt.executeUpdate("INSERT INTO COSTS VALUES (1,'in',400)");
			stmt.executeUpdate("INSERT INTO COSTS VALUES (1,'out',700)");
			stmt.executeUpdate("INSERT INTO COSTS VALUES (1,'inter',900)");
			System.out.println("Added table COSTS");
			
			stmt.executeUpdate("CREATE TABLE CREDIT_LIMITS (grade_level integer, residency VARCHAR(10), min INTEGER, max INTEGER," +
					" PRIMARY KEY (grade_level,residency))");
			stmt.executeUpdate("INSERT INTO CREDIT_LIMITS VALUES (2,'in',0,9)");
			stmt.executeUpdate("INSERT INTO CREDIT_LIMITS VALUES (2,'out',0,9)");
			stmt.executeUpdate("INSERT INTO CREDIT_LIMITS VALUES (2,'inter',9,9)");
			stmt.executeUpdate("INSERT INTO CREDIT_LIMITS VALUES (1,'in',0,12)");
			stmt.executeUpdate("INSERT INTO CREDIT_LIMITS VALUES (1,'out',0,12)");
			stmt.executeUpdate("INSERT INTO CREDIT_LIMITS VALUES (1,'inter',9,12)");
			System.out.println("Added table CREDIT_LIMITS");
			
			stmt.executeUpdate("CREATE TABLE PREREQUISITES (course_id VARCHAR(10), gpa FLOAT, prereq_id VARCHAR(10), special VARCHAR(1)," +
					" PRIMARY KEY (course_id))");
			stmt.executeUpdate("INSERT INTO PREREQUISITES VALUES ('CS401',0,'CS402','F')");
			stmt.executeUpdate("INSERT INTO PREREQUISITES VALUES ('CS521',3.5,'CS520','F')");
			stmt.executeUpdate("INSERT INTO PREREQUISITES VALUES ('CS525',0,'','T')");
			stmt.executeUpdate("INSERT INTO PREREQUISITES VALUES ('CS530',0,'CS515','F')");
			stmt.executeUpdate("INSERT INTO PREREQUISITES VALUES ('CS421',0,'CE420','F')");
			System.out.println("Added table PREREQUISITES");
			
			stmt.executeUpdate("CREATE TABLE REQUESTS (student_id VARCHAR(10), course_id VARCHAR(10), submit_date VARCHAR(10), unit INTEGER," +
					" status VARCHAR(10), PRIMARY KEY (student_id, course_id))");
			System.out.println("Added table REQUESTS");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(stmt);
	}
	
	private static String readSQL(String path) {
		String sql = "";
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				sql += line;
				line = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sql;
	}
	public static void main(String[] args) {
		//Test connection
		connection = getConnection();
        if (connection != null) {
            System.out.println("Connected...");
        } else {
            System.out.println("Connection failed!");
        }
        dropTables();
        populateTables();
        close();
	}
	
}
