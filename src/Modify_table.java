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

/**
 * Servlet implementation class Store_Assignment
 */
@WebServlet("/modifyTable")
public class Modify_table extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Modify_table() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response.setContentType("text/html");
		
		String Store_table = "";
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
		//properties for creating connection to Oracle database
		Properties props = new Properties();
		
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		
		//creating connection to Oracle database using JDBC
		Connection conn;
		
		PreparedStatement preStatement;
		String sql_command = "select * from STRONGHEIM_TABLE";
		int ands = 0;
		if(!(request.getParameter("studentID").equalsIgnoreCase("Please select")) || 
				!(request.getParameter("class").equalsIgnoreCase("Please select")) || 
				!(request.getParameter("Assignment").equalsIgnoreCase("Please select"))){
			sql_command += " where " ;
			
			if(!(request.getParameter("studentID").equalsIgnoreCase("Please select"))){
				ands++;
				sql_command += "id = " + request.getParameter("studentID") + "   ";
			}
			
			if(!(request.getParameter("Assignment").equalsIgnoreCase("Please select"))){
				ands++;
				sql_command += "assignment = '" + request.getParameter("Assignment") + "'   ";
			}
			
			if(!(request.getParameter("class").equalsIgnoreCase("Please select"))){
				ands++;
				sql_command += "class = '" + request.getParameter("class") + "'   ";
			}
		}
		for(int i = 1;i < ands; i++){
			sql_command = sql_command.replaceFirst("   ", " and ");
		}
		
		System.out.println(sql_command);
		
		Store_table += "<div class=\"container-fluid\"> <table class=\"table table-striped table-bordered\">";
		Store_table += "<br><br><br><br><br><br><br><br><br>"
				+ "<h3>"  + "Grades for Student ID:" + " <span id=\"Iyde\">" +
				"</span>" + "<input type=\"hidden\" value=" 
				+ request.getParameter("studentID") +" name=\"Iyde\" >" 
				+ request.getParameter("studentID") + "</h3>";
		Store_table += "<thread>";
		Store_table += "<tr>"; 
		Store_table += "<th id=\"Assn\"> Assignment </th>";  
		Store_table += "<th> Date </th>";   
		Store_table += "<th> Grade </th>"; 
		Store_table += "</tr>";
		Store_table += "</thread>";
		Store_table +=  "<tbody>" ;
		
//		double sum = 0,max = 0,min = 100, average = 0;
//		int count = 0;
		
		try{
				conn = DriverManager.getConnection(url,props);
				preStatement = conn.prepareStatement(sql_command);
				ResultSet result = preStatement.executeQuery();
			    
			    while(result.next()){
			    	
			    	//Count number of results for determining average
//			    	count++;
//			    	sum += result.getDouble("Grade");
//			    	
//			    	if(result.getDouble("Grade") > max){
//			    		max = result.getDouble("Grade");
//			    	}
//			    	
//			    	if(result.getDouble("Grade") < min){
//			    		min = result.getDouble("Grade");
//			    	}
					Store_table +=  "<tr>";
					Store_table +=  "<td>" + " <span id=\"Assn\">" +
					"</span>" + "<input type=\"hidden\" value=" 
					+ result.getString("Assignment") +" name=\"Assn\" >" 
					+ result.getString("Assignment") + "</td>";

					Store_table +=  "<td>" + result.getString("Assignment_date") + "</td>";
					Store_table +=  "<td id=\"assn\" name=\"assn\">" + result.getDouble("Grade_points") + " Change to: <br>" +  "<div class=\"col-xs-4 \"> <input type=\"text \" class=\"form-control\" id=\"Grade\" name=\"Grade\" placeholder=\"Grade 0-100\"> <button type=\"submit\" class=\"btn btn-default \" formaction=\"/Strongheim_Assignment_final/change\">Change </button> </div>" + "</td>" ;
					Store_table +=  "</tr> <br/>";
			    }
			    //average = sum/count;
			    
			    Store_table += "</tbody> </table> </div>";
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		
		request.setAttribute("table",Store_table);
//		request.setAttribute("average",(" " + average + " ").format("%.2f", average));
//		request.setAttribute("min"," " + min + " ");
//		request.setAttribute("max"," " + max + " ");
		getServletContext().getRequestDispatcher("/Modify.jsp").forward(request, response);
	

	  } 
	  
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
