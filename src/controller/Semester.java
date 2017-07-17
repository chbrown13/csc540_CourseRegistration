 package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DBBuilder;

public class Semester {
	private String semester;
	private String year;
	private int maxSeats;
	private List<Student> waitlist;
	private int maxWait;
	private String location;
	private String schedule, days, startTime, endTime;
	private String courseID;
	private List<Instructor> instructors;
	private int size;
	private String addDrop;
	
	public Semester() {
		
	}
	
	/**
	 * @return get course offering
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * @param sem the semester to set
	 */
	public void setCourseID(String id) {
		this.courseID = id;
	}
	
	/**
	 * @return the course instructors
	 */
	public List<Instructor> getInstructors() {
		return instructors;
	}

	/**
	 * @param list of instructors
	 */
	public void setInstructors(List<Instructor> list) {
		this.instructors = list;
	}
	
	/**
	 * @return the semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * @param sem the semester to set
	 */
	public void setSemester(String sem) {
		this.semester = sem;
	}
	
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param yyyy year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * @return the maxSeats
	 */
	public int getMaxSeats() {
		return maxSeats;
	}

	/**
	 * @param maxSeats the maxSeats to set
	 */
	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}
	
	/**
	 * @return class size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param maxSeats the maxSeats to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the waitlist
	 */
	public List<Student> getWaitlist() {
		return waitlist;
	}

	/**
	 * @param waitlist the waitlist to set
	 */
	public void setWaitlist(List<Student> waitlist) {
		this.waitlist = waitlist;
	}

	/**
	 * @return the maxWait
	 */
	public int getMaxWait() {
		return maxWait;
	}

	/**
	 * @param maxWait the maxWait to set
	 */
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * @return add drop date
	 */
	public String getAddDrop() {
		return addDrop;
	}

	/**
	 * @param location the location to set
	 */
	public void setAddDrop(String addDrop) {
		this.addDrop = addDrop;
	}
	
	/**
	 * @returns schedule in format "D,D HH:MM-HH:MM"
	 */
	public String getSchedule() {
		String sched = this.days+" "+this.startTime+"-"+this.endTime;
		return sched;
	}
	
	public String getWaitString() {
		String str = "";
		for (Student s: this.waitlist) {
			str += s.getStudentID()+",";
		}
		return str;
	}
	
	public List<Student> studentWaitList(String str) {
		String[] wait = str.split(",");
		List<Student> list = new ArrayList<Student>();
		Connection conn = DBBuilder.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM SEMESTERS WHERE semester=? AND year=?");
			ps.setString(1, this.semester);
			ps.setString(2, this.year);
			rs = ps.executeQuery();
			if(rs.next()) {
				String waiting = rs.getString("waitlist");
				String[] ids = waiting.split(",");
				for(String id: ids) {
					Student s = Student.findStudent(id);
					list.add(s);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setWaitlist(list);
		return list;
	}
	
	/**
	 * @param schedule to set from db
	 * Must be in format "D,D HH:MMTT-HH:MMTT" where TT is AM or PM
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
		String[] sched = schedule.split(" ");
		this.days = sched[0];
		String[] times = sched[1].split("-");
		this.startTime = times[0];
		this.endTime = times[1];
	}
	
	public String getDays() {
		return days;
	}
	
	public void setDays(String d) {
		this.days = d;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String start) {
		this.startTime = start;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public String viewPending(String sem, String year) {
		String output = "Available Courses";
		
		return output;
	}
	
	public String viewCourses() {
		String output = "My Courses";
		
		return output;
	}
	
	/**
	 * Drop the course from schedule
	 * @param id
	 */
	public void dropCourses() {

	}
	
	public void setEndTime(String end) {
		this.endTime = end;
	}
	
	/**
	 * Can this student enroll in this course?
	 * @param s
	 * @param c
	 * @return
	 */
	public boolean enrollStudent(Student s, Course c) {
		if(s.getCourses().containsKey(this.courseID) || s.getPendingCourses().contains(c) || 
				this.size == this.maxSeats || !Admin.checkCredits(s,c,false)) {
			return false;
		}
		this.size += 1;	
		if(this.waitlist.contains(s)) {
			this.waitlist.remove(s);
		}
		update();
		return true;
	}
	
	/**
	 * Can this student drop this course?
	 * @param s
	 * @param c
	 * @return
	 */
	public boolean dropStudent(Student s, Course c) {
		if(!s.getPendingCourses().contains(c) || this.size <= 0 || !Admin.checkCredits(s, c, true)) {
			return false;
		}
		this.size -= 1;
		update();
		return true;
	}
	
	/**
	 * Can this student join the waitlist for this course?
	 * @param s
	 * @return
	 */
	public boolean waitlistStudent(Student s) {
		if(waitlist.contains(s.getStudentID()) || this.waitlist.size()+1 > this.maxWait) {
			return false;
		}
		this.waitlist.add(s);
		update();
		return true;
	}
	
	public String getStudentStatus(Student s) {
		if(this.waitlist.contains(s.getStudentID())) {
			return "WAITLISTED";
		}
		else if (this.size < this.maxSeats) {
			return "AVAILABLE";
		}
		else if (this.waitlist.size() < this.maxWait) {
			return "REJECTED (waitlist)";
		}
		else {
			return "REJECTED";
		}
	}
	
	private void update() {
		Connection conn = DBBuilder.getConnection();
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE STUDENTS SET enrolled_courses='' WHERE bill > 0");
			pstmt = conn.prepareStatement("UPDATE SEMESTERS SET size=?,waitlist=? WHERE semester=? AND year=?");
			pstmt.setInt(1, this.size);
			pstmt.setString(2, this.getWaitString());
			pstmt.setString(3, this.semester);
			pstmt.setString(4, this.year);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBBuilder.close(stmt);
		DBBuilder.close();
	}
	//testing
	public static void main(String[] args) {
		Semester s = new Semester();
		s.setCourseID("CSC540");
		s.setSchedule("M,W 11:00AM-12:00PM");
		System.out.println(s.getDays());
		System.out.println(s.getStartTime());
		System.out.println(s.getEndTime());
	}
	
}
