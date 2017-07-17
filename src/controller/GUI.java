package controller;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	
	JFrame frame;
	JPanel 	StartMenu, LoginPage, 
			AdminMain, AdminProfile, AdminEnroll, AdminViewStudent, 
			AdminCourse, AdminViewCourse, AdminAddCourse;
	
	public GUI() {
		JFrame frame = new JFrame();
			
		 //make sure the program exits when the frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Course Registration GUI");
		frame.setSize(300,250);
		
		 //This will center the JFrame in the middle of the screen
        frame.setLocationRelativeTo(null);
		
        StartMenu = new JPanel();
        LoginPage = new JPanel();
        AdminMain = new JPanel();
        
        FlowLayout flowLayout = new FlowLayout();
        
        // Start Menu Panel
		JButton btnLogin = new JButton("Login");
		JButton btnExit = new JButton("Exit");
		
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				StartMenu.setVisible(false);
				LoginPage.setVisible(true);
			}
			
		});
		
		btnExit.addActionListener(new CloseListener());
		
		StartMenu.setLayout(flowLayout);
		StartMenu.add(btnLogin);
		StartMenu.add(btnExit);
//		frame.add(StartMenu);
		
		// Login Menu Panel
		JLabel lblUsername = new JLabel("Username: ");
		JTextField txtUsername = new JTextField();
		JLabel lblPassword = new JLabel("Password: ");
		JFormattedTextField txtPassword = new JFormattedTextField();
		
		LoginPage.setLayout(new BoxLayout(LoginPage, BoxLayout.PAGE_AXIS));
		JPanel pnlUsername = new JPanel();
		pnlUsername.setLayout(new BoxLayout(pnlUsername, BoxLayout.LINE_AXIS));
		JPanel pnlPassword = new JPanel();
		pnlPassword.setLayout(new BoxLayout(pnlPassword, BoxLayout.LINE_AXIS));
		
		pnlUsername.add(lblUsername);
		pnlUsername.add(txtUsername);
		
		pnlPassword.add(lblPassword);
		pnlPassword.add(txtPassword);
		
		LoginPage.add(pnlUsername);
		LoginPage.add(pnlPassword);
		
//		frame.add(LoginPage);
		
		// Admin Main Panel
		AdminMain.setLayout(new BoxLayout(AdminMain, BoxLayout.PAGE_AXIS));
		JButton btnViewProfile = new JButton("View Profile");
		JButton btnEnrollStudent = new JButton("Enroll A New Student");
		JButton btnViewStudentDetails = new JButton("View Student's Details");
		JButton btnViewAddCourses = new JButton("View/Approve Courses");
		JButton btnViewAddCourseOfferings = new JButton("View/Approve Course Offering");
		JButton btnViewApproveRequests = new JButton("View/Approve Special Enrollment Requests");
		JButton btnEnforceDeadline = new JButton("Enforce Add/Drop Deadline");
		JButton btnLogout = new JButton("Logout");
		
		AdminMain.add(btnViewProfile);
		AdminMain.add(btnEnrollStudent);
		AdminMain.add(btnViewStudentDetails);
		AdminMain.add(btnViewAddCourses);
		AdminMain.add(btnViewAddCourseOfferings);
		AdminMain.add(btnViewApproveRequests);
		AdminMain.add(btnEnforceDeadline);
		AdminMain.add(btnLogout);
		
		frame.add(AdminMain);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	private class CloseListener implements ActionListener{
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        //DO SOMETHING
	        System.exit(0);
	    }
	}
}
