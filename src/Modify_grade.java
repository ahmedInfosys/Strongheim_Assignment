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


@WebServlet("/modify1")
public class Modify_grade extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Modify_grade() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("text/html");
		
		String id_options = "",class_options = "", Assignment_options = "";
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
		//properties for creating connection to Oracle database
		Properties props = new Properties();
		
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		
		//creating connection to Oracle database using JDBC
		Connection conn;
		
		PreparedStatement preStatement;
		String sql_command = "select distinct class from STRONGHEIM_TABLE";
		String sql_command2 = "select distinct id from STRONGHEIM_TABLE";
		String sql_command3 = "select distinct Assignment from STRONGHEIM_TABLE";

		try{
				conn = DriverManager.getConnection(url,props);
				preStatement = conn.prepareStatement(sql_command);
				ResultSet result = preStatement.executeQuery();
			    
				//Dropdown for student classes
				class_options +=  "<option>Please select</option>";
			    while(result.next()){
			    	class_options +=  "<option>" + result.getString("class") + "</option>";
			    }
				
			    preStatement = conn.prepareStatement(sql_command2);
				result = preStatement.executeQuery();

				//Dropdown for student IDs
				id_options +=  "<option>Please select</option>";
			    while(result.next()){
					id_options +=  "<option>" + result.getInt("ID") + "</option>";
			    }
			    
			    
			    preStatement = conn.prepareStatement(sql_command3);
				result = preStatement.executeQuery();
				
				//Dropdowan for Assignments
				Assignment_options +=  "<option>Please select</option>";
			    while(result.next()){
			    	Assignment_options +=  "<option>" + result.getString("Assignment") + "</option>";
			    }


		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("classes",class_options);
		request.setAttribute("IDs",id_options);
		request.setAttribute("Assignments",Assignment_options);
		getServletContext().getRequestDispatcher("/Modify.jsp").forward(request, response);
	

	  } 
	  
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
