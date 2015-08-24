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

@WebServlet("/change")
public class update_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public update_info() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

		
		String sql_command = "update STRONGHEIM_TABLE set grade_points = ? where id = ? and Assignment = ?";
		System.out.println(sql_command);
		
		try{
			conn = DriverManager.getConnection(url,props);
			preStatement = conn.prepareStatement(sql_command);
			
			preStatement.setString(1, request.getParameter("Grade"));
			preStatement.setString(2, request.getParameter("Iyde"));
			preStatement.setString(3,  request.getParameter("Assn"));
			
			
			
			preStatement.executeUpdate();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
