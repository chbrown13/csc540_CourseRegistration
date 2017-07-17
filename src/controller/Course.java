package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
	private String deptID;
	private String courseName;
	private String courseID;
	private int size;
	private int credits;
	private int level;
	private Prerequisite prereqs;
	private boolean approval;
	
	
	/**
	 * @return the gpaReq
	 */
	public Prerequisite getPrereq() {
		return prereqs;
	}

	/**
	 * @param gpaReq the gpaReq to set
	 */
	public void setPrereq(Prerequisite p) {
		this.prereqs = p;
	}

	/**
	 * @return the approval
	 */
	public boolean isApproval() {
		return approval;
	}

	/**
	 * @param approval the approval to set
	 */
	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	public Course() {
		
	}

	public void getCourse(int id) {
		
	}
	
	/**
	 * @return the deptID
	 */
	public String getDeptID() {
		return deptID;
	}

	/**
	 * @param deptID the deptID to set
	 */
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}

	/**
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param title the title to set
	 */
	public void setCourseName(String title) {
		this.courseName = title;
	}

	/**
	 * @return the courseID
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * @param i the credits to set
	 */
	public void setCredits(int i) {
		this.credits = i;
	}

	public static List<Course> enrolledCoursesList(String str) {
		List<Course> list = new ArrayList<Course>();
		if(str != null) {
			String[] courses = str.split("\n");
			for(String s: courses) {
				Course c= new Course();
				c.setCourseID(s);
				list.add(c);
			}
		}
		return list;
	}
	
	public static Map<Course,String> coursesList(String str) {
		Map<Course,String> map = new HashMap<Course,String>();
		if(str != null) {
			String[] courses = str.split("\n");
			for(String s: courses) {
				Course c = new Course();
				c.setCourseID(s);
				String id = s.substring(0,s.indexOf("("));
				c.setCourseID(id);
				String grade = s.substring(s.indexOf("(")+1,s.indexOf(")"));
				map.put(c, grade);
			}
		}
		return map;
	}
	
	public static String enrolledCoursesString(List<Course> list) {
		String str = "";
		if(list != null) {
			for(Course c: list) {
				str += c.getCourseID() + "\n";
			}
			return str;
		}
		return "";
	}
	
	public static String coursesString(Map<Course,String> map) {
		String str = "";
		if(map != null) {
			for(Course c: map.keySet()) {
				str += c.getCourseID()+"("+map.get(c)+")\n";
			}
			return str;
		}
		return "";
	}
	
	//testing
	public static void main(String[] args) {
		Map<Course,String> m = Course.coursesList("CS515(A)\nCS520(A-)\nCS530(A+)");
		System.out.println(coursesString(m));
	}
}