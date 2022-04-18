package com.training.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.event.PopupMenuListener;

public class JdbcTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = "jdbc:sqlserver://localhost:1433;databaseName=college;integratedsecurity=true;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String password="password5";
		Connection con = DriverManager.getConnection(url,user,password);
		String query = "select * from students";
		/*if(con != null){
			System.out.println("Connection established successfully!!!");
		}else{
			System.out.println("Connection refused");
		}*/
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		ArrayList<Student> students = new ArrayList<Student>();
		
		while(rs.next()){
			students.add(populateStudent(rs));
			//System.out.println("StudentID RS: " +rs.getString("student_id"));
			//System.out.println("StuentName: " +rs.getString("student_name"));
			//System.out.println("StudentMarks: " +rs.getString("student_marks"));
		}
		System.out.println("###### Original Order");
		for(Student student : students){
			System.out.println(student.getStudentName()+" received "+student.getStudentMarks()+" marks.");
		}
		//ArrayList<Student> studentList = students;

		Comparator<Student> compareByMarks = 
			(Student o1, Student o2) -> Integer.compare(o1.getStudentMarks(), o2.getStudentMarks());
		//o1.getStudentMarks().compareTo( o2.getStudentMarks());
		System.out.println("###### Ascending Order");
		Collections.sort(students, compareByMarks);
		for(Student student : students){
			System.out.println(student.getStudentName()+" received "+student.getStudentMarks()+" marks.");
		}
		//System.out.println("###### Descending Order");
		//Collections.sort(studentList, compareByMarks.reversed());
	}

	private static Student populateStudent(ResultSet rs) throws SQLException{
		Student student = new Student();
		student.setStudentId(rs.getInt("student_id"));
		student.setStudentName(rs.getString("student_name"));
		student.setStudentMarks(rs.getInt("student_marks"));
		return student;
	}
}
