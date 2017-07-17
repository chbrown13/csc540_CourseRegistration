package controller;

import java.sql.*;

public class Prerequisite {
	private String CourseID;
	private String PrereqID;
	private String Grade;
	private float GPA;
	private boolean Permission;
	
	public static final String SPECIAL = "Requires Special Permission";

	public Prerequisite() {
		
	}

	public Prerequisite ViewProfile(String CourseID, String PrereqID){
		return this;
	}
	
	/**
	 * @return the courseID
	 */
	public String getCourseID() {
		return CourseID;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(String courseID) {
		CourseID = courseID;
	}

	/**
	 * @return the prereqID
	 */
	public String getPrereqID() {
		return PrereqID;
	}

	/**
	 * @param prereqID the prereqID to set
	 */
	public void setPrereqID(String prereqID) {
		PrereqID = prereqID;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return Grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		Grade = grade;
	}

	/**
	 * @return the gPA
	 */
	public float getGPA() {
		return GPA;
	}

	/**
	 * @param gPA the gPA to set
	 */
	public void setGPA(float gPA) {
		GPA = gPA;
	}

	/**
	 * @return the permission
	 */
	public Boolean getPermission() {
		return Permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Boolean permission) {
		Permission = permission;
	}
	
	
	/**
	 * Viewing prerequisites for a given course
	 * **/
	public String AddPrereq(String courseID, String PrereqID, String Grade, int GPA, boolean Permission){
		this.CourseID= courseID;
		this.PrereqID = PrereqID;
		this.Grade = Grade;
		this.GPA = GPA;
		this.Permission = Permission;
		
		return "";
	}	
}