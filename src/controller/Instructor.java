package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Instructor {
	private String instrID;
	private String deptID;
	private String name;
	
	public Instructor() {
		
	}

	/**
	 * @return the instrID
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param instrID the instrID to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the instrID
	 */
	public String getInstrID() {
		return instrID;
	}

	/**
	 * @param instrID the instrID to set
	 */
	public void setInstrID(String instrID) {
		this.instrID = instrID;
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
	
	public static List<Instructor> instructorsList(String str, String dept) {
		List<Instructor> list = new ArrayList<Instructor>();
		if(str != null && !str.isEmpty()) {
			String[] instrs = str.split(",");
			for(String s: instrs) {
				Instructor i = new Instructor();
				i.setName(s);
				i.setDeptID(dept);
				list.add(i);
			}
		}
		return list;
	}
	
	public static String instructorsString(List<Instructor> list) {
		String str = "";
		if(list != null && !list.isEmpty()) {
			for(Instructor i: list) {
				str += i.getName()+",";
			}
		}
		return str;
	}
}
