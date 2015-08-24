//Storing students informations into database

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Assignment_into_db")
public class Store_Assn_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Store_Assn_info() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String calculate(double grade_points){
    	String grade_letter = "";
    	double gpa  = 0;
    	
    	if( grade_points > 90){
    		grade_letter = "A";
    		gpa = 4;
    	}else if( grade_points > 80){
    		grade_letter = "B";
    		gpa = 3;
    	}else if( grade_points > 70){
    		grade_letter = "C";
    		gpa = 2;
    	}else if( grade_points > 60){
    		grade_letter = "D";
    		gpa = 1;
    	}else {
    		grade_letter = "F";
    		gpa = 0;
    	}
    	
    	return gpa + "" + grade_letter;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int size = calculate(Double.parseDouble(request.getParameter("Grade"))).length();
		double grade_points = Double.parseDouble(request.getParameter("Grade"));
		String grade_letter = calculate(grade_points).substring(size - 1);
		String gpa = calculate(grade_points).substring(0,size - 1);
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
		//properties for creating connection to Oracle database
		Properties props = new Properties();
		
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		
		//creating connection to Oracle database using JDBC
		Connection conn;
		
		PreparedStatement preStatement;
		String sql_command = "insert into STRONGHEIM_TABLE values (?,?,?,?,?,?,?,?)";
		try{
			conn = DriverManager.getConnection(url,props);
			preStatement = conn.prepareStatement(sql_command);
			
			
			preStatement.setString(1,request.getParameter("ID"));
			preStatement.setString(2,request.getParameter("code"));
			preStatement.setString(3,request.getParameter("Assignment"));
			preStatement.setString(4,request.getParameter("type"));
			preStatement.setString(5,request.getParameter("datepicker"));
			preStatement.setString(6,request.getParameter("Grade"));
			preStatement.setString(7,gpa);
			preStatement.setString(8,grade_letter);
			
			//System.out.println(request.getParameter("datepicker"));
			ResultSet result = preStatement.executeQuery();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
